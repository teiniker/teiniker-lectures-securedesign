Example: Servler-SSL-Translator
-------------------------------------------------------------------------------

URL: https://localhost:8443/Servlet-SSL-Translator/
http://localhost:8080/Servlet-SSL-Translator/

How to access the Web application from cURL?
--------------------------------------------

$ curl -k -i https://localhost:8443/Servlet-SSL-Translator/
$ curl -k -i -X POST -d 'word=cat&language=Deutsch&action=Translate' https://localhost:8443/Servlet-SSL-Translator/controller


TLC Configuration in web.xml
----------------------------
To support TLS, a Servlet's web.xml deployment descriptor must contain the
following declaration:

		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>

Without it, we can access the Servlet from both HTTPS and HTTP connector at the same time:

$ curl -k -i https://localhost:8443/Servlet-SSL-Translator/

$ curl -i http://localhost:8080/Servlet-SSL-Translator/


How to run Wildfly and deploy the Web application?
--------------------------------------------------

$ cd wildfly-x.y.z.final
$ bin/standalone.sh

Within the project folder:
$ pwd
Servlet-SSL-Translator/

$ mvn wildfly:deploy

$ mvn wildfly:undeploy