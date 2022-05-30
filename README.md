# Java Sec Code


Java sec code is a very powerful and friendly project for learning Java vulnerability code.


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

- [Actuators to RCE](https://github.com/pchack93/java-sec-code/blob/master/src/main/resources/logback-online.xml)
- [CommandInject](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/CommandInject.java)
- [CORS](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/CORS.java)
- [CRLF Injection](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/CRLFInjection.java)
- [CSRF](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/security/WebSecurityConfig.java)
- [Deserialize](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/Deserialize.java)
- [Fastjson](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/Fastjson.java)
- [File Upload](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/FileUpload.java)
- [GetRequestURI](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/GetRequestURI.java)
- [IP Forge](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/IPForge.java)
- [Java RMI](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/RMI/Server.java)
- [JSONP](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/Jsonp.java)
- [Log4j](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/Log4j.java)
- [ooxmlXXE](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/othervulns/ooxmlXXE.java)
- [PathTraversal](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/PathTraversal.java)
- [RCE](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/Rce.java)
  - Runtime
  - ProcessBuilder
  - ScriptEngine
  - Yaml Deserialize  
  - Groovy
- [Swagger](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/config/SwaggerConfig.java)
- [SpEL](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/SpEL.java)
- [SQL Injection](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/SQLI.java)
- [SSRF](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/SSRF.java)
- [SSTI](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/SSTI.java)
- [URL Redirect](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/URLRedirect.java)
- [URL whitelist Bypass](https://github.com/JohnSampath/JavaSecCode/blob/master/src/main/java/org/pchack/controller/URLWhiteList.java)
- [xlsxStreamerXXE](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/othervulns/xlsxStreamerXXE.java)
- [XSS](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/XSS.java)
- [XStream](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/XStreamRce.java)
- [XXE](https://github.com/pchack93/java-sec-code/blob/master/src/main/java/org/pchack/controller/XXE.java)



## Vulnerability Description

- [Actuators to RCE](https://github.com/pchack93/java-sec-code/wiki/Actuators-to-RCE)
- [CORS](https://github.com/pchack93/java-sec-code/wiki/CORS)
- [CSRF](https://github.com/pchack93/java-sec-code/wiki/CSRF)
- [Deserialize](https://github.com/pchack93/java-sec-code/wiki/Deserialize)
- [Fastjson](https://github.com/pchack93/java-sec-code/wiki/Fastjson)
- [Java RMI](https://github.com/pchack93/java-sec-code/wiki/Java-RMI)
- [JSONP](https://github.com/pchack93/java-sec-code/wiki/JSONP)
- [POI-OOXML XXE](https://github.com/pchack93/java-sec-code/wiki/Poi-ooxml-XXE)
- [SQLI](https://github.com/pchack93/java-sec-code/wiki/SQL-Inject)
- [SSRF](https://github.com/pchack93/java-sec-code/wiki/SSRF)
- [SSTI](https://github.com/pchack93/java-sec-code/wiki/SSTI)
- [URL whitelist Bypass](https://github.com/pchack93/java-sec-code/wiki/URL-whtielist-Bypass)
- [XXE](https://github.com/pchack93/java-sec-code/wiki/XXE)
- [Others](https://github.com/pchack93/java-sec-code/wiki/others)

## How to run

The application will use mybatis auto-injection. Please run mysql server ahead of time and configure the mysql server database's name and username/password except docker environment.

``` 
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/java_sec_code
spring.datasource.username=root
spring.datasource.password=woshishujukumima
```

- Docker
- IDEA
- Tomcat
- JAR

### Docker


Start docker:

``` 
docker-compose pull
docker-compose up
```


Stop docker:

```
docker-compose down
```

Docker's environment:

- Java 1.8.0_102
- Mysql 8.0.17
- Tomcat 8.5.11


### IDEA

- `git clone https://github.com/pchack93/java-sec-code`
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

- `git clone https://github.com/pchack93/java-sec-code` & `cd java-sec-code`
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
git clone https://github.com/pchack93/java-sec-code
cd java-sec-code
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

Core developers : [pchack](https://github.com/pchack93), [liergou9981](https://github.com/liergou9981)
Other developers: [lightless](https://github.com/lightless233),  [Anemone95](https://github.com/Anemone95), [waderwu](https://github.com/waderwu). 


## Donate

If you like the poject, you can donate to support me. With your support, I will be able to make `Java sec code` better 😎.

### Alipay

Scan the QRcode to support `Java sec code`.

<img title="Alipay QRcode" src="https://aliyun-testaaa.oss-cn-shanghai.aliyuncs.com/alipay_qr.png" width="200">
