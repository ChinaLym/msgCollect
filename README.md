
# 新地址：
[https://gitee.com/giteeLym/msgCollect](https://gitee.com/giteeLym/msgCollect)

----

# 部署
## 基础环境
### 必须：
- java 8+						
- maven 3.5.4+	[官网最新地址](http://maven.apache.org/download.cgi)
- mysql 5.5	（数据库版本过高时需要处理 ssh）[官网5.5地址](https://dev.mysql.com/downloads/mysql/5.5.html)
----
### 开发可选：
- IDEA	
- git		 

自行解决如何下载、安装、使用

## 依赖
### 依赖框架
- Spring Boot   整合
- Spring 		松耦合
- Spring MVC	web 请求流程
- Spring Data jpa	数据持久层
- Thymeleaf		模板引擎（类似 jsp，前后分离可另选型）
- log4j			日志框架

### 运行依赖
#### 必须：
- 依赖的JS css 等在src/main/resources/static.rar，需要解压到项目内src/main/resources
- 数据库表结构在 msg_collect.sql，不包含表数据，需要导入数据库中，并修改 src/main/resources/application.yml，将数据库密码等连接参数改为实际数据库
#### 可选：
- msg_collect_files.rar 包含基本的头像，需要解压至任意路径，并修改  src/main/java/com/msgc/config/WebMvcConfig.java 的 FILE_DIR 变量，若无此目录会自动创建
- 日志文件保存目录为 log4j.properties 中的目录，若无此目录会自动创建

## 运行时需要先注册，无内置账号

## 需要修改
- 项目的静态路径 WebMvcConfig.java(com/msgc/config)
- 数据库连接	application.yml
- 日志输出路径 log4j.properties
