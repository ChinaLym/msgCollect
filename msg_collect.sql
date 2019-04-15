/*
SQLyog v10.2 
MySQL - 5.7.20 : Database - msg_collect
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`msg_collect` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `msg_collect`;

/*Table structure for table `tb_admin` */

DROP TABLE IF EXISTS `tb_admin`;

CREATE TABLE `tb_admin` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `realname` varchar(50) DEFAULT NULL,
  `head_uri` varchar(100) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='管理员表，管理后台，目前未使用，启用时应分离数据库，保证系统安全';

/*Table structure for table `tb_admin_login_record` */

DROP TABLE IF EXISTS `tb_admin_login_record`;

CREATE TABLE `tb_admin_login_record` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) DEFAULT NULL,
  `ip` varchar(46) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `is_pass` tinyint(1) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员登陆记录表，未使用，启用时应分离数据库，保证系统安全';

/*Table structure for table `tb_answer` */

DROP TABLE IF EXISTS `tb_answer`;

CREATE TABLE `tb_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer_record_id` int(11) NOT NULL COMMENT 'answer_record表id',
  `field_id` int(11) NOT NULL COMMENT 'field表id',
  `type` varchar(10) NOT NULL COMMENT '类型，冗余字段名称',
  `content` varchar(256) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`id`),
  KEY `answer_record_id` (`answer_record_id`),
  KEY `idx_field_id` (`field_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='答案表，一次回答里，每个表字段均对应一条答案记录';

/*Table structure for table `tb_answer_record` */

DROP TABLE IF EXISTS `tb_answer_record`;

CREATE TABLE `tb_answer_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL COMMENT 'table表id',
  `user_id` int(11) NOT NULL COMMENT 'user表id',
  `user_real_name` varchar(50) DEFAULT NULL COMMENT '填写人姓名，冗余字段，该字段在 user 真实姓名修改时需要同步',
  `ip` int(11) DEFAULT NULL COMMENT '转为int后的ip地址',
  `device_system` varchar(32) DEFAULT NULL COMMENT '访问设备的系统名称',
  `browser` varchar(32) DEFAULT NULL COMMENT '访问者使用的浏览器',
  `update_time` datetime DEFAULT NULL COMMENT '该记录的最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_table_id` (`table_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='回答记录表，保存用户填表记录，一条记录可对应多条回复表';

/*Table structure for table `tb_area` */

DROP TABLE IF EXISTS `tb_area`;

CREATE TABLE `tb_area` (
  `code` int(11) NOT NULL COMMENT '主键，编码',
  `name` varchar(50) DEFAULT NULL COMMENT '地区or县名称',
  `city_code` int(11) DEFAULT NULL COMMENT '所在市 编码',
  PRIMARY KEY (`code`),
  KEY `idx_city_id` (`city_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='县区信息表';

/*Table structure for table `tb_broadcast_message` */

DROP TABLE IF EXISTS `tb_broadcast_message`;

CREATE TABLE `tb_broadcast_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creater_id` int(11) NOT NULL COMMENT '创建人',
  `title` varchar(255) DEFAULT '无标题' COMMENT '标题',
  `content` text NOT NULL COMMENT '正文',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_compel` tinyint(1) DEFAULT NULL COMMENT '是否强制提醒,转移至消息类型中更合适',
  `is_sys` tinyint(1) DEFAULT NULL COMMENT '是否是系统消息，转移至消息类型中更合适',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表，广播消息，组消息';

/*Table structure for table `tb_city` */

DROP TABLE IF EXISTS `tb_city`;

CREATE TABLE `tb_city` (
  `code` int(11) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `province_code` int(11) DEFAULT NULL COMMENT '省份编号',
  PRIMARY KEY (`code`),
  KEY `province_id` (`province_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地州市信息表';

/*Table structure for table `tb_comment` */

DROP TABLE IF EXISTS `tb_comment`;

CREATE TABLE `tb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL COMMENT '评论所在表 table表id',
  `user_id` int(11) NOT NULL COMMENT '谁评论的',
  `parent_id` int(11) NOT NULL DEFAULT '-1' COMMENT '回复的对象(其他评论)，若无则为-1',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `is_effective` tinyint(1) DEFAULT '1' COMMENT '1 有效 0已删除',
  PRIMARY KEY (`id`),
  KEY `idx_table_id` (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='评论表，提问，评论，回复均包含在内';

/*Table structure for table `tb_comment_like` */

DROP TABLE IF EXISTS `tb_comment_like`;

CREATE TABLE `tb_comment_like` (
  `id` int(11) NOT NULL,
  `aim_id` int(11) NOT NULL COMMENT '表或评论的id',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '评价的对象 1 评论 0收集表',
  `user_id` int(11) NOT NULL COMMENT '评价人',
  `like` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 赞  0踩',
  `is_effective` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 有效 0 已取消'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞表，后期业务过大时可以根据描述对象来分割成多张表';

/*Table structure for table `tb_excel_read_strategy` */

DROP TABLE IF EXISTS `tb_excel_read_strategy`;

CREATE TABLE `tb_excel_read_strategy` (
  `id` varchar(32) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '创建者，0代表系统创建，所有人都可以使用该策略',
  `name` varchar(50) NOT NULL COMMENT '策略名称',
  `table_name_strategy` tinyint(1) DEFAULT NULL COMMENT '按照文件名0 还是表内容1',
  `field_position` int(11) DEFAULT NULL COMMENT 'field在第几行',
  `start` int(11) DEFAULT NULL COMMENT '第一个想要的field所在列',
  `end` int(11) DEFAULT NULL COMMENT '最后一个想要的field所在列',
  `with_defalut_value` tinyint(1) DEFAULT NULL COMMENT '是否生成默认值，当前默认值为下一行',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='excel策略表，读取excel的策略，以后支持自定义读取策略时，可以添加一字段表示解析方向为横向或纵向';

/*Table structure for table `tb_field` */

DROP TABLE IF EXISTS `tb_field`;

CREATE TABLE `tb_field` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `table_id` int(10) NOT NULL COMMENT '所属表id',
  `num` int(11) DEFAULT NULL COMMENT '第几个字段，保存顺序',
  `name` varchar(100) DEFAULT NULL COMMENT '字段名称',
  `type` varchar(10) DEFAULT '普通' COMMENT '字段类型',
  `max_length` int(11) DEFAULT '220' COMMENT '最大长度',
  `is_required` tinyint(1) DEFAULT '0' COMMENT '该字段是否必填，默认不必填',
  `visibility` tinyint(1) DEFAULT '1' COMMENT '是否展示该字段，默认可见',
  `default_value` varchar(256) DEFAULT NULL COMMENT '默认值',
  PRIMARY KEY (`id`),
  KEY `table_id` (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8 COMMENT='字段表，所有tb_table的列（字段）都在这里';

/*Table structure for table `tb_field_type` */

DROP TABLE IF EXISTS `tb_field_type`;

CREATE TABLE `tb_field_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '类型名',
  `html_type` varchar(16) NOT NULL DEFAULT 'text' COMMENT 'input的type',
  `html_class` varchar(64) DEFAULT NULL COMMENT 'class属性',
  `html_pattern` varchar(64) NOT NULL DEFAULT '.*?' COMMENT '正则',
  `html_title` varchar(64) DEFAULT NULL COMMENT 'input的标题或提示',
  `note` varchar(255) DEFAULT NULL COMMENT '注释和说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='字段类型表，用于告诉浏览器在界面上如何展示。以后下拉框的选项从这里查，支持自定义字段，该表需要缓存至内存';

/*Table structure for table `tb_group` */

DROP TABLE IF EXISTS `tb_group`;

CREATE TABLE `tb_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组号',
  `master_id` int(11) NOT NULL COMMENT '群主id，关联user表id',
  `name` varchar(100) DEFAULT NULL COMMENT '组名',
  `icon` varchar(255) DEFAULT NULL COMMENT '组图标 uri',
  `intro` varchar(255) DEFAULT NULL COMMENT '简介',
  `notice` varchar(255) DEFAULT NULL COMMENT '公告',
  `member_number` int(11) DEFAULT '0' COMMENT '人数, 冗余字段需要定时同步',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_master_id` (`master_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户组表';

/*Table structure for table `tb_group_to_user` */

DROP TABLE IF EXISTS `tb_group_to_user`;

CREATE TABLE `tb_group_to_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '组id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `group_name` varchar(100) DEFAULT NULL COMMENT '组名,冗余字段，需要在tb_group 修改时同步',
  `group_nick` varchar(50) DEFAULT NULL COMMENT '组内昵称',
  `create_time` datetime NOT NULL COMMENT '入组时间',
  PRIMARY KEY (`id`),
  KEY `idx_group_id` (`group_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组-用户关联表，记录群和用户关系';

/*Table structure for table `tb_message` */

DROP TABLE IF EXISTS `tb_message`;

CREATE TABLE `tb_message` (
  `id` varchar(48) NOT NULL,
  `receiver` int(11) NOT NULL COMMENT 'user表id，接收者',
  `title` varchar(48) DEFAULT NULL COMMENT '标题',
  `content` varchar(256) DEFAULT NULL COMMENT '内容',
  `href` varchar(256) DEFAULT NULL COMMENT '跳转链接',
  `type` int(11) DEFAULT NULL COMMENT '消息类型',
  `is_read` tinyint(1) DEFAULT NULL COMMENT '是否已读',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_receiver` (`receiver`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表，简短的，发送给特定用户的';

/*Table structure for table `tb_message_type` */

DROP TABLE IF EXISTS `tb_message_type`;

CREATE TABLE `tb_message_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '类型名',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='消息类型表';

/*Table structure for table `tb_message_user` */

DROP TABLE IF EXISTS `tb_message_user`;

CREATE TABLE `tb_message_user` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `to_user_id` int(11) NOT NULL COMMENT '接收者id，关联user表id',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未读 1已读',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未删除 1已删除',
  PRIMARY KEY (`message_id`),
  KEY `idx_to_user_id` (`to_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息-用户 关联表';

/*Table structure for table `tb_province` */

DROP TABLE IF EXISTS `tb_province`;

CREATE TABLE `tb_province` (
  `code` int(11) NOT NULL COMMENT '省份编码',
  `name` varchar(50) DEFAULT NULL COMMENT '省份名称',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省份信息表';

/*Table structure for table `tb_table` */

DROP TABLE IF EXISTS `tb_table`;

CREATE TABLE `tb_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` int(11) NOT NULL COMMENT '表的所有(创建)者.user表id',
  `name` varchar(100) DEFAULT NULL COMMENT '表名',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `start_time` datetime DEFAULT NULL COMMENT '最早开始填写时间',
  `end_time` datetime DEFAULT NULL COMMENT '截止时间',
  `visibility` tinyint(1) DEFAULT '1' COMMENT '表内数据是否对允许填写人公开展示,默认展示',
  `state` varchar(10) DEFAULT 'EDIT' COMMENT '状态：设计中、已发布、已结束、已删除...',
  `filled_num` int(11) DEFAULT '0' COMMENT '当前填写次数，冗余字段需要定时同步，或考虑去除',
  `max_fill_num` int(11) DEFAULT '100' COMMENT '最大可填写次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `detail` text COMMENT '简介',
  `secret_key` varchar(4) DEFAULT NULL COMMENT '访问秘钥',
  PRIMARY KEY (`id`),
  KEY `idx_owner` (`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='信息收集表';

/*Table structure for table `tb_table_group_white_list` */

DROP TABLE IF EXISTS `tb_table_group_white_list`;

CREATE TABLE `tb_table_group_white_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL COMMENT '关联table表主键',
  `group_id` int(11) NOT NULL COMMENT '关联group表主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `coverage_index` (`table_id`,`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='表-组白名单 关联表';

/*Table structure for table `tb_unfilled_record` */

DROP TABLE IF EXISTS `tb_unfilled_record`;

CREATE TABLE `tb_unfilled_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'user表id',
  `table_id` int(11) NOT NULL COMMENT 'table表id',
  `table_name` varchar(100) DEFAULT NULL COMMENT '表名，冗余字段，但不需要同步',
  `is_filled` tinyint(1) DEFAULT NULL COMMENT '是否填写',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '删除标记（删除该提醒）',
  `create_time` datetime DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='记录用户要填写的表';

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `sex` varchar(2) DEFAULT '空' COMMENT '性别',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `head_image` varchar(100) DEFAULT '/AdminLTE/dist/img/user2-160x160.jpg' COMMENT '头像',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证',
  `tel` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq号',
  `home` int(10) DEFAULT NULL COMMENT '地址',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标记位',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=268 DEFAULT CHARSET=utf8 COMMENT='用户表，后期可以将该表分为用户表和person表，person表代表现实中真正存在的人，user表仅表示平台用户';

/*Table structure for table `tb_user_cookie` */

DROP TABLE IF EXISTS `tb_user_cookie`;

CREATE TABLE `tb_user_cookie` (
  `user_id` int(11) NOT NULL COMMENT 'user表id',
  `cookie` varchar(32) NOT NULL COMMENT 'cookie(32位uuid )',
  `expiration_date` datetime NOT NULL COMMENT '失效日期',
  PRIMARY KEY (`user_id`),
  KEY `idx_cookie` (`cookie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户cookie表，记录该对应以方便用户下次自动登录';

/*Table structure for table `tb_user_settings` */

DROP TABLE IF EXISTS `tb_user_settings`;

CREATE TABLE `tb_user_settings` (
  `user_id` int(11) NOT NULL COMMENT 'user表id',
  `reply_notify` tinyint(1) DEFAULT '1' COMMENT '他人回复我时通知我',
  `omment_notify` tinyint(1) DEFAULT '1' COMMENT '他人对我的表提问 & 评论时通知我',
  `fill_notify` tinyint(1) DEFAULT '1' COMMENT '他人填写我的收集表后提醒我',
  `end_notify` tinyint(1) DEFAULT '1' COMMENT '收集表即将截止时提醒我',
  `completion_info` tinyint(1) DEFAULT '1' COMMENT '我在填表时，自动填充我的个人信息',
  `auto_like` tinyint(1) DEFAULT '1' COMMENT '自动添加浏览过的收集表至待填写',
  `auto_unlike` tinyint(1) DEFAULT '1' COMMENT '自动删除我的收藏中不可访问的表',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存用户的个性化设置，作为user表的扩展表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
