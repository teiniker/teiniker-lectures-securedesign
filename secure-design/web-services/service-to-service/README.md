# Securing Communication Between Microservices and the API Gateway 

## Securing the Communication
We have to consider what happens if someone accesses the microservice directly,
**bypassing the API gateway** layer.

There are three common ways to secure communications among services in a microservices deployment:
* **Trust the Network**: No security is enforced in service-to-service communication. 
  This model relies on network-level security which must guarantee that no attacker can intercept 
  communications among services.  

* **Mutual TLS**: Each microservice in the deployment has to carry a public/private key pair and uses 
  that key pair to authenticate to the receiver microservice via mTLS. 
  Challenges in mTLS include bootstrapping trust and key/certificates management.\
  _Example_: [Using OpenSSL](../api-security-basics/api-authentication/mTLS/OpenSSL)\
  _Exercise_: [Mutual Transport Layer Security](../api-security-basics/api-authentication/mTLS/SpringBoot-ArticleService-mTLS-Exercise) ([Model Solution](../api-security-basics/api-authentication/mTLS/SpringBoot-ArticleService-mTLS))

* **JSON Web Tokens**: JWT works at the application layer and is a container that carry a set of claims 
  (end-user attributes) and is signed by the issuer.


## Microservices using gRPC 

gRPC is a high performance, open source RPC framework which is based on a client-server model of **remote procedure calls**. A client application can directly call methods on a server application as if it was a local object.

* [gRPC Overview](gRPC/introduction)

### Protocol Buffers
* Example: [ProtocolBuffers](gRPC/gRPC-ProtocolBuffers)
* Example: [ProtocolBuffers using protoc](gRPC/gRPC-ProtocolBuffers-protoc)
* Exercise: [gRPC-ProtocolBuffers-Order](gRPC/gRPC-ProtocolBuffers-Order-Exercise) ([Model Solution](gRPC/gRPC-ProtocolBuffers-Order-Exercise))

### gRPC Services
* Example: [ClientServer](gRPC/gRPC-ClientServer)
* Example: [ClientServer using mTLS](gRPC/gRPC-ClientServer-mTLS)
* Exercise: [SensorService](gRPC/gRPC-SensorService-Exercise) ([Model Solution](gRPC/gRPC-SensorService-mTLS)) 
* Exercise: [ArticleService](gRPC/gRPC-ArticleService-Exercise) ([Model Solution](gRPC/gRPC-ArticleService-mTLS))
* Example: [Server-Side Streaming](gRPC/gRPC-SensorService-Stream)


## References
* Kasun Indrasiri, Danesh Kuruppu. **gRPC: Up and Running**. O'Reilly Media, 2020

* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020

* Sourabh Sharma. **Modern API Development with Spring and Spring Boot: Design highly scalable and maintainable APIs with REST, gRPC, GraphQL, 
	and the reactive paradigm**.  Packt Publishing, 2021 
  
*Egon Teiniker, 2016-2022, GPL v3.0*
