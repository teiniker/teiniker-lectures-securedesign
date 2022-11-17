# Knowledge for SW Security

Critical software security knowledge and expertise can be compiled:
* OWASP Top 10
* CWE/SANS Top 25

These lists are a **tool for education and awareness** to help programmers to prevent 
the kinds of vulnerabilities by identifying and avoiding all-too-common mistakes 
that occur before software is even shipped. 

## OWASP Top 10 – 2021

* **A01:2021-Broken Access Control**\
    Access control enforces policy such that users cannot act outside of their intended permissions. Failures typically lead to unauthorized information disclosure, modification, or destruction of all data or performing a business function outside the user's limits

* **A02:2021-Cryptographic Failures**\
    The first thing is to determine the protection needs of data in transit and at rest. For example, passwords, credit card numbers, health records, personal information, and business secrets require extra protection. 

* **A03:2021-Injection**\
    An application is vulnerable to attack when User-supplied data is not validated, filtered, or sanitized by the application. Some of the more common injections are SQL, NoSQL, OS command, Object Relational Mapping (ORM), and LDAP injection.

* **A04:2021-Insecure Design**\
    Secure design is a culture and methodology that constantly evaluates threats and ensures that code is robustly designed and tested to prevent known attack methods. Threat modeling should be integrated into refinement sessions (or similar activities); look for changes in data flows and access control or other security controls.

* **A05:2021-Security Misconfiguration**\
    The application might be vulnerable if the application is missing appropriate security hardening across any part of the application stack or improperly configured permissions on cloud services. Unnecessary features are enabled or installed (e.g., unnecessary ports, services, pages, accounts, or privileges). Or default accounts and their passwords are still enabled and unchanged.

* **A06:2021-Vulnerable and Outdated Components**\
    You are likely vulnerable if you do not know the versions of all components you use (both client-side and server-side). This includes components you directly use as well as nested dependencies.

* **A07:2021-Identification and Authentication Failures**\
    Confirmation of the user's identity, authentication, and session management is critical to protect against authentication-related attacks. Authentication weaknesses include default, weak, or well-known passwords, such as "Password1" or "admin/admin" or permiting brute force or other automated attacks. 

* **A08:2021-Software and Data Integrity Failures**\
    Software and data integrity failures relate to code and infrastructure that does not protect against integrity violations. An example of this is where an application relies upon plugins, libraries, or modules from untrusted sources, repositories, and content delivery networks (CDNs).

* **A09:2021-Security Logging and Monitoring Failures**\
    This category is to help detect, escalate, and respond to active breaches. Without logging and monitoring, breaches cannot be detected. Insufficient logging, detection, monitoring, and active response occurs any time auditable events, such as logins, failed logins, and high-value transactions, are not logged.

* **A10:2021-Server-Side Request Forgery**\
    SSRF flaws occur whenever a web application is fetching a remote resource without validating the user-supplied URL. It allows an attacker to coerce the application to send a crafted request to an unexpected destination, even when protected by a firewall, VPN, or another type of network access control list.


## CWE/SANS Top 25 - 2022
1. Out-of-bounds Write
2. Improper Neutralization of Input During Web Page Generation (**Cross-site Scripting**)
3. Improper Neutralization of Special Elements used in an SQL Command (**SQL Injection**)
4. Improper Input Validation
5. Out-of-bounds Read
6. Improper Neutralization of Special Elements used in an OS Command (**OS Command Injection**)
7. Use After Free
8. Improper Limitation of a Pathname to a Restricted Directory (**Path Traversal**)
9. **Cross-Site Request Forgery (CSRF)**
10. Unrestricted Upload of File with Dangerous Type
11. NULL Pointer Dereference
12. Deserialization of Untrusted Data
13. Integer Overflow or Wraparound
14. Improper Authentication
15. Use of Hard-coded Credentials
16. Missing Authorization
17. Improper Neutralization of Special Elements used in a Command (**Command Injection**)
18. Missing Authentication for Critical Function
19. Improper Restriction of Operations within the Bounds of a Memory Buffer
20. Incorrect Default Permissions
21. **Server-Side Request Forgery (SSRF)**
22. Concurrent Execution using Shared Resource with Improper Synchronization (**Race Condition**)
23. Uncontrolled Resource Consumption
24. Improper Restriction of XML External Entity Reference
25. Improper Control of Generation of Code (**Code Injection**)

## References

* [OWASP Top Ten](https://owasp.org/www-project-top-ten/)
* [2022 CWE Top 25 Most Dangerous Software Weaknesses](https://cwe.mitre.org/top25/archive/2022/2022_cwe_top25.html)
* [Jim Manico - OWASP Top 10 2021](https://youtu.be/iXeQGM_ogfY)

*Egon Teiniker, 2019-2022, GPL v3.0*	