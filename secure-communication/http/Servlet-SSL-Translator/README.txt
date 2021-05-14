Example: Servler-SSL-Translator
-------------------------------------------------------------------------------

URL: https://localhost:8443/Servlet-SSL-Translator/


How to access the Web application from cURL?
--------------------------------------------

$ curl -k -i https://localhost:8443/Servlet-SSL-Translator/
$ curl -k -i -X POST -d 'word=cat&language=Deutsch&action=Translate' https://localhost:8443/Servlet-SSL-Translator/controller


How to run Wildfly and deploy the Web application?
--------------------------------------------------

$ cd wildfly-x.y.z.final
$ bin/standalone.sh

Within the project folder:
$ pwd
Servlet-SSL-Translator/

$ mvn wildfly:deploy

$ mvn wildfly:undeploy