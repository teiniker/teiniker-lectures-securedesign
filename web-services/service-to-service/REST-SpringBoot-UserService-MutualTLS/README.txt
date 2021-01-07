Mutual TLS
-------------------------------------------------------------------------------
To enable TLS mutual authentication, set the following property in the
application.properties file:
server.ssl.client-auth:need

$ mvn spring-boot:run

$ curl -i -k -X GET https://localhost:8443/user/2
curl: (35) error:14094412:SSL routines:ssl3_read_bytes:sslv3 alert bad certificate

To fix this, we need to create a key pair (a public key and a private key) for
the cURL client and configure the REST service to trust the public key.
To generate a private key and a public key for the cURL client, we use the
following OpenSSL command:
$ openssl genrsa -out privkey.pem 4096

Now, to generate a self-signed certificate, corresponding to the preceding private key
(privkey.pem), use the following OpenSSL command:
$ openssl req -key privkey.pem -new -x509 -sha256 -nodes -out client.crt -subj "/C=at/ST=st/L=kberg/O=fhj/OU=ims/CN=teini"

Import the public certificate (client.crt)
$ keytool -import -file client.crt -alias client -keystore keystore.jks -storepass springboot
Owner: CN=teini, OU=ims, O=fhj, L=kberg, ST=st, C=at
Issuer: CN=teini, OU=ims, O=fhj, L=kberg, ST=st, C=at
Serial number: 2751a5c3052c8f755a8b4ddd63f68a56678f8768
Valid from: Thu Nov 05 17:50:45 CET 2020 until: Sat Dec 05 17:50:45 CET 2020
Certificate fingerprints:
	 SHA1: 3E:FE:F6:AB:7C:42:71:19:06:0E:F5:FD:B7:D2:ED:6B:13:17:47:5B
	 SHA256: 1A:97:D8:54:CA:0A:F9:0A:B3:09:D6:3F:77:27:7F:2E:87:53:87:CA:DA:FC:5A:2B:8E:C8:45:E7:01:A1:A6:00
Signature algorithm name: SHA256withRSA
Subject Public Key Algorithm: 4096-bit RSA key
Version: 3
...

$ keytool -list -keystore keystore.jks
Enter keystore password:
Keystore type: PKCS12
Keystore provider: SUN

Your keystore contains 2 entries

client, Nov 5, 2020, trustedCertEntry,
Certificate fingerprint (SHA-256): 1A:97:D8:54:CA:0A:F9:0A:B3:09:D6:3F:77:27:7F:2E:87:53:87:CA:DA:FC:5A:2B:8E:C8:45:E7:01:A1:A6:00
spring, Oct 29, 2020, PrivateKeyEntry,
Certificate fingerprint (SHA-256): B4:F6:96:79:26:BF:EB:C2:A8:EB:00:04:B3:8D:7D:4C:0A:56:9A:B6:20:97:D5:B8:F8:A1:32:74:BC:FD:B6:B8

Start the REST service:
$ mvn spring-boot:run

$ curl -k --key privkey.pem --cert client.crt -X GET https://localhost:8443/user/2
{"id":2,"username":"marge","password":"xCSuPDv2U6I5jEO1wqvEQ/jPYhY="}

$ curl -v -k --key privkey.pem --cert client.crt -X GET https://localhost:8443/user/2
*   Trying ::1...
* TCP_NODELAY set
* Expire in 149997 ms for 3 (transfer 0x5601b2474f50)
* Expire in 200 ms for 4 (transfer 0x5601b2474f50)
* Connected to localhost (::1) port 8443 (#0)
* ALPN, offering h2
* ALPN, offering http/1.1
* successfully set certificate verify locations:
*   CAfile: none
  CApath: /etc/ssl/certs
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.2 (IN), TLS handshake, Certificate (11):
* TLSv1.2 (IN), TLS handshake, Server key exchange (12):
* TLSv1.2 (IN), TLS handshake, Request CERT (13):
* TLSv1.2 (IN), TLS handshake, Server finished (14):
* TLSv1.2 (OUT), TLS handshake, Certificate (11):
* TLSv1.2 (OUT), TLS handshake, Client key exchange (16):
* TLSv1.2 (OUT), TLS handshake, CERT verify (15):
* TLSv1.2 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.2 (OUT), TLS handshake, Finished (20):
* TLSv1.2 (IN), TLS handshake, Finished (20):
* SSL connection using TLSv1.2 / ECDHE-RSA-AES256-GCM-SHA384
* ALPN, server did not agree to a protocol
* Server certificate:
*  subject: C=us; ST=ca; L=sjc; O=zee; OU=bar; CN=foo
*  start date: Sep  3 22:03:07 2019 GMT
*  expire date: Aug 31 22:03:07 2029 GMT
*  issuer: C=us; ST=ca; L=sjc; O=zee; OU=bar; CN=foo
*  SSL certificate verify result: self signed certificate (18), continuing anyway.
> GET /user/2 HTTP/1.1
> Host: localhost:8443
> User-Agent: curl/7.64.0
> Accept: */*
>
< HTTP/1.1 200
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Fri, 06 Nov 2020 12:01:53 GMT
<
* Connection #0 to host localhost left intact
{"id":2,"username":"marge","password":"xCSuPDv2U6I5jEO1wqvEQ/jPYhY="}
