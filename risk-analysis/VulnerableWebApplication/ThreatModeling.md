# Threat Modeling

A **threat modeling process** can be divided into three steps:

1. **Understanding the Attacker's View** 
    ([Attack Surface Analysis](https://github.com/teiniker/teiniker-lectures-securedesign/blob/master/risk-analysis/VulnerableWebApplication/AttackSurfaceAnalysis.md))
    * **Entry Points**: Are any locations where data or control transfer between the system being modeled and another system.

    * **Assets**: Are the resources the system has that an attacker might try to modify, steal, or otherwise 
        access or manipulate.

    * **Trust Levels (Roles)**: Which define the privilege that an external entity should have to legitimately 
        use an entry point or functionality at the entry point.

2. **Characterizing the Security of the System**
    * **Define use scenario**s:
        Developers must specify how the system will be used, and also how the system will not be used.        

    * **Identify assumption and dependencies**:
        Developers should collect information such as external dependencies, external and internal security notes, and 
        implementation assumptions.

    * **Model the system**
        * **Decompose the Application** using package-, component-, and class-diagrams. We model the internal structure 
            of an application.
        * **Data flow diagrams (DFDs)** are used to understand the actions a system performs at a given entry point. 
            DFDs are visual representations of how a system process data. 

3. **Determining Threats**
    * **Identify threats**: For each entry point, the developers determine how an attacker might try to affect an asset.

    * **Analyze threats**: Developers can decompose a threat into individual, testable conditions.
        Threats that are not mitigated become vulnerabilities (security bugs) which must be fixed.
    
Threat modeling provides a basis for **assessing the security architecture of a system**, it also can be used as a 
**penetration test plan** and a tool to direct security code reviews.
       
Threat modeling starts with the design of a software system, but threat modeling is not strictly linear 
– it is **iterative**.
The **threat model document** is updated during development phases, when the architecture is done, and when code is complete.


## Decompose the Application

In order to be able to carry out a more detailed analysis, it is necessary to dissect the architecture of the web 
application into its parts. 

As a first step we analyze the **dependencies between packages**:
![Dependency Graph](figures/jdepend-report.png)

In a second step, we use the **class diagram** provides to get an overview of the architectural- and design patterns involved.

![ZAP Proxy](figures/ClassDiagram.png)

After potential **paths from the entry points to the assets** have been found in the models, 
these paths are examined at the source code level.
We have to check which improvements in the design and in the implementation can be carried 
out so that the attacks are no longer possible.

Note that finding paths from the entry points to possible vulnerabilities is 
a **manual activity** that can take a lot of time for larger applications.


## Determining Threats

A popular approach to find threats is to carry out the **OWASP Top 10 attacks**. 
This can be done automatically (active scanner) or manually. 
The aim is to find out whether there are any weak points in the web application.

### Active Scanning

To perform a first quick test we start the active scanner of the **OWASP ZAP** proxy, enter the 
desired **URL** and press the **Attack** button.

URL: http://localhost:8080/VulnerableWebApplication

![ZAP Proxy](figures/ZAP-Analysis.png)

### Manual Tests

URL: http://localhost:8080/VulnerableWebApplication/login.jsp

* **SQL Injection (SQLi)** to bypass authentication: 
    ```
    username: student' #

    username: ' OR 1 #
    ```

* **Reflected Cross Site Scripting (XSS)**
    ```
    username: <script>alert("XSS");</script>
    password: xxx
    ```

* **No HTTPS** 

    All data is sent in plain text from the browser to the web application.


* **Direct Object References**
    ```
    http://localhost:8080/VulnerableWebApplication/table.jsp
    ```
    

* **GET Request**

    Browser: change <form method="POST" ...> to <form method="GET" ...>


* **Database** 

    Password stored in Base64 (without hashing)
```
    +----+-----------+----------+----------+--------------+
    | id | firstname | lastname | username | password     |
    +----+-----------+----------+----------+--------------+
    |  1 | student   | student  | student  | c3R1ZGVudA== |
    +----+-----------+----------+----------+--------------+
```
    


URL: http://localhost:8080/VulnerableWebApplication/table.jsp

* **Stored Cross Site Scripting (XSS)**
    ```
    FirstName: <script>alert("XSS");</script>
    LastName: <script>alert("XSS");</script>
    username: <script>alert("XSS");</script>
    ```

* **Missing Input Validation**
    Try to enter mor than 255 characters for FirstName, LastName, and username...
 


## References
* Adam Shostack. **Threat Modeling – Designing for Security**. Wiley, 2014

* Frank Swiderski, Window Snyder. **Threat Modeling**. Microsoft Press, 2004

* [OWASP: Threat Modeling](https://owasp.org/www-community/Threat_Modeling)

*Egon Teiniker, 2019-2021, GPL v3.0*