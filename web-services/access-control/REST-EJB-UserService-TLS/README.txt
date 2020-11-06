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

$ curl -v -i -k -X GET https://localhost:8443/REST-EJB-UserService-TLS/v1/users
*   Trying ::1...
* TCP_NODELAY set
* Expire in 149998 ms for 3 (transfer 0x55cb796b1f50)
* Expire in 200 ms for 4 (transfer 0x55cb796b1f50)
* connect to ::1 port 8443 failed: Connection refused
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Expire in 149998 ms for 3 (transfer 0x55cb796b1f50)
* Connected to localhost (127.0.0.1) port 8443 (#0)
* ALPN, offering h2
* ALPN, offering http/1.1
* successfully set certificate verify locations:
*   CAfile: none
  CApath: /etc/ssl/certs
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.2 (IN), TLS handshake, Certificate (11):
* TLSv1.2 (IN), TLS handshake, Server key exchange (12):
* TLSv1.2 (IN), TLS handshake, Server finished (14):
* TLSv1.2 (OUT), TLS handshake, Client key exchange (16):
* TLSv1.2 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.2 (OUT), TLS handshake, Finished (20):
* TLSv1.2 (IN), TLS handshake, Finished (20):
* SSL connection using TLSv1.2 / ECDHE-RSA-AES256-GCM-SHA384
* ALPN, server accepted to use h2
* Server certificate:
*  subject: CN=localhost
*  start date: Nov  6 09:43:19 2020 GMT
*  expire date: Nov  4 09:43:19 2030 GMT
*  issuer: CN=localhost
*  SSL certificate verify result: self signed certificate (18), continuing anyway.
* Using HTTP2, server supports multi-use
* Connection state changed (HTTP/2 confirmed)
* Copying HTTP/2 data in stream buffer to connection buffer after upgrade: len=0
* Using Stream ID: 1 (easy handle 0x55cb796b1f50)
> GET /REST-EJB-UserService-TLS/v1/users HTTP/2
> Host: localhost:8443
> User-Agent: curl/7.64.0
> Accept: */*
>
* Connection state changed (MAX_CONCURRENT_STREAMS == 4294967295)!
< HTTP/2 200
HTTP/2 200
< content-type: application/xml;charset=UTF-8
content-type: application/xml;charset=UTF-8
< content-length: 458
content-length: 458
< date: Fri, 06 Nov 2020 10:31:57 GMT
date: Fri, 06 Nov 2020 10:31:57 GMT

<
* Connection #0 to host localhost left intact
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<collection>
    <user id="1">
        <username>homer</username>
        <password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>
    </user>
    <user id="2">
        <username>marge</username>
        <password>xCSuPDv2U6I5jEO1wqvEQ/jPYhY=</password></user>
    <user id="3">
        <username>bart</username>
        <password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password>
    </user>
    <user id="4">
        <username>lisa</username>
        <password>xO0U4gIN1F7bV7X7ovQN2TlSUF4=</password>
    </user>
</collection>

SSL connection using TLSv1.2 / ECDHE-RSA-AES256-GCM-SHA384
TLS 1.2

ECDHE RSA is a method (Ephemeral  Elliptic Curve Diffie-Hellman) to exchange
        secret keys over an insecure channel in order to start encrypted
        communication.

AES 256 GCM is an encryption (cipher) method, using the 256 bit key learned
        over the TLS 1.2 connection using ECDHE RSA.
        GCM is a mode of AES. GCM is an improvement over the old CBC mode.

SHA 384 is a data integrity method to ensure the message has not been
        tampered with by using a calculated 384 bit hash quantity.



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
		


 