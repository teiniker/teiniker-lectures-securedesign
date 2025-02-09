# API Security Basics 

In the following examples, we demonstrate the basic security mechanisms 
for securing APIs. These techniques are mainly used for individual 
services, but can also be combined to secure a complete system.

* **Access Control**
    * Transport Layer Security 
        * Example: [SpringBoot-UserService-TLS](api-tls/SpringBoot-UserService-TLS/)
        * Exercise: [SpringBoot-ArticleService-TLS](api-tls/SpringBoot-ArticleService-TLS-Exercise/) 
        ([Model Solution](api-tls/SpringBoot-ArticleService-TLS/))

     * Authentication
         * [Password Attacks](api-authentication/Password-Attacks/)
         * Example: [SpringBoot-ArticleService-BasicAuth](api-authentication/basic/SpringBoot-ArticleService-BasicAuth)

    * Authorization
        * Service-Level Authorization
            * Example: [SpringBoot-ArticleService-Authorization](api-authorization/SpringBoot-ArticleService-Authorization/)
            * Exercise: [SpringBoot-Book-Autorization](api-authorization/SpringBoot-Book-Authorization-Exercise/)
            ([Model Solution](api-authorization/SpringBoot-ArticleService-Authorization/))

        * Function-Level Authorization
            * Example: [SpringBoot-ArticleService-Authorization-Method](api-authorization/SpringBoot-ArticleService-Authorization-Method/) 

        * Object-Level Authorization
            * [SpringBoot-BookService-UUID](api-authorization/SpringBoot-BookService-UUID/)  

* **API Clients** 
   * Example: [Client-TLS-ArticleService](api-clients/Client-TLS-ArticleService/)
   * Example: [client-TLS-ArticleService-JSON](api-clients/Client-TLS-ArticleService-JSON/) 

* **API Specification**
    * [OpenAPI](api-specifications/OpenAPI/README.md)
    * Example: [SpringBoot-Swagger](api-specifications/SpringBoot-Swagger/)   
    * Example: [SpringBoot-BeanValidation](api-specifications/SpringBoot-BeanValidation/)
    * Exercise: [SpringBoot-BeanValidation-Book](api-specifications/SpringBoot-BeanValidation-Book-Exercise/)
    ([Model Solution](api-specifications/SpringBoot-BeanValidation-Book/))

* **API Monitoring**
    * [Introduction](api-monitoring/README.md)
    * Example: [SpringBoot-Actuator](api-monitoring/SpringBoot-Actuator/)
    * [Gobuster](api-monitoring/gobuster/README.md)

*Egon Teiniker, 2017-2025, GPL v3.0*
