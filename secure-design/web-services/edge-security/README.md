# Edge Security 

## API Gateway
The API gateway is an important piece of infrastructure in our microservice architecture,
since it plays a critical role that helps us clearly separate the functional requirements
from nonfunctional ones.

Microservices are behind a set of APIs that is exposed to the outside world via an API
gateway.
The API gateway is the entry point to the microservice deployment, which screens all
incoming messages for security and other QoS features.

One key aspect of microservices best practice is the **single responsibility principle (SRP)**.
Each microservice should be performing only one particular function.
An API gateway helps in **decoupling security from a microservice**.

### Spring Cloud Gateway

**Spring Cloud Gateway is a non-blocking API gateway**. 
When using a non-blocking API gateway, a thread is always available to process the incoming request. 
These request are then processed asynchronously in the background and once completed the response is 
returned. So no incoming request never gets blocked when using Spring Cloud Gateway.

_Examples_:
   * [SpringBoot-APIGateway](SpringBoot-API-Gateway)


### Zuul Gateway
**Zuul is a blocking API Gateway**.
A blocking gateway makes use of as many threads as the number of incoming requests.
So this approach is more resource intensive. If no threads are available to process incoming
request then the request has to wait in queue.


## Authentication on the Edge

**OAuth 2.0** is a mechanism for solving the problems related to providing our
username and password to an application that we don't trust to access our data.

### Spring Security OAuth 
**End of Life Notice** - 
The Spring Security OAuth project has reached end of life and is no longer actively maintained by VMware, Inc.
This project has been replaced by the OAuth2 support provided by Spring Security and Spring Authorization Server.
    
## References
* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020

*Egon Teiniker, 2016-2022, GPL v3.0*
