package com.edu.roy.wx.tools;

import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTools {
	
	private static ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
	}
	
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T readObject(String json, Class<T> t) {
		try {
			return mapper.readValue(json, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> List<T> readList(String json, TypeReference<List<T>> reference) {
		try {
			return mapper.readValue(json, reference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
