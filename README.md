# Secure Design by Example

This repository collects examples, exercises and model solutions 
to give an introduction to secure software design topics in Java.


* **Architectural Risk Analysis**
  * [Case Study: Vulnerable Web Application](risk-analysis/VulnerableWebApplication/)
  * [Knowledge for Software Security](risk-analysis/knowledge-sw-security/README.md)
  * [Secure Design Principles](risk-analysis/secure-design-principles/README.md)

* **Secure Software Design**

  * Secure Web Service Design 
    * [Introduction](secure-design/web-services/introduction/)
      * [RESTful Services](secure-design/web-services/introduction/rest/README.md)
      * [OWASP API Security Top 10](secure-design/web-services/introduction/owasp/OWASP-API-Security-Top10.md)

    * [API Security Basics](secure-design/web-services/api-security-basics/)
    
    * [Edge Security](secure-design/web-services/edge-security/)
    
    * [Service to Service Security](secure-design/web-services/service-to-service/)

  * Web Applications
    * [Introduction](secure-design/web-applications/introduction/)
        * [OWASP Top 10](secure-design/web-applications/introduction/owasp/OWASP-Top10.md)
    
    * Client-Side Controls
      * Example: [SpringBoot-MVC-CaptureDate](secure-design/web-applications/client-side-controls/SpringBoot-MVC-CaptureData/)
      * Example: [SpringBoot-MVC-TransmittingData](secure-design/web-applications/client-side-controls/SpringBoot-MVC-TransmittingData/)

    * Access Control
      * [SpringBoot-MVC-Login](secure-design/web-applications/access-control/SpringBoot-MVC-Login/)

    * Browser-Attack Prevention
      * [XSS Prevention](secure-design/web-applications/browser-attack-prevention/xss-prevention/)
      * [CSRF Prevention](secure-design/web-applications/browser-attack-prevention/csrf-prevention/)

  * Datastores
    * [File Handling](secure-design/datastores/filesystem/)
    * [XML Processing](secure-design/datastores/xml/)
    * [JSON Processing](secure-design/datastores/json/)
    * [SQL Processing](secure-design/datastores/sql/)

* **Secure Continuous Integration**
  * [Introduction](secure-ci/introduction/README.md)
  * [Static Code Analysis](secure-ci/static-code-analysis/)
  * [CI Infrastructure](secure-ci/infrastructure/)

See also: 
[Secure Coding by Exampe](https://github.com/teiniker/teiniker-lectures-securecoding) 

As a development environment, you can use a pre-configured Linux VM image:
[Virtual Lab](https://drive.google.com/drive/folders/1AzsF4Mvh1HJ8k6OW5W5hQ5CF0HdqA51l)

*Egon Teiniker, 2017-2026, GPL v3.0*
