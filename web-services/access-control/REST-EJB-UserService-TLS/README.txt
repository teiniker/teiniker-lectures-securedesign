How to access the REST resource?
-------------------------------------------------------------------------------

URL: http://localhost:8080/REST-EJB-UserService-TLS/v1/users

=> https://localhost:8443/REST-EJB-UserService-TLS/v1/users

$ curl -i -X GET http://localhost:8080/REST-EJB-UserService-TLS/v1/users
HTTP/1.1 302 Found
Connection: keep-alive
Location: https://localhost:8443/REST-EJB-UserService-TLS/v1/users
Content-Length: 0
Date: Thu, 29 Oct 2020 08:55:59 GMT

$ curl -i -k -X GET https://localhost:8443/REST-EJB-UserService-TLS/v1/users


How to configure Wildfly's HTTPS connection?
-------------------------------------------------------------------------------
*) Create a Keystore

$ cd JBOSS_HOME/standalone/configuration
$ keytool -genkeypair -keystore wildfly.keystore -storepass student -keypass student -keyalg RSA -alias wildfly -dname "cn=ims,o=fhj,c=at" 

$ keytool -genkeypair -keystore keystore.jks -storeType jks -storepass student -alias spring -keypass student -keyalg RSA -keysize 4096 -validity 3650 -dname "cn=ims,o=fhj,c=at"



*) Configure Wildfly (standalone.xml):

        <management>
        <security-realms>
			...
            
            <!-- SSL/TLS Configuration (BEGIN) --> 
			<security-realm name="CertificateRealm">
				<server-identities>
					<ssl>
						<keystore path="wildfly.keystore" relative-to="jboss.server.config.dir" keystore-password="student" />
					</ssl>
				</server-identities>
			</security-realm>
			<!-- SSL/TLS Configuration (END) -->

        </security-realms>

	
       <subsystem xmlns="urn:jboss:domain:undertow:2.0">
            <buffer-cache name="default"/>
            <server name="default-server">
                <http-listener name="default" socket-binding="http" redirect-socket="https"/>
				
				<!-- SSL/TLS Configuration (BEGIN) -->  
				<https-listener name="https" socket-binding="https" security-realm="CertificateRealm"/>	
                <!-- SSL/TLS Configuration (END) -->
                
                <host name="default-host" alias="localhost">
                    <location name="/" handler="welcome-content"/>
                    <filter-ref name="server-header"/>
                    <filter-ref name="x-powered-by-header"/>
                </host>
            </server>
			...
        </subsystem>

*) Restart Wildfly
	=> Undertow HTTPS listener https listening on localhost/127.0.0.1:8443
	
	
How to configure a WS application to use TLS?
-------------------------------------------------------------------------------

src/main/webapp/WEB-INF/web.xml

	<security-constraint>
		<web-resource-collection>
			<web-resource-name>All resources</web-resource-name>
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>
		


 