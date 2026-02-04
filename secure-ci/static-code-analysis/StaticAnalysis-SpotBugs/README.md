# SpotBugs

SpotBugs is a program to find bugs in Java programs. It looks for instances of **bug patterns** — 
code instances that are likely to be errors.

## Setup

Download and unzip  [SpotBugs](https://spotbugs.readthedocs.io/en/stable/installing.html).

Rename `spotbugs.properties.template` to `spotbugs.properties` and set the **spotbugs.home** property 
to your install directory of SpotBugs.

_Example_: spotbugs.properties
```
# Project to analyze
project.home = ../../risk-analysis/VulnerableWebApplication
spotbugs.src = ${project.home}/src/main/java
spotbugs.test = ${project.home}/src/test/java
spotbugs.build = ${project.home}/target/classes

# Tool Settings
spotbugs.home=/home/student/local/spotbugs-4.9.7/
spotbugs.output.format = text
spotbugs.output.file = reports/spotbugs-report.txt
```

## SpotBugs GUI

For a first look, we can run SpotBugs with it's own GUI in a standalone mode:
```
$ cd spotbugs-x.y.z/
$ bin/spotbugs
```

## SpotBugs Ant Task

In this project, we use the Ant build tool to run SpotBugs because we analyze a different project, see: **project.home**.
The project is only used to configure the analysis tool and to store the reports.
```
$ ant
$ less reports/spotbugs-report.txt
```

## Find Security Bugs

Find Security Bugs is a SpotBugs plugin for security audits of Java web applications.
It can detect 138 different vulnerability types with over 820 unique API signatures.

Download [Find Security Bugs](https://find-sec-bugs.github.io/) and copy the JAR file into the 
`spotbugs-x.y.z/plugin` folder.

If you run SpotBugs again, you will see the additional warnings in your report file.


## References
* [SpotBugs Download](https://spotbugs.readthedocs.io/en/stable/installing.html)
* [Using the SpotBugs Ant task](https://spotbugs.readthedocs.io/en/latest/ant.html)
* [Plugin: find-sec-bugs](https://find-sec-bugs.github.io/)
