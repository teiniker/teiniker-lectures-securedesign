How to configure Wildfly's HTTPS connection?
-------------------------------------------------------------------------------
*) Create a Keystore

$ cd JBOSS_HOME/standalone/configuration
$ keytool -genkeypair -keystore wildfly.keystore -storepass student -keypass student -keyalg RSA -alias wildfly -dname "cn=ims,o=fhj,c=at" 
 
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


TODO: Describe web.xml settings!!!


How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-UserService-TLS/UserService?wsdl

This request will be redirected to:

=> https://localhost:8443/SOAP-EJB-UserService-TLS/UserService?wsdl

$ curl --insecure --verbose https://localhost:8443/SOAP-EJB-UserService-TLS/UserService?wsdl


How to access SOAP WS via using SoapUI?
-------------------------------------------------------------------------------
 	https://localhost:8443/SOAP-EJB-UserService-TLS/UserService?wsdl
	- WSDL can be read
	- Requests can be send (change protocol and port number)

	SOAP Request:
		POST https://localhost:8443/SOAP-EJB-UserService-TLS/UserService HTTP/1.1
		Accept-Encoding: gzip,deflate
		Content-Type: text/xml;charset=UTF-8
		SOAPAction: ""
		Content-Length: 260
		Host: localhost:8443
		Connection: Keep-Alive
		User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
		
		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
		   <soapenv:Header/>
		   <soapenv:Body>
		      <ser:findById>
		         <arg0>1</arg0>
		      </ser:findById>
		   </soapenv:Body>
		</soapenv:Envelope>


	SOAP Response:	
		HTTP/1.1 200 OK
		Connection: keep-alive
		X-Powered-By: Undertow/1
		Server: WildFly/9
		Content-Type: text/xml;charset=UTF-8
		Content-Length: 292
		Date: Tue, 10 Nov 2015 19:30:19 GMT
		
		<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
			<soap:Body>
				<ns2:findByIdResponse xmlns:ns2="http://service.lab.se.org/">
					<return id="1">
						<username>homer</username>
						<password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>
					</return>
				</ns2:findByIdResponse>
			</soap:Body>
		</soap:Envelope>
