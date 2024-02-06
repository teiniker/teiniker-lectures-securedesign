# Example: REST Client (via HTTPS + Basic Authentication)

## Setup

First, we deploy a REST service into Wildfly (make sure that you have started Wildfly):
```
$ cd api-security-basics/REST-EJB-UserService-BasicAuth
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

We can execute the `UserServiceJSONTest` class.

For every test we can see how the SQL scripts are started, the TLS connection is established and finally
how the GET request is executed.
After each test, the database table will be removed.

```
SQL> CREATE TABLE user(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,username VARCHAR(32) NOT NULL,password VARCHAR(32) NOT NULL) ENGINE = INNODB
SQL> INSERT INTO user (username, password) VALUES ('homer', 'ijD8qepbRnIsva0kx0cKRCcYysg=')
SQL> INSERT INTO user (username, password) VALUES ('marge', 'xCSuPDv2U6I5jEO1wqvEQ/jPYhY=')
SQL> INSERT INTO user (username, password) VALUES ('bart', 'Ls4jKY8G2ftFdy/bHTgIaRjID0Q=')
SQL> INSERT INTO user (username, password) VALUES ('lisa', 'xO0U4gIN1F7bV7X7ovQN2TlSUF4=')
Response-Content:
[
    {"id":1,"username":"homer","password":"ijD8qepbRnIsva0kx0cKRCcYysg="},
    {"id":2,"username":"marge","password":"xCSuPDv2U6I5jEO1wqvEQ/jPYhY="},
    {"id":3,"username":"bart","password":"Ls4jKY8G2ftFdy/bHTgIaRjID0Q="},
    {"id":4,"username":"lisa","password":"xO0U4gIN1F7bV7X7ovQN2TlSUF4="}
]

SQL> drop table user
```

## References
* [HttpClient with SSL](https://www.baeldung.com/httpclient-ssl)


*Egon Teiniker, 2020-2021, GPL v3.0*