Servlet: Servlet Filter Log (Common Logging Format)
-------------------------------------------------------------------------------


How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-Filter-Log/

 127.0.0.1 - - 09/Mar/2016:07:25:12 +0100 "GET /Servlet-Filter-Log/ HTTP/1.1" 200 933
 127.0.0.1 - - 09/Mar/2016:07:25:19 +0100 "GET /Servlet-Filter-Log/controller?username=student&password=student&action=Login HTTP/1.1" 200 126



How to configure Wildfly to support Common Logging Format?
-------------------------------------------------------------------------------
standalone.xml:

 <subsystem xmlns="urn:jboss:domain:undertow:2.0">
            <buffer-cache name="default"/>
            <server name="default-server">
                <http-listener name="default" socket-binding="http"/>
                <https-listener name="https" socket-binding="https" security-realm="CertificateRealm"/>
                <host name="default-host" alias="localhost">
                    <location name="/" handler="welcome-content"/>
                    <filter-ref name="server-header"/>
                    <filter-ref name="x-powered-by-header"/>
                    <access-log pattern="common" directory="${jboss.home.dir}/standalone/log" prefix="access" />    !!!!!
                </host>
            </server>
            <servlet-container name="default">
                <jsp-config/>
                <websockets/>
            </servlet-container>
            <handlers>
                <file name="welcome-content" path="${jboss.home.dir}/welcome-content"/>
            </handlers>
            <filters>
                <response-header name="server-header" header-name="Server" header-value="WildFly/8"/>
                <response-header name="x-powered-by-header" header-name="X-Powered-By" header-value="Undertow/1"/>
            </filters>
        </subsystem>

standalone/log/access.log:
127.0.0.1 - - [03/Mar/2016:16:34:16 +0100] "GET / HTTP/1.1" 200 2425
127.0.0.1 - - [03/Mar/2016:16:34:29 +0100] "GET /WebApp-Resources/ HTTP/1.1" 200 265
127.0.0.1 - - [03/Mar/2016:16:34:29 +0100] "GET /WebApp-Resources/script.js HTTP/1.1" 200 88
127.0.0.1 - - [03/Mar/2016:16:34:29 +0100] "GET /WebApp-Resources/tux.jpg HTTP/1.1" 200 10521


How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ mvn wildfly:run

Make sure that you have configured the wildfly-maven-plugin:

	<configuration>
		<jbossHome>/home/student/install/wildfly-9.0.1.Final/</jbossHome>
		<port>9990</port>
		<server-config>standalone.xml</server-config>
	</configuration> 
		