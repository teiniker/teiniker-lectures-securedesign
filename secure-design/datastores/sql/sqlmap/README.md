# SQLMap

**sqlmap** is an open-source penetration testing tool used to 
**detect and exploit SQL injection flaws in web applications**. 

By automating the process of identifying vulnerable parameters and 
crafting malicious payloads, it saves security testers a great deal 
of time compared to manual testing. Sqlmap supports various database 
management systems, injection techniques (error-based, union-based, 
time-based, boolean-based, etc.), and can even provide database 
takeover capabilities once a successful injection is discovered.

## Setup 

```bash
$ sudo apt install sqlmap

$ sqlmap --version
```

We can also download the latest version of sqlmap from the 
[sqlmap website](https://sqlmap.org/).


## Parameter Usage

* **--technique**:

    The technique option in sqlmap specifies the **SQL injection techniques** 
    that sqlmap should focus on during its testing:

    * **B: Boolean-based blind SQL injection**

    This technique relies on sending crafted SQL queries that return different 
    results (true or false) depending on whether a specific condition is met. 
    It does not require visible error messages or data output but relies on 
    observing changes in the application's behavior (e.g., different content 
    in the response, page rendering, or HTTP status codes).

    * **T: Time-based blind SQL injection**

    This technique sends SQL queries that include time delays (e.g., SLEEP(5) 
    in MySQL or pg_sleep(5) in PostgreSQL) to infer true/false conditions based 
    on the time it takes for the server to respond. If the response delay matches 
    the query, it indicates a successful injection point.

    By using `--technique=BT`, sqlmap will only attempt boolean-based blind and 
    time-based blind SQL injection methods, ignoring other techniques such as 
    error-based or union-based. This is particularly useful when the application 
    doesn't display error messages or return visible data directly, which is often 
    the case in hardened or production environments.


## References

* [YouTube: How Hackers Exploit SQL Injections And Use SQLmap](https://youtu.be/WsufKF2N6Sg?si=lqXvvIHhq1PSJeRv)

* [SQLMap](https://sqlmap.org/)

* [SQLMap Wiki](https://github.com/sqlmapproject/sqlmap/wiki


*Egon Teiniker, 2017-2025, GPL v3.0*
 