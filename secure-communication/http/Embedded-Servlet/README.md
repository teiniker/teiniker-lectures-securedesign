# Embedded Tomcat - Servlet 

```
$ mvn package 
$ java -jar target/Embedded-Servlet.jar
```

## Build Process

The following Maven plug-ins are used to create a JAR file which can be started as a web server hosting
the `SimpleServlet`:

`Example`: JAR file of the embedded Tomcat application
```
$ jar -tf target/Embedded-Servlet.jar 
META-INF/MANIFEST.MF
META-INF/
org/
org/se/
org/se/lab/
org/se/lab/SimpleServlet.class
org/se/lab/EmbeddedTomcatMain.class
META-INF/maven/
META-INF/maven/org.se.lab/
META-INF/maven/org.se.lab/Embedded-Servlet/
META-INF/maven/org.se.lab/Embedded-Servlet/pom.xml
META-INF/maven/org.se.lab/Embedded-Servlet/pom.properties
```

As we can see, the dependencies are not put into the JAR file, but they are referenced from the `MANIFEST.MF` file
as shown in the next example. The `Class-Path` configuration points to the `target/lib/` directory which will be
created during the build process.
The `Main-Class` configuration points to the class containing the application's entry point.

_Example_: Manifest file of the JAR file
```
Manifest-Version: 1.0
Created-By: Maven Jar Plugin 3.2.0
Build-Jdk-Spec: 14
Class-Path: lib/tomcat-annotations-api-9.0.36.jar lib/tomcat-dbcp-9.0.36
 .jar lib/tomcat-juli-9.0.36.jar lib/tomcat-el-api-9.0.36.jar lib/tomcat
 -embed-core-9.0.36.jar lib/tomcat-embed-jasper-9.0.36.jar lib/tomcat-em
 bed-el-9.0.36.jar lib/ecj-3.18.0.jar
Main-Class: org.se.lab.EmbeddedTomcatMain
```

## References
* [tomcat-9-embedded](https://github.com/jyeary/tomcat-9-embedded)

* [Apache Maven Dependency Plugin](https://maven.apache.org/plugins/maven-dependency-plugin/)

* [Apache Maven JAR Plugin](https://maven.apache.org/plugins/maven-jar-plugin/)