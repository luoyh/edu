package com.edu.roy.wx.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts({ @Signature(
		method = "prepare", 
		type = StatementHandler.class, 
		args = { Connection.class, Integer.class }) 
})
public class PageInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		BoundSql boundSql = statementHandler.getBoundSql();
		Object param = boundSql.getParameterObject();
		String sql = statementHandler.getBoundSql().getSql();
		if (param instanceof Page) {
			Page<?> page = (Page<?>) param;
			
			String base = "select count(*) " + sql.substring(sql.indexOf("FROM"));
			Connection connection = (Connection)invocation.getArgs()[0]; // don't close
			try (PreparedStatement countStatement = connection.prepareStatement(base)) {
				ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue("delegate.parameterHandler");
				parameterHandler.setParameters(countStatement);
				try (ResultSet rs = countStatement.executeQuery()) {
					if(rs.next()) {
						page.setTotal(rs.getInt(1));
					}
				}
			}
			
			if (!StringUtils.isBlank(page.getOrder())) {
				sql += " order by " + page.getOrder();
				if (!page.isAsc()) {
					sql += " desc ";
				}
			}
			
			sql += " limit " + page.getStart() + "," + page.getSize();
			metaObject.setValue("delegate.boundSql.sql", sql);
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		
	}

}
