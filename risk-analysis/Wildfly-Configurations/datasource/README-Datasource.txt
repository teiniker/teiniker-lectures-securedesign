Connecting Wildfly to a Database
---------------------------------------------------------------------

To allow applications to connect to a database, we need to configure
a datasource. Upon server startup, each datasource is prepopulated 
with a pool of database connections. Applications acquire a database
connection from the pool by doing a JNDI lookup. 


How to install the JDBC driver?
---------------------------------------------------------------------
The recommended way is to install the driver as a module.

Create the directory structure under the modules folder and add in the
JDBC driver as well as the module descriptor file:

	wildfly-x.y.z
	+- modules
		+- system
		+- com
		   +- mysql
		      +- main
		         +- module.xml
		         +- mysql-connector-java-5.1.24-bin.jar
		          
The main folder is where all the key module components are installed
(JDBC driver and the module.xml file).

Note that you can copy paste the com/ folder from this project!



How to configure a datasource in standalone.xml?
---------------------------------------------------------------------

Within the <subsystem xmlns="urn:jboss:domain:datasources:1.0"> add a 
new <datasource> and <driver> element:

	<datasource jndi-name="java:jboss/datasources/MySqlDS" pool-name="MySqlDS" enabled="true" use-java-context="true" use-ccm="true">
    	<connection-url>jdbc:mysql://localhost:3306/testdb</connection-url>
       	<driver>mysql</driver>
        <security>
            <user-name>student</user-name>
            <password>student</password>
        </security>
    </datasource>  

	<driver name="mysql" module="com.mysql">
    	<xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
    </driver>

 
Note that the given standalone/configuration/standalone.xml file 
shows you the relative position of these changes - but it is NOT 
a working standalone.xml!!!
 	
 
How to ckeck if the Datasource is used by the Wildfly server?
---------------------------------------------------------------------

Check the console output, it should include the following lines:

...
WFLYJCA0005: Deploying non-JDBC-compliant driver class com.mysql.jdbc.Driver (version 5.1)
...
WFLYJCA0018: Started Driver service with driver-name = mysql
...
WFLYJCA0001: Bound data source [java:jboss/datasources/MySqlDS]
...

 	 