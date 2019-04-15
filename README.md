# 部署
## 依赖
- 依赖的JS css 等在src/main/resources，需要解压到项目内src/main/resources
- msg_collect_files.rar 包含基本的头像，需要解压至任意路径
- 数据库表结构在 msg_collect.sqk，不包含表数据，需要导入数据库中

## 需要修改
- 项目的静态路径 WebMvcConfig.java(com/msgc/config)
- 数据库连接	application.yml
- 日志输出路径 log4j.properties
