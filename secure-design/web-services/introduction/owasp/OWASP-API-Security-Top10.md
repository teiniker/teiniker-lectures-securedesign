# OWASP API Security Top 10 - 2023

APIs expose application logic and sensitive data such as Personally Identifiable 
Information (PII) and because of this have increasingly become a target for 
attackers. Without secure APIs, rapid innovation would be impossible.

API Security focuses on strategies and solutions to understand and mitigate the 
unique vulnerabilities and security risks of 
**Application Programming Interfaces (APIs)**.


* **API1:2023 - Broken Object Level Authorization**\
    APIs tend to expose endpoints that handle object identifiers, creating 
    a wide attack surface of Object Level Access Control issues. Object 
    level authorization checks should be considered in every function that 
    accesses a data source using an ID from the user. 

* **API2:2023 - Broken Authentication**\
    Authentication mechanisms are often implemented incorrectly, allowing 
    attackers to compromise authentication tokens or to exploit implementation 
    flaws to assume other user’s identities temporarily or permanently. 
    Compromising a system’s ability to identify the client/user, compromises 
    API security overall.

* **API3:2023 - Broken Object Property Level Authorization**\
    This category combines API3:2019 Excessive Data Exposure and API6:2019 
    - Mass Assignment, focusing on the root cause: the lack of or improper 
    authorization validation at the object property level. This leads to 
    information exposure or manipulation by unauthorized parties.

* **API4:2023 - Unrestricted Resource Consumption**\
    Satisfying API requests requires resources such as network bandwidth, CPU, 
    memory, and storage. Other resources such as emails/SMS/phone calls or 
    biometrics validation are made available by service providers via API 
    integrations, and paid for per request. Successful attacks can lead to 
    Denial of Service or an increase of operational costs.

* **API5:2023 - Broken Function Level Authorization**\
    Complex access control policies with different hierarchies, groups, and 
    roles, and an unclear separation between administrative and regular 
    functions, tend to lead to authorization flaws. By exploiting these issues, 
    attackers can gain access to other users’ resources and/or administrative 
    functions.

* **API6:2023 - Unrestricted Access to Sensitive Business Flows**\
    APIs vulnerable to this risk expose a business flow - such as buying a 
    ticket, or posting a comment - without compensating for how the 
    functionality could harm the business if used excessively in an automated 
    manner. This doesn’t necessarily come from implementation bugs.

* **API7:2023 - Server Side Request Forgery**\
    Server-Side Request Forgery (SSRF) flaws can occur when an API is fetching 
    a remote resource without validating the user-supplied URI. This enables an 
    attacker to coerce the application to send a crafted request to an 
    unexpected destination, even when protected by a firewall or a VPN.

* **API8:2023 - Security Misconfiguration**\
    APIs and the systems supporting them typically contain complex configurations, 
    meant to make the APIs more customizable. Software and DevOps engineers can 
    miss these configurations, or don’t follow security best practices when it 
    comes to configuration, opening the door for different types of attacks.

* **API9:2023 - Improper Inventory Management**\
    APIs tend to expose more endpoints than traditional web applications, making 
    proper and updated documentation highly important. A proper inventory of 
    hosts and deployed API versions also are important to mitigate issues such as 
    deprecated API versions and exposed debug endpoints.

* **API10:2023 - Unsafe Consumption of APIs**\
    Developers tend to trust data received from third-party APIs more than user 
    input, and so tend to adopt weaker security standards. In order to compromise APIs, 
    attackers go after integrated third-party services instead of trying to compromise 
    the target API directly.

## References
* [OWASP API Security Project](https://owasp.org/www-project-api-security/) 

*Egon Teiniker, 2020-2023, GPL v3.0*	