Servlet: Translator
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-Translator/


How to access the Web application from cURL?
--------------------------------------------

$ curl -i http://localhost:8080/Servlet-Translator/
$ curl -i -X POST -d 'word=cat&language=Deutsch&action=Translate' http://localhost:8080/Servlet-Translator/controller


How to run Wildfly and deploy the Web application?
--------------------------------------------------

$ cd wildfly-x.y.z.final
$ bin/standalone.sh

Within the project folder:
$ pwd
Servlet-Translator/
$ mvn wildfly:deploy
