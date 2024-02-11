How to access the REST API via HTTPS?
-------------------------------------------------------------------------------

$ mvn spring-boot:run

$ curl -i -k -X GET https://localhost:8443/user/2
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 29 Oct 2020 13:46:58 GMT

{"id":2,"username":"marge","password":"xCSuPDv2U6I5jEO1wqvEQ/jPYhY="}


$ curl -i -k -X GET https://localhost:8443/user
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 29 Oct 2020 13:47:21 GMT

[{"id":1,"username":"homer","password":"ijD8qepbRnIsva0kx0cKRCcYysg"},{"id":2,"username":"marge","password":"xCSuPDv2U6I5jEO1wqvEQ/jPYhY="},{"id":3,"username":"bart","password":"Ls4jKY8G2ftFdy/bHTgIaRjID0Q="},{"id":4,"username":"lisa","password":"xO0U4gIN1F7bV7X7ovQN2TlSUF4="}]s


How to generate a keystore file and configure Spring Boot?
-------------------------------------------------------------------------------

$ keytool -genkey -keyalg RSA -alias spring -keystore keystore.jks -storepass springboot -validity 365 -keysize 4096 -storetype pkcs12

resources/application.properties:
server.port=8443
server.ssl.key-store-type=pkcs12
server.ssl.key-store=keystore.jks
server.ssl.key-store-password=springboot
server.ssl.key-password=springboot
server.ssl.key-alias=spring

