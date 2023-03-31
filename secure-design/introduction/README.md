# Secure Software Design

The [CWE/SANS top 25](https://cwe.mitre.org/top25/archive/2022/2022_cwe_top25.html) 
vulnerabilities from 2022 were categorized for the following subject areas.

## Web Applications and Web Services Security

### Access Control
* 14:CWE-287 Improper Authentication
* 16:CWE-862 Missing Authorization
* 18:CWE-306 Missing Authentication for Critical Function
* 20:CWE-276 Incorrect Default Permissions
* 21:CWE-918 Server-Side Request Forgery (SSRF)

### Browser Attacks 
* 2:CWE-79 Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')
* 9:CWE-352	Cross-Site Request Forgery (CSRF)

## Data Stores

### File Handling 
* 8:CWE-22 Improper Limitation of a Pathname to a Restricted Directory ('Path Traversal')
* 10:CWE-434 Unrestricted Upload of File with Dangerous Type
* 23:CWE-400 Uncontrolled Resource Consumption

### XML Processing 
* 24:CWE-611 Improper Restriction of XML External Entity Reference

### SQL Processing 
* 3:CWE-89 Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')

## Native Components

### C/C++
* 1:CWE-787	Out-of-bounds Write
* 5:CWE-125	Out-of-bounds Read
* 7:CWE-416	Use After Free
* 11:CWE-476 NULL Pointer Dereference
* 13:CWE-190 Integer Overflow or Wraparound
* 19:CWE-119 Improper Restriction of Operations within the Bounds of a Memory Buffer
* 25:CWE-94 Improper Control of Generation of Code ('Code Injection')

### Shell Commands
* 6:CWE-78 Improper Neutralization of Special Elements used in an OS Command ('OS Command Injection')
* 17:CWE-77 Improper Neutralization of Special Elements used in a Command ('Command Injection')

### Current Execution 
* 22: CWE-362 Concurrent Execution using Shared Resource with Improper Synchronization ('Race Condition')


## Secure Coding
Some vulnerabilities are more related to the implementation level and are covered by the 
[Secure Coding Repository](https://github.com/teiniker/teiniker-lectures-securecoding).

### Reverse Engineering
* 15:CWE-798 Use of Hard-coded Credentials

### Encapsulation
* 12:CWE-502 Deserialization of Untrusted Data

### Data Validation 
* 4:CWE-20 Improper Input Validation


## References 
* [2022 CWE Top 25 Most Dangerous Software Weaknesses](https://cwe.mitre.org/top25/archive/2022/2022_cwe_top25.html)

*Egon Teiniker, 2017-2023, GPL v3.0*
