# SonarQube

SonarQube is an automatic code review tool to detect bugs, vulnerabilities and
code smells in your code.

## Start and Stop SonarQube
Before we can run an analysis, we have to fire up the SonarQube server process.

*Note that SonarQube is only working with Java 11!!*

```
$ cd local/sonarqube-8.0/bin/linux-x86-64/
$ sonar.sh start

$ sonar.sh stop
```

To access the SonarQube server from a browser, use the following link:
```
URL: http://localhost:9000
admin/admin
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

sonar.java.source=1.8
sonar.java.binaries=target/classes
```

Within the target project folder run the sonar-scanner:
```
$ sonar-scanner

```
After  some seconds, the measurements can be reviewed on the SonarQube server.

##Rule Sets
SonarQube supports many languages and for each language there exists rules sets which
can also be configured. We can disable and enable single rules. 

## References

[SonarQube](https://www.sonarqube.org/)
[Rule Sets](https://rules.sonarsource.com/)