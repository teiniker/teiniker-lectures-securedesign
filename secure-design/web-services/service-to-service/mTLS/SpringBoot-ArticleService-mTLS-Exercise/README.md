# Example: Mutual Transport Layer Security 

In this example, we secure the communications among microservices with mutual Transport Layer Security (mTLS).
mTLS is the most popular option for securing communications between microservices.

We start from a regular service implementation.
```
$ mvn spring-boot:run
```
```
$ curl -i http://localhost:8080/articles
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 26 Nov 2021 12:47:39 GMT

[
  {"id":1,"description":"Design Patterns","price":4295},
  {"id":2,"description":"Effective Java","price":3336}
]
```

## Setup TLS

In order to configure a TLS connector, we have to create a server-side certificate and configure the 
application.properties file.

```
$ cd src/main/resources
$ keytool -genkeypair -keystore server.jks -storepass student -keypass student -keyalg RSA -alias server -dname "cn=se,o=lab,c=org"
```

Change `application.properties`:
```
server.ssl.key-store=classpath:server.jks
server.ssl.key-store-type=pkcs12
server.ssl.key-store-password=student
server.ssl.key-password=student
server.ssl.key-alias=server
server.port=8443
```

Now, we can start the service and access it via HTTPS:
```
$ mvn spring-boot:run
```

```
$ curl -i -k https://localhost:8443/articles
```

## Setup: Mutual TLS

To enable mTLS authentication, add the following property in the
`application.properties` file:
```
server.ssl.client-auth:need
```

We start the server and try to access the service as before:
```
$ mvn spring-boot:run
```

```
$ curl -i -k https://localhost:8443/articles
curl: (56) OpenSSL SSL_read: error:14094412:SSL routines:ssl3_read_bytes:sslv3 alert bad certificate, errno 0
```
The server no longer accepts a request without a client certificate.

To fix this, we need to create a key pair (a public key and a private key) for
the curl client and configure the service to trust the public key.

To **generate a private key and a public key** for the curl client, we use the
following **OpenSSL** command:
```
$ openssl genrsa -out privkey.pem 4096
```

To **generate a self-signed certificate**, corresponding to the preceding private key `privkey.pem`, 
use the following OpenSSL command:
```
$ openssl req -key privkey.pem -new -x509 -sha256 -nodes -out client.crt -subj "/C=at/ST=st/L=kberg/O=fhj/OU=ims/CN=teini"
```

Now we **import the public certificate** client.crt into the server's keystore.
```
$ keytool -import -file client.crt -alias client -keystore src/main/resources/server.jks -storepass student
```

With the following `keytool` command, we **list the content of the keystore** file:
```
$ keytool -list -keystore src/main/resources/server.jks -storepass student
Keystore type: PKCS12
Keystore provider: SUN

Your keystore contains 2 entries

server, Oct 9, 2021, PrivateKeyEntry, 
Certificate fingerprint (SHA-256): 68:85:08:A9:DB:3E:15:CD:00:98:D5:F2:AA:59:D2:E6:67:63:C7:E3:8B:90:06:E3:F2:65:6E:5B:85:CD:14:52
client, Nov 25, 2021, trustedCertEntry, 
Certificate fingerprint (SHA-256): 05:26:E2:2B:94:BB:61:A8:66:80:15:C6:D5:DA:CA:EC:63:B5:58:97:3E:0C:5A:84:32:05:4D:98:07:5B:20:5C
```
We can see that the client certificate is now stored in the keystore file.

Within the **server application**, we have to set the `javax.net.ssl.trustStore` and `javax.net.ssl.trustStorePassword` 
system properties so that they point to the server's keystore file.
Therefore, we add a few lines in a `static` block to the `SpringBootArticleServiceApplication`:
```Java
public class SpringBootArticleServiceApplication
{
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

	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootArticleServiceApplication.class, args);
	}
}
```

We start the server again:
```
$ mvn spring-boot:run
```

## Access the Service Using a Client Certificate

In order to access the service which needs a client certificate, we use the following `curl` command
which provides the `--key` and `--cert` options to the request:
```
$ curl -k --key privkey.pem --cert client.crt -X GET https://localhost:8443/articles/2
{"id":2,"description":"Effective Java","price":3336}
```

* `--key privkey.pem`  specifies the key file or path to the private key.

* `--cert client.crt` specifies the certificate with the corresponding public key.



## References

* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020
  * Chapter 6: Securing east/west traffic with certificates
  
*Egon Teiniker, 2020-2021, GPL v3.0*
