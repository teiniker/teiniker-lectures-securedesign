Example: BookService - TLS
-------------------------------------------------------------------------------

application.properties
server.port=8443
server.ssl.key-store=classpath:server.jks
server.ssl.key-store-type=pkcs12
server.ssl.key-store-password=student
server.ssl.key-password=student
server.ssl.key-alias=server

$ cd src/main/resources
$ keytool -genkeypair -keystore server.jks -storepass student -keypass student -keyalg RSA -alias server -dname "cn=se,o=lab,c=org"

$ mvn spring-boot:run

$ curl -ik https://localhost:8443/books
$ curl -ik https://localhost:8443/books/2


