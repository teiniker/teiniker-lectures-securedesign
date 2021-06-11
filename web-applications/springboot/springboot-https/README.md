# Spring Boot: HTTPS Connector


## Create a keystore file

```
$ keytool -genkey -keyalg RSA -alias server -keystore server.jks -storepass student -keypass student -validity 365 -keysize 4096 -storetype pkcs12

What is your first and last name?
  [Unknown]:  teini
What is the name of your organizational unit?
  [Unknown]:  ims
What is the name of your organization?
  [Unknown]:  fhj
What is the name of your City or Locality?
  [Unknown]:  kberg
What is the name of your State or Province?
  [Unknown]:  styria
What is the two-letter country code for this unit?
  [Unknown]:  at
Is CN=teini, OU=ims, O=fhj, L=kberg, ST=styria, C=at correct?
  [no]:  y
Generating 4,096 bit RSA key pair and self-signed certificate (SHA384withRSA) with a validity of 365 days
        for: CN=teini, OU=ims, O=fhj, L=kberg, ST=styria, C=at
```

## Access the Web service

```
https://localhost:8443//translate/katze
```

## Access the Web application

```
https://localhost:8443/app?word=pferd
```

```
$ curl -k -i --verbose --proxy localhost:8010 -X GET https://localhost:8443/app?word=pferd
```

*Egon Teiniker, 2020, GPL v3.0*