# PMD 

PMD is a static source code analyzer. 
It finds common programming flaws like **unused variables**, **empty catch blocks**, 
**unnecessary object creation**, and so forth.

PMD is also shipped with a **Copy & Paste** detector (CPD) which can find duplicated code. 

## Setup

Download and unzip [PMD](https://github.com/pmd/pmd/releases/tag/pmd_releases/).

Rename `pmd.properties.template` to `pmd.properties` and set the **pmd.home** property to your install directory of PMD

_Example_: pmd.properties
```
# Project to analyze
project.home = ../StaticAnalysis-Target
project.src = ${project.home}/src/main/java


# Tool Settings
pmd.home=/home/student/local/pmd-bin-7.21.0

# CPD Configuration
cpd.tokens=25
cpd.output.file=reports/cpd-report.txt
cpd.output.format=text

# PMD Configuration
pmd.output.format = text
pmd.output.file = reports/pmd-report.txt
```

## PMD Ant Task

In this project, we use the Ant build tool to run PMD because we analyze a different project, see: project.home. 
The project is only used to configure the analysis tool and to store the reports.
```
$ ant
$ less reports/pmd-report.txt
$ less reports/cpd-report.txt
```

## References
* [ PMD Source Code Analyzer Project](https://pmd.github.io/pmd-6.36.0/index.html)
* [Finding duplicated code with CPD](https://pmd.github.io/pmd-6.36.0/pmd_userdocs_cpd.html)

*Egon Teiniker, 2017-2026, GPL v3.0*
