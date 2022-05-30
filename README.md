# Java Security Code


Java Security code is a very useful and friendly project for learning Java vulnerability code.


## Introduce

This project can also be called Java vulnerability code. 

Each vulnerability type code has a security vulnerability by default unless there is no vulnerability. The relevant fix code is in the comments or code. Specifically, you can view each vulnerability code and comments.

Login username & password:

```
admin/admin123
pchack/pchack123
```


## Vulnerability Code

Sort by letter.

- [Actuators to RCE](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/resources/logback-online.xml)
- [CommandInject](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/CommandInject.java)
- [CORS](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/CORS.java)
- [CRLF Injection](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/CRLFInjection.java)
- [CSRF](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/security/WebSecurityConfig.java)
- [Deserialize](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/Deserialize.java)
- [Fastjson](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/Fastjson.java)
- [File Upload](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/FileUpload.java)
- [GetRequestURI](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/GetRequestURI.java)
- [IP Forge](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/IPForge.java)
- [Java RMI](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/RMI/Server.java)
- [Log4j](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/Log4j.java)
- [PathTraversal](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/PathTraversal.java)
- [Swagger](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/config/SwaggerConfig.java)
- [SpEL](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/SpEL.java)
- [SQL Injection](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/SQLI.java)
- [SSRF](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/SSRF.java)
- [SSTI](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/SSTI.java)
- [URL Redirect](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/URLRedirect.java)
- [URL whitelist Bypass](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/URLWhiteList.java)
- [xlsxStreamerXXE](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/othervulns/xlsxStreamerXXE.java)
- [XSS](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/XSS.java)
- [XStream](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/XStreamRce.java)
- [XXE](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/XXE.java)



## Vulnerability Description

- [Actuators to RCE](https://github.com/JohnSampath/JavaSecCode/wiki/Actuators-to-RCE)
- [CORS](https://github.com/JohnSampath/JavaSecCode/wiki/CORS)
- [CSRF](https://github.com/JohnSampath/JavaSecCode/wiki/CSRF)
- [Deserialize](https://github.com/JohnSampath/JavaSecCode/wiki/Deserialize)
- [Java RMI](https://github.com/JohnSampath/JavaSecCode/wiki/Java-RMI)
- [SQLI](https://github.com/JohnSampath/JavaSecCode/wiki/SQL-Inject)
- [SSRF](https://github.com/JohnSampath/JavaSecCode/wiki/SSRF)
- [SSTI](https://github.com/JohnSampath/JavaSecCode/wiki/SSTI)
- [URL whitelist Bypass](https://github.com/JohnSampath/JavaSecCode/wiki/URL-whtielist-Bypass)
- [XXE](https://github.com/JohnSampath/JavaSecCode/wiki/XXE)
- [Others](https://github.com/JohnSampath/JavaSecCode/wiki/others)

## How to run

The application will use mybatis auto-injection. Please run mysql server ahead of time and configure the mysql server database's name and username/password except docker environment.

``` 
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

- IDEA/STS
- Tomcat
- JAR

### IDEA

- `git clone https://github.com/JohnSampath/JavaSecCode`
- Open in IDEA and click `run` button.

Example:

```
http://localhost:8080/rce/exec?cmd=whoami
```

return:

```
Viarus
```

### Tomcat

- `git clone https://github.com/JohnSampath/JavaSecCode` & `cd JavaSecCode`
- Build war package by `mvn clean package`.
- Copy war package to tomcat webapps directory.
- Start tomcat application.

Example:

```
http://localhost:8080/java-sec-code-1.0.0/rce/exec?cmd=whoami
```

return:

```
Viarus
```


### JAR

Change `war` to `jar` in `pom.xml`.

```xml
<groupId>sec</groupId>
<artifactId>java-sec-code</artifactId>
<version>1.0.0</version>
<packaging>war</packaging>
```

Build package and run.

```
git clone https://github.com/JohnSampath/JavaSecCode
cd JavaSecCode
mvn clean package -DskipTests 
java -jar target/java-sec-code-1.0.0.jar
```

## Authenticate

### Login

[http://localhost:8080/login](http://localhost:8080/login)

If you are not logged in, accessing any page will redirect you to the login page. The username & password are as follows.

```
admin/admin123
pchack/pchack123
```

### Logout

[http://localhost:8080/logout](http://localhost:8080/logout)

### RememberMe

Tomcat's default JSESSION session is valid for 30 minutes, so a 30-minute non-operational session will expire. In order to solve this problem, the rememberMe function is introduced, and the default expiration time is 2 weeks.


## Contributors
