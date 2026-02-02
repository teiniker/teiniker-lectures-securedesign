Exercise: mTLS - BookService
-------------------------------------------------------------------------------

You are given a BookService:

$ mvn spring-boot:run

$ curl -i http://localhost:8080/books
$ curl -i http://localhost:8080/books/2


A) Transport Layer Security (TLS)
Extend the service to support TLS.

application.properties
server.port=8443
server.ssl.key-store=classpath:server.jks
server.ssl.key-store-type=pkcs12
server.ssl.key-store-password=student
server.ssl.key-password=student
server.ssl.key-alias=server

$ cd src/main/resources
$ keytool -genkeypair -keystore server.jks -storepass student -keypass student -keyalg RSA -alias server -dname "cn=se,o=lab,c=org"

$ curl -ik https://localhost:8443/books
$ curl -ik https://localhost:8443/books/2


B) Mutual TLS 
Extend the service to support mTLS.

application.properties
server.ssl.client-auth:need

$ mkdir client
$ cd client

# Generate private key
$ openssl genrsa -out privkey.pem 4096

# Generate self-signed certificate
$ openssl req -key privkey.pem -new -x509 -sha256 -nodes -out client.crt -subj "/C=de/ST=bremen/L=bremen/O=mks/OU=hsb/CN=teiniker"

# Import the certificate client.crt into the server's keystore
$ keytool -import -file client.crt -alias client -keystore ../src/main/resources/server.jks -storepass student
$ keytool -list -keystore ../src/main/resources/server.jks -storepass student

Add to Application class:

	static
	{
		String path = System.getProperty("user.dir");
		String keystorePath = path + File.separator + "src/main/resources/server.jks";
		File file = new File(keystorePath);
		if (file.exists())
        {
			System.setProperty("javax.net.ssl.trustStore", keystorePath);
			System.setProperty("javax.net.ssl.trustStorePassword", "student");
		}
	}

Start the server:
$ mvn spring-boot:run

Access the service with the curl client:
$ cd client
$ curl -k --key privkey.pem --cert client.crt https://localhost:8443/books
$ curl -k --key privkey.pem --cert client.crt https://localhost:8443/books/2

