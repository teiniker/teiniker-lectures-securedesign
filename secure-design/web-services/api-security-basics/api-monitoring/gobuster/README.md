# Gobuster 

Gobuster is a tool used to brute-force: URIs (directories and files) in web sites, 
DNS subdomains (with wildcard support), Virtual Host names on target web servers, 
Open Amazon S3 buckets, Open Google Cloud buckets and TFTP servers.

Gobuster is useful for pentesters, ethical hackers and forensics experts. 
It also can be used for security tests.


## Analyzing a REST Service

This example shows the analysis of a vulnerable SpringBoot service
that provides `/actuator` endpoints without restrictions.

```Bash
$  gobuster dir -u  http://localhost:8080/ -w wordlist.txt
===============================================================
Gobuster v3.5
by OJ Reeves (@TheColonial) & Christian Mehlmauer (@firefart)
===============================================================
[+] Url:                     http://localhost:8080/
[+] Method:                  GET
[+] Threads:                 10
[+] Wordlist:                wordlist.txt
[+] Negative Status codes:   404
[+] User Agent:              gobuster/3.5
[+] Timeout:                 10s
===============================================================
2024/12/31 14:08:55 Starting gobuster in directory enumeration mode
===============================================================
/actuator/loggers     (Status: 200) [Size: 70960]
/actuator/metrics     (Status: 200) [Size: 1576]
/actuator/beans       (Status: 200) [Size: 135667]
/actuator/info        (Status: 200) [Size: 2]
/actuator/mappings    (Status: 200) [Size: 24483]
/actuator/env         (Status: 200) [Size: 16877]
/actuator/health      (Status: 200) [Size: 15]
/actuator/conditions  (Status: 200) [Size: 136196]
/actuator/configprops (Status: 200) [Size: 17154]
/actuator/threaddump  (Status: 200) [Size: 135292]

/actuator/heapdump    (Status: 200) [Size: 59545838]
===============================================================
2024/12/31 14:08:57 Finished
===============================================================
```



## Setup 

```Bash
$ sudo apt install gobuster
```

## References

* [GitHub: Gobuster](https://github.com/OJ/gobuster)
* [Gobuster Tutorial](https://hackertarget.com/gobuster-tutorial/)

*Egon Teiniker, 2016-2024, GPL v3.0*