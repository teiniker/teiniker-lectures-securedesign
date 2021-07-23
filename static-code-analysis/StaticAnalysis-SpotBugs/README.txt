Using the SpotBugs Ant task
-------------------------------------------------------------------------------
https://spotbugs.readthedocs.io/en/latest/ant.html

Download Spotbugs from:
https://spotbugs.readthedocs.io/en/stable/installing.html

Change the "spotbugs.home" property in "spotbugs.properties"

Set the "project.home" of the project you want to analyze.

$ ant
$ less reports/spotbugs-report.txt

Add find-sec-bugs
-----------------
https://find-sec-bugs.github.io/

=> copy findsecbugs-plugin-1.11.0.jar into spotbugs-4.3.0/plugin



