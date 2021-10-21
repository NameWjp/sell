## sell
一个简单的商品管理后台模板，包含简单的页面和按钮权限，和商品新增等功能。

## 准备环境
1. JDK 1.8 以上
2. Maven 3.5 以上
3. Mysql 5.7 以上
4. Redis
5. node 8.9 以上

## 运行方式
1. 在数据库新建 sell 库，执行根目录的 sell.sql 文件。
2. 运行管理端和前端代码
3. 使用 admin/123456 账号登录
4. 替换 logback-spring.xml 文件中的日志保存路径
5. 替换 application.yml 中的 upload 里的 path（图片保存路径）