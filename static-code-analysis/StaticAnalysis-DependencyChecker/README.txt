Apache Maven Dependency Plugin
-------------------------------------------------------------------------------
https://maven.apache.org/plugins/maven-dependency-plugin/index.html

Resolve the lists the dependencies for this project:
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

Displays the dependency tree for this project:
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

Analyze the dependencies of a project and determine which are:
- used and declared
- used and undeclared
- unused and declared
$ mvn dependency:analyze
 Unused declared dependencies found:
    commons-codec:commons-codec:jar:1.10:compile
    org.owasp.encoder:encoder:jar:1.2:compile
    org.springframework.security:spring-security-web:jar:4.0.1.RELEASE:compile
    org.springframework.security:spring-security-config:jar:4.0.1.RELEASE:compile

