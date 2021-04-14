# Attack Surface Analysis

Remember, the attack surface is the sum of all **paths for data and commands into and out (entry and exit points)** 
of the application as well as all **valuable data (assets)** used in the application.

Also, we focus on different types of users - at least: **anonymous users** and **admin users**.

## Mapping the Attack Surface

We start with a simple picture of the system:

![]()

Based on these notes, we start with spidering the given web application.

```
URL: http://localhost:8080/VulnerableWebApplication
```

### Web Application Spider

We use **OWASP ZAP** to explore the web application's surface.
ZAP is an interception proxy which records all interactions between the browser and the web application.

#### Automated Spider

An automated spider works **like a web crawler** starting from a given page and following all links to search for
documents.

![]()

#### Manual Spider 

The manual spider requires a **user to interact with the web application** while the ZAP proxy is recording HTTP
requests.
Based on these recordings, a tree structure of sites is generated showing used HTTP methods and their parameters.

This is the most effective way to analyze a web application's sites because a user can provide the right data
(e.g. authentication) and executes the existing workflows in a proper way.
 
![]()

## References
* [OWASP Zed Attack Proxy (ZAP)](https://www.zaproxy.org/)
* [Youtube: ZAP Deep Dive: Exploring with the Standard Spider](https://youtu.be/mz2nhYpU-sw) 

*Egon Teiniker, 2019-2021, GPL v3.0*