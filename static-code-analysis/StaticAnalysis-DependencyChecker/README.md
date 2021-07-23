# OWASP Dependency-Check

The OWASP Top 10 2013 contains a new entry: **A9-Using Components with Known
Vulnerabilities**.


## Setup

Download the Dependency Check [Ant Task](https://owasp.org/www-project-dependency-check/)

Rename `analysis.properties.template` to `analysis.properties` and set the **dependency-check.home** property 
to your install directory of SpotBugs.

Example: analysis.properties
```
# Project to analyze
target.home =/home/student/github/teiniker-lectures-securedesign/risk-analysis/VulnerableWebApplication/target/VulnerableWebApplication/WEB-INF
target.lib = ${target.home}/lib/

# Tool settings
dependency-check.home=/home/student/local/dependency-check-ant/
analysis.reports = reports
```


# Apache Maven Dependency Plugin

The dependency plugin provides the capability to analyze and manipulate artifacts

* **dependency:list** alias for resolve that lists the dependencies for this project.
* **dependency:tree** displays the dependency tree for this project.
* **dependency:analyze** analyzes the dependencies of this project and determines which are: used and declared; used and undeclared; unused and declared.
* see [all options](https://maven.apache.org/plugins/maven-dependency-plugin/index.html)

_Example_: Resolve the list of dependencies for a project
```
$ mvn dependency:list
 The following files have been resolved:
    org.springframework.security:spring-security-core:jar:4.0.1.RELEASE:compile
    log4j:log4j:jar:1.2.17:compile
    org.springframework:spring-context:jar:4.1.6.RELEASE:compile
    org.springframework:spring-core:jar:4.1.6.RELEASE:compile
    javax.servlet:javax.servlet-api:jar:3.1.0:compile
    aopalliance:aopalliance:jar:1.0:compile
    org.springframework:spring-expression:jar:4.1.6.RELEASE:compile
    org.springframework.security:spring-security-config:jar:4.0.1.RELEASE:compile
    org.owasp.encoder:encoder:jar:1.2:compile
    org.springframework:spring-web:jar:4.1.6.RELEASE:compile
    org.springframework:spring-beans:jar:4.1.6.RELEASE:compile
    commons-codec:commons-codec:jar:1.10:compile
    org.springframework.security:spring-security-web:jar:4.0.1.RELEASE:compile
    org.springframework:spring-aop:jar:4.1.6.RELEASE:compile
```

_Example_: Displays the dependency tree of a project
```
$ mvn dependency:tree
 org.se.lab:VulnerableWebApplication:war:0.0.1-SNAPSHOT
 +- log4j:log4j:jar:1.2.17:compile
 +- commons-codec:commons-codec:jar:1.10:compile
 +- org.owasp.encoder:encoder:jar:1.2:compile
 +- org.springframework.security:spring-security-web:jar:4.0.1.RELEASE:compile
 |  +- aopalliance:aopalliance:jar:1.0:compile
 |  +- org.springframework.security:spring-security-core:jar:4.0.1.RELEASE:compile
 |  +- org.springframework:spring-beans:jar:4.1.6.RELEASE:compile
 |  +- org.springframework:spring-context:jar:4.1.6.RELEASE:compile
 |  +- org.springframework:spring-core:jar:4.1.6.RELEASE:compile
 |  +- org.springframework:spring-expression:jar:4.1.6.RELEASE:compile
 |  \- org.springframework:spring-web:jar:4.1.6.RELEASE:compile
 +- org.springframework.security:spring-security-config:jar:4.0.1.RELEASE:compile
 |  \- org.springframework:spring-aop:jar:4.1.6.RELEASE:compile
  \- javax.servlet:javax.servlet-api:jar:3.1.0:compile
```
_Example_: Analyze the dependencies of a project and determine dependencies which are:
```
$ mvn dependency:analyze
 Unused declared dependencies found:
    commons-codec:commons-codec:jar:1.10:compile
    org.owasp.encoder:encoder:jar:1.2:compile
    org.springframework.security:spring-security-web:jar:4.0.1.RELEASE:compile
    org.springframework.security:spring-security-config:jar:4.0.1.RELEASE:compile
```

References:
-----------
* [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/)
* [Apache Maven Dependency Plugin](https://maven.apache.org/plugins/maven-dependency-plugin/index.html)
