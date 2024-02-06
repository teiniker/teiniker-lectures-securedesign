# SonarQube

SonarQube is an automatic code review tool to detect bugs, vulnerabilities and
code smells in your code.

## Setup 

First, download [SonarQube](https://www.sonarqube.org/downloads/), unzip the folder and move it into
the installation directory, e.g. `/home/student/local`:
```
$ cd Downloads 
$ unzip sonarqube-8.9.10.61524.zip
$ mv sonarqube-8.9.10.61524 ../local/sonarqube-8.9.10
```

Second, download [SonarScanner](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/) Linux-64-bit:
```
$ cd Download 
$ unzip sonar-scanner-cli-4.7.0.2747-linux.zip
$ mv sonar-scanner-4.7.0.2747-linux ../local/sonar-scanner-4.7.0

$ vi ~/.bashrc
export SONAR_SCANNER=/home/student/local/sonar-scanner-4.7.0
add $SONAR_SCANNER/bin: to the $PATH 

$ sonar-scanner --version
INFO: Scanner configuration file: /home/student/local/sonar-scanner-4.7.0/conf/sonar-scanner.properties
INFO: Project root configuration file: NONE
INFO: SonarScanner 4.6.2.2472
INFO: Java 11.0.11 AdoptOpenJDK (64-bit)
INFO: Linux 5.10.0-8-amd64 amd64
```
Note that version numbers can change in newer versions...


## Start and Stop SonarQube
Before we can run an analysis, we have to fire up the SonarQube server process.

*Note that SonarQube is now working with Java 17!!*

```
$ cd local/sonarqube-8.9.10/bin/linux-x86-64/
$ ./sonar.sh start

$ ./sonar.sh stop
```

To access the SonarQube server from a browser, use the following link:
```
URL: http://localhost:9000

Log In to SonarQube
=> admin/admin
=> change to amin/root66
```
Also, we add a new account which can be accessed via **SonarScanner**:
```
Administration/Security
[Create User]
student/student
```

## Project Configuration

In order to analyze an existing project, add a **sonar-project.properties** file
to the target project.

```
# must be unique in a given SonarQube instance
sonar.projectKey=VulnerableWebApplication

# this is the name and version displayed in the SonarQube UI. Was mandatory prior to SonarQube 6.1.
sonar.projectName=VulnerableWebApplication
sonar.projectVersion=1.0
 
# Path is relative to the sonar-project.properties file. Replace "\" by "/" on Windows.
# This property is optional if sonar.modules is set. 
sonar.sources=src
sonar.java.binaries=target/classes

# SonarQube account
sonar.login=student
sonar.password=student
```

Within the **target project folder** run the SonarScanner:
```
$ sonar-scanner
```
After some seconds, the measurements can be reviewed on the SonarQube server.

##Rule Sets
SonarQube supports many languages and for each language there exists rules sets which
can also be configured. We can disable and enable single rules. 

## References
* [SonarQube](https://www.sonarqube.org/)
* [SonarScanner](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/)
* [Rule Sets](https://rules.sonarsource.com/)