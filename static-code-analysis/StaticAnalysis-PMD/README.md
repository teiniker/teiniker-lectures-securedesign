# PMD 

PMD is a static source code analyzer. 
It finds common programming flaws like **unused variables**, **empty catch blocks**, 
**unnecessary object creation**, and so forth.

## Setup

Download and unzip [PMD](https://pmd.github.io/pmd-6.36.0/index.html).

Rename `pmd.properties.template` to `pmd.properties` and set the **pmd.home** property to your install directory of PMD

_Example_: pmd.properties
```
# Target Project
project.home = ../../risk-analysis/VulnerableWebApplication
pmd.src = ${project.home}/src/main/java

# PMD Configuration
pmd.home=/home/student/local/pmd-bin-6.36.0/
pmd.output.format = text
pmd.output.file = reports/pmd-report.txt

# CPD Configuration
cpd.tokens=50
cpd.output.file=reports/cpd-report.txt
cpd.output.format=text
```

## PMD Ant Task

In this project, we use the Ant build tool to run PMD because we analyze a different project, see: project.home. 
The project is only used to configure the analysis tool and to store the reports.

$ ant
$ less reports/pmd-report.txt
$ less reports/cpd-report.txt


## References
* [ PMD Source Code Analyzer Project](https://pmd.github.io/pmd-6.36.0/index.html)
