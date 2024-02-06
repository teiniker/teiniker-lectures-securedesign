Configuring Wildfly Logging
-------------------------------------------------------------------------------

The Wildfly logging framework is build around three main concepts: loggers,
handlers and formatters.

Loggers are normally named using a hierarchical dot-separated namespace.
At the top of the hierarchy there's the "root-logger".

In the default server configuration, the root-logger defines two handlers that
are connected to CONSOLE and to the FILE handler.


How to activate common logging?
-------------------------------------------------------------------------------
Add the marked line to the undertow subsystem in the standalone.xml

<subsystem xmlns="urn:jboss:domain:undertow:6.0" default-server="default-server"
    default-virtual-host="default-host" default-servlet-container="default"
    default-security-domain="other">
    <buffer-cache name="default"/>
    <server name="default-server">
        <http-listener name="default" socket-binding="http" redirect-socket="https" enable-http2="true"/>
        <https-listener name="https" socket-binding="https" security-realm="CertificateRealm" enable-http2="true"/>
        <host name="default-host" alias="localhost">
            <location name="/" handler="welcome-content"/>
            <access-log pattern="common" directory="${jboss.home.dir}/standalone/log" prefix="access"/>  <!-- Add this line -->
            <http-invoker security-realm="ApplicationRealm"/>
        </host>
    ...
</subsystem>

The following configuration forces Wildfy to use the common log format and write a
logfile with the prefix "access" into the standalone/log directory:

    <access-log pattern="common" directory="${jboss.home.dir}/standalone/log" prefix="access"/>

Here are some example log sines from the accesslog file:
127.0.0.1 - - [21/Mar/2019:17:04:05 +0100] "GET /Servlet-SimpleLogin/ HTTP/1.1" 200 1168
127.0.0.1 - - [21/Mar/2019:17:04:06 +0100] "GET /favicon.ico HTTP/1.1" 200 1150
127.0.0.1 - - [21/Mar/2019:17:04:13 +0100] "POST /Servlet-SimpleLogin/controller HTTP/1.1" 200 126
127.0.0.1 - - [21/Mar/2019:17:04:37 +0100] "POST /Servlet-SimpleLogin/controller HTTP/1.1" 200 160


How to change standalone.xml for a custom application logger?
-------------------------------------------------------------------------------

1) We add a new logger which is related to the package "org.se.lab" because we
are interested in the classes contained in this package. We set the log level
to "DEBUG".

2) To make DEBUG messages visible on the CONSOLE handler, we have to change the
console-handler's log level also to "DEBUG".

Here you can see the final settings:

        <subsystem xmlns="urn:jboss:domain:logging:3.0">
            <console-handler name="CONSOLE">
                <level name="DEBUG"/>  			<!-- Changed from INFO to DEBUG -->
                <formatter>
                    <named-formatter name="COLOR-PATTERN"/>
                </formatter>
            </console-handler>
            <periodic-rotating-file-handler name="FILE" autoflush="true">
                <formatter>
                    <named-formatter name="PATTERN"/>
                </formatter>
                <file relative-to="jboss.server.log.dir" path="server.log"/>
                <suffix value=".yyyy-MM-dd"/>
                <append value="true"/>
            </periodic-rotating-file-handler>
            <logger category="com.arjuna">
                <level name="WARN"/>
            </logger>
            <logger category="org.jboss.as.config">
                <level name="DEBUG"/>
            </logger>
            <logger category="sun.rmi">
                <level name="WARN"/>
            </logger>
            
 <!-- Add new logger BEGIN -->
            <logger category="org.se.lab">
                <level name="DEBUG"/>
            </logger>
 <!-- Add new logger END -->
            
            <root-logger>
                <level name="INFO"/>
                <handlers>
                    <handler name="CONSOLE"/>
                    <handler name="FILE"/>
                </handlers>
            </root-logger>
            <formatter name="PATTERN">
                <pattern-formatter pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n"/>
            </formatter>
            <formatter name="COLOR-PATTERN">
                <pattern-formatter pattern="%K{level}%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%e%n"/>
            </formatter>
        </subsystem>
