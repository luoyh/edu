
# sys

```
drop table if exists sys;
create table sys (
	id int not null auto_increment,
	sys_key varchar(32) not null,
	sys_value varchar(128) not null,
	sys_comment varchar(128) null,
	gmt_created datetime not null,
	gmt_modified datetime not null,
	primary key(id),
	unique index s_key(sys_key)
) engine=innodb default charset=utf8;
insert into sys values(null,'wx_appid','wx960b8ad69eeacac5','微信公众号appid',now(),now()),
					  (null,'wx_secret','d4624c36b6795d1d99dcf0547af5443d','微信公众号secret',now(),now()),
					  (null,'global_test_status','test','test是测试环境',now(),now()),
					  (null,'question_adjunct_path','c:/datas/f','附件存放路径',now(),now());
```

# member

```
drop table if exists member;
create table member (
	id bigint not null auto_increment,
	openid varchar(64) not null,
	mobile varchar(16) not null,
	name varchar(12) not null,
	age tinyint(4) not null,
	school varchar(20) not null,
	sex tinyint(2) not null default 0 comment '0:secret,1:male,2:female',
	base_wx_info varchar(5120) not null,
	gmt_created datetime not null,
	gmt_modified datetime not null,
	primary key(id),
	unique index uni_idx_openid(openid),
	unique index uni_idx_mobile(mobile)
) engine=innodb default charset=utf8 comment '用户信息表';
```

# drill_record

```
drop table if exists drill_record;
create table drill_record (
	id bigint  not null auto_increment,
	member_id bigint  not null,
	result_id bigint not null,
	type tinyint not null comment '0:order,1:suite',
	result tinyint not null comment '0:void,1:incertitude,2:bingo,3:wrong,4:part',
	subject_id bigint not null,
	suite_id bigint  not null,
	question_id bigint  not null,
	answers varchar(10240) not null,
	score int not null,
	gmt_created datetime not null,
	gmt_modified datetime not null,
	primary key(id),
	UNIQUE KEY `uidx_r_question` (`result_id`,`question_id`)
) engine=innodb default charset=utf8 comment '答题记录表';
```

# drill_result

```
drop table if exists drill_result;
create table drill_result (
	id bigint not null auto_increment,
	member_id bigint not null,
	subject_id bigint not null,
	suite_id bigint not null default 0 comment 'type < 0 is suiteId',
	type int not null comment '-2:drill,-1:exam,0:all,1:single,2:multi,3:judge,4:solve,5:other',
	target_id bigint null comment 'type < 0 is suiteId else questionId',
	score int not null,
	status int not null default 0 comment '0:unchecked,1:checked',
	gmt_created datetime not null,
	gmt_modified datetime not null,
	primary key(id)
)engine=innodb default charset=utf8 comment '练习结果表';
```

# wrong_result

```
drop table if exists wrong_result;
create table wrong_result (
	id bigint not null auto_increment,
	member_id bigint not null,
	subject_id bigint not null,
	suite_id bigint not null,
	question_id bigint not null,
	answers varchar(10240) not null,
	cnd int not null default 0 comment '0:default,1:solved',
	gmt_created datetime not null,
	gmt_modified datetime not null,
	primary key(id),
	unique index uidx_m_q(member_id, question_id)
)engine=innodb default charset=utf8 comment '错题表';
```

# subject

```
drop table if exists subject;
create table subject (
	id bigint not null auto_increment,
	name varchar(8) not null comment '科目名称',
	gmt_created datetime not null,
	gmt_modified datetime not null,
	primary key(id),
	unique index s_name(name)
) engine=innodb default charset=utf8 comment '科目表';
```


# suite

```
drop table if exists suite;
create table suite (
	id bigint not null auto_increment,
	title varchar(1024) not null comment '试卷标题',
	years int not null comment '试卷年份，4位',
	months int not null comment '试卷月份',
	subject_id int not null comment '所属科目',
	questions int not null default 0 comment '题目数量',
	score int not null default 0 comment '此套试卷的分数',
	timing int not null default 0 comment '此套试卷的答题时间,秒',
	gmt_created datetime not null,
	gmt_modified datetime not null,
	primary key(id)
) engine=innodb default charset=utf8 comment '试卷表';
```

# question

```
drop table if exists question;
create table question (
	id bigint  not null auto_increment,
	sort int not null comment '题目排序，也就是题目编号',
	title varchar(5120) not null comment '题目标题',
	type tinyint not null comment '0:single,1:multi,3:判断题,3:other',
	suite_id bigint not null comment '试卷id',
	subject_id bigint not null comment '科目id',
	score int not null comment '题目分数',
	options varchar(1024) null comment 'JSON数组ABC',
	answers varchar(5120) null comment '题目答案JSON数组ABC,非选择题则是第一个元素',
	description varchar(5120) null comment '题目说明',
	adjunct varchar(1024) null comment '题目附件地址',
	ass_adjunct varchar(1024) null comment '题目答案附件地址',
	adjunct_type tinyint(2) not null default 0 comment '附件类型:0:图片,1:audio,2:video',
	ass_adjunct_type tinyint(2) not null default 0,
	gmt_created datetime not null,
	gmt_modified datetime not null,
	primary key(id)
) engine=innodb default charset=utf8;
```

