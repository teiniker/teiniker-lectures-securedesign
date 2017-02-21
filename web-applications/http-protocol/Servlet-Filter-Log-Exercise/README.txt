Servlet: Servlet Filter Log (Common Logging Format)
-------------------------------------------------------------------------------


How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-Filter-Log-Exercise/

127.0.0.1 - - 17/Apr/2015:08:48:57 +0200 "GET /Servlet-Filter-Log/controller?username=student&password=student&action=Login HTTP/1.1" 200 127



How to configure Wildfly to support Common Logging Format?
-------------------------------------------------------------------------------
standalone.xml:

 <subsystem xmlns="urn:jboss:domain:undertow:1.2">
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


How to run Wildfly and deploy the Web application?
-------------------------------------------------------------------------------

$ mvn wildfly:run

Make sure that you have configured the wildfly-maven-plugin:

	<configuration>
		<jbossHome>/home/student/install/wildfly-8.2.0.Final</jbossHome>
		<port>9990</port>
		<server-config>standalone.xml</server-config>
	</configuration> 
		