# Example: REST Client (via HTTPS)

## Setup

First, we deploy a REST service into Wildfly (make sure that you have started Wildfly):
```
$ cd api-security-basics/REST-EJB-UserService-TLS
$ mvn wildfly:deploy
```

On the client side, we have to check the server-side certificate.
As a shortcut, we reference to the server-side keystore, so we don't have to export the certificate from the 
server and to import it into the client's trust store).
```
$ cat src/test/resources/rest.properties
rest.host=localhost
rest.port=8443
ssl.trustStore=/home/student/local/wildfly-24.0.1.Final/standalone/configuration/application.keystore
```

Also, we have to start the database server.
```
$ sudo systemctl start mariadb.service
```

## Running the Tests

We can execute the `HttpsRestRequestTest` class.

For every test we can see how the SQL scripts are started, the TLS connection is established and finally
how the GET request is executed.
After each test, the database table will be removed.

```
2021-10-22 13:05:35,480 DEBUG org.se.lab.HttpsRestRequestTest.setup: Connect to localhost:8443

SQL> CREATE TABLE user(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,username VARCHAR(32) NOT NULL,password VARCHAR(32) NOT NULL) ENGINE = INNODB
SQL> INSERT INTO user (username, password) VALUES ('homer', 'ijD8qepbRnIsva0kx0cKRCcYysg=')
SQL> INSERT INTO user (username, password) VALUES ('marge', 'xCSuPDv2U6I5jEO1wqvEQ/jPYhY=')
SQL> INSERT INTO user (username, password) VALUES ('bart', 'Ls4jKY8G2ftFdy/bHTgIaRjID0Q=')
SQL> INSERT INTO user (username, password) VALUES ('lisa', 'xO0U4gIN1F7bV7X7ovQN2TlSUF4=')

2021-10-22 13:05:38,379 DEBUG org.se.lab.HttpsRestRequestTest.printHttpsCert: Response Code : 200
2021-10-22 13:05:38,386 DEBUG org.se.lab.HttpsRestRequestTest.printHttpsCert: Cipher Suite : TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
2021-10-22 13:05:38,387 DEBUG org.se.lab.HttpsRestRequestTest.printHttpsCert: Cert Type : X.509
2021-10-22 13:05:38,389 DEBUG org.se.lab.HttpsRestRequestTest.printHttpsCert: Cert Hash Code : 233334933
2021-10-22 13:05:38,390 DEBUG org.se.lab.HttpsRestRequestTest.printHttpsCert: Cert Public Key Algorithm : RSA
2021-10-22 13:05:38,393 DEBUG org.se.lab.HttpsRestRequestTest.printHttpsCert: Cert Public Key Format : X.509

2021-10-22 13:05:38,394 DEBUG org.se.lab.HttpsRestRequestTest.httpsGetRequest: URL: https://localhost:8443/REST-EJB-UserService-TLS/v1/users
2021-10-22 13:05:38,397 DEBUG org.se.lab.HttpsRestRequestTest.httpsGetRequest: Request-Method: GET
2021-10-22 13:05:38,397 DEBUG org.se.lab.HttpsRestRequestTest.httpsGetRequest: Response-Code: 200
2021-10-22 13:05:38,401 DEBUG org.se.lab.HttpsRestRequestTest.httpsGetRequest: Response-Content-Length:458
2021-10-22 13:05:38,404 DEBUG org.se.lab.HttpsRestRequestTest.readResponseContent: Response-Content:
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><collection><user id="1"><username>homer</username><password>ijD8qepbRnIsva0kx0cKRCcYysg=</password></user><user id="2"><username>marge</username><password>xCSuPDv2U6I5jEO1wqvEQ/jPYhY=</password></user><user id="3"><username>bart</username><password>Ls4jKY8G2ftFdy/bHTgIaRjID0Q=</password></user><user id="4"><username>lisa</username><password>xO0U4gIN1F7bV7X7ovQN2TlSUF4=</password></user></collection>

SQL> drop table user
```

## References
* [HttpClient with SSL](https://www.baeldung.com/httpclient-ssl)


*Egon Teiniker, 2020-2021, GPL v3.0*