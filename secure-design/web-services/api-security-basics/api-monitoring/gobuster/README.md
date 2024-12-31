# Gobuster 

Gobuster is a tool used to brute-force: URIs (directories and files) in web sites, 
DNS subdomains (with wildcard support), Virtual Host names on target web servers, 
Open Amazon S3 buckets, Open Google Cloud buckets and TFTP servers.

Gobuster is useful for pentesters, ethical hackers and forensics experts. 
It also can be used for security tests.


## Setup 

```Bash
$ sudo apt install gobuster
```


## Analyzing a REST Service

This example shows the analysis of a vulnerable SpringBoot service
that provides `/actuator` endpoints without restrictions.

```Bash
$  gobuster dir -u  http://localhost:8080/ -w spring-boot.txt
===============================================================
Gobuster v3.5
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@firefart)
===============================================================
[+] Url:                     http://localhost:8080/
[+] Method:                  GET
[+] Threads:                 10
[+] Wordlist:                spring-boot.txt
[+] Negative Status codes:   404
[+] User Agent:              gobuster/3.5
[+] Timeout:                 10s
===============================================================
2024/12/31 14:39:56 Starting gobuster in directory enumeration mode
===============================================================
/actuator             (Status: 200) [Size: 1831]
/actuator/beans       (Status: 200) [Size: 135649]
/actuator/conditions  (Status: 200) [Size: 136196]
/actuator/env         (Status: 200) [Size: 16845]
/actuator/env/lang    (Status: 200) [Size: 554]
/actuator/env/pwd     (Status: 200) [Size: 797]
/actuator/env/language (Status: 200) [Size: 552]
/actuator/caches      (Status: 200) [Size: 20]
/actuator/env/path    (Status: 200) [Size: 938]
/actuator/env/home    (Status: 200) [Size: 558]
/actuator/health      (Status: 200) [Size: 15]
/actuator/configprops (Status: 200) [Size: 17154]
/actuator/info        (Status: 200) [Size: 2]
/actuator/mappings    (Status: 200) [Size: 24483]
/actuator/loggers     (Status: 200) [Size: 69524]
/actuator/metrics     (Status: 200) [Size: 1545]
/actuator/threaddump  (Status: 200) [Size: 158577]
/actuator/scheduledtasks (Status: 200) [Size: 54]

/actuator/heapdump    (Status: 200) [Size: 64783169]
===============================================================
2024/12/31 14:39:57 Finished
===============================================================
```

We can also use **ZAP as an interception proxy** to analyze the traffic:

```Bash
$ gobuster dir -u  http://localhost:8080/ -w spring-boot.txt --proxy http://localhost:8010
```


## References

* [GitHub: Gobuster](https://github.com/OJ/gobuster)
* [Gobuster Tutorial](https://hackertarget.com/gobuster-tutorial/)

* [GitHub: SecLists](https://github.com/danielmiessler/SecLists/tree/master/Discovery/Web-Content)

*Egon Teiniker, 2016-2024, GPL v3.0*
