Servlet: Access Control 
-------------------------------------------------------------------------------


How to access the Web application?
-------------------------------------------------------------------------------
URL: http://localhost:8080/Servlet-AccessControl-Exercise/


How to Generate ApplicationRealm Credentials for Wildfly AS?
-------------------------------------------------------------------------------

Insert the following user and groups:

student=82364171bad00c2c933b216cac1001d4
bart=ef8e7e1ce75f808464fbc0a2f7e6ce50
burns=459d8156e48ce8d8b5b4c90de402d5bf

student=user
bart=user
burns=admin


$ cd JBOSS_HOME/

$ bin/add-user.sh 
What type of user do you wish to add? 
 a) Management User (mgmt-users.properties) 
 b) Application User (application-users.properties)
(a): b

Enter the details of the new user to add.
Using realm 'ApplicationRealm' as discovered from the existing property files.
Username : student

Password recommendations are listed below. To modify these restrictions edit the add-user.properties configuration file.
 - The password should not be one of the following restricted values {root, admin, administrator}
 - The password should contain at least 8 characters, 1 alphabetic character(s), 1 digit(s), 1 non-alphanumeric symbol(s)
 - The password should be different from the username
Password : student
JBAS015269: Password must have at least 8 characters!
Are you sure you want to use the password entered yes/no? yes

Re-enter Password : student 
What groups do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[  ]: user
About to add user 'student' for realm 'ApplicationRealm'
Is this correct yes/no? yes

Added user 'student' to file '/home/student/install/wildfly-8.2.0.Final/standalone/configuration/application-users.properties'
Added user 'student' to file '/home/student/install/wildfly-8.2.0.Final/domain/configuration/application-users.properties'
Added user 'student' with groups user to file '/home/student/install/wildfly-8.2.0.Final/standalone/configuration/application-roles.properties'
Added user 'student' with groups user to file '/home/student/install/wildfly-8.2.0.Final/domain/configuration/application-roles.properties'

Is this new user going to be used for one AS process to connect to another AS process? 
e.g. for a slave host controller connecting to the master or for a Remoting connection for server to server EJB calls.
yes/no? no

 
$ cat application-users.properties 

$ cat application-roles.properties 


How to add security constraints to the Web application?
-------------------------------------------------------------------------------
In the web.xml add the following elements:

	<security-role>
		<role-name>user</role-name>
	</security-role>
	<security-role>
		<role-name>admin</role-name>
	</security-role>
	
To configure the access control for each of these roles add:
	
a) Public pages:
	<security-constraint>
		<web-resource-collection>
			<web-resource-name>public</web-resource-name>
			<url-pattern>/public/*</url-pattern>
			<http-method>GET</http-method>
			<http-method>POST</http-method>
		</web-resource-collection>
		<!-- <auth-constraint> -->
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>
	
b) User's pages:
	<security-constraint>
		<web-resource-collection>
			<web-resource-name>user</web-resource-name>
			<url-pattern>/user/*</url-pattern>
			<http-method>GET</http-method>
			<http-method>POST</http-method>
		</web-resource-collection>
		<auth-constraint>
			<role-name>user</role-name>
		</auth-constraint>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>
	
c) Admin' pages:
	<security-constraint>
		<web-resource-collection>
			<web-resource-name>admin</web-resource-name>
			<url-pattern>/admin/*</url-pattern>
			<http-method>GET</http-method>
			<http-method>POST</http-method>
		</web-resource-collection>
		<auth-constraint>
			<role-name>admin</role-name>
		</auth-constraint>
		<user-data-constraint>
			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
		</user-data-constraint>
	</security-constraint>	

Note that these constraints are only valid for the given HTTP Methods,
there are NO constraints for the other methods - so we better remove 
the <http-method> elements!!!
	