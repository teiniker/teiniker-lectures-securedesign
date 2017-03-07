Wildfly: Form-Based Authentication (ApplicationRealm) 
===============================================================================

The Java EE security model is declarative. The term declarative here means
that you describe the security roles and permissions in a standard XML descriptor.

The default implementation of the declarative security model is based on Java
Authentication and Authorization Service (JAAS) login modules ans subjects.


How to Generate ApplicationRealm Credentials for Wildfly AS?
-------------------------------------------------------------------------------

$ cd /home/student/install/wildfly-9.0.1.Final/

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

/home/student/install/wildfly-9.0.1.Final/standalone/configuration:
 
$ cat application-users.properties 
# Properties declaration of users for the realm 'ApplicationRealm' which is the default realm
# for application services on a new installation.
student=82364171bad00c2c933b216cac1001d4


$ cat application-roles.properties 
# Properties declaration of users roles for the realm 'ApplicationRealm' which is the default realm
# for application services on a new installation.
student=user


How to Configure ApplicationRealm Authentication for Wildfly AS?
-------------------------------------------------------------------------------
(These are the default settings in the standalone.xml file)

    <management>
        <security-realms>
			...
            <security-realm name="ApplicationRealm">
                <authentication>
                    <local default-user="$local" allowed-users="*" skip-group-loading="true"/>
                    <properties path="application-users.properties" relative-to="jboss.server.config.dir"/>
                </authentication>
                <authorization>
                    <properties path="application-roles.properties" relative-to="jboss.server.config.dir"/>
                </authorization>
            </security-realm>
        </security-realms>


How to Configure Authentication in the Web Application?
-------------------------------------------------------------------------------
(In the given Web application these settings are already done in the web.xml file)

In order for a Web application to use an authentication mechanism, it must be 
configured to do so inside its deployment descriptor web.xml.

This is done by adding <security-constraint> and <login-config> elements to
the <web-app> element.

The <security-constraint> element is used to define a portion of the application 
that is restricted to users belonging to a specific role.

The <url-pattern> element uses URL pattern matching to determine the protected 
portion of the application.

The <role-name> element is used to restrict that portion of the application to
authenticated uses who belong to the "user" role.

	<security-constraint>
		<web-resource-collection>
			<web-resource-name>secure</web-resource-name>
			<url-pattern>/*</url-pattern>
		</web-resource-collection>
		<auth-constraint>
			<role-name>user</role-name>
		</auth-constraint>
	</security-constraint>

The <login-config> element is used to specify how users authenticate with the 
Web application.

<auth-method> determines which of the authentication mechanisms (BASIC, DIGEST, 
FORM, CLIENT-CERT) is used.

<form-login-config> identifies which page in the Web application is used to 
authenticate the user. No page is configured to be displayed when authentication 
succeeds. Instead, the user is presented with the URL that triggered the 
authentication in the first place.

	<login-config>
		<auth-method>FORM</auth-method>
		<form-login-config>
			<form-login-page>/login.html</form-login-page>
			<form-error-page>/error.html</form-error-page>
		</form-login-config>
	</login-config>
	

The Web application security model is build around the concept of users and 
roles. Users are assigned to a role, which determines the resources that the
user is allowed to access.

The advantage of roles is that they enable the Web application to be configured 
independently of the permissions of the users who access the application.
The actual mapping of users to roles can be specified at deployment time - 
and can be changed dynamically without having to change the application code. 

In our example we add the following elements to the deployment descriptor 
web.xml:
	
	<security-role>
		<role-name>user</role-name>
	</security-role>
	 
	 
How to Implement an Authentication Form?
-------------------------------------------------------------------------------
The HTML form used to send the credentials to the server must be configured in 
three specific ways:
a) The value of its <form> element's action attribute must be j_security_check
b) The username must be sent in a field named j_username
c) The password must be sent in a field named j_password

see login.html
 
The error page can contain any HTML that conveys to the user the fact that
the authentication attempt failed.

see error.html

We can also create a page named index.html that contains the main page of the 
application. This page is shown if your authentication is successful. 


How to Access the Web Application from a Browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Wildfly-Authentication-FormBased/


