Servlet: Access Control 
-------------------------------------------------------------------------------
https://blogs.oracle.com/SureshMandalapu/entry/declarative_compared_to_programmatic_security


How to access the Web application?
-------------------------------------------------------------------------------
URL: https://localhost:8443/Servlet-AccessControl/

student as user
teacher as admin


How to configure access control in the Web application?
-------------------------------------------------------------------------------

This is a so called "Declarative Access Control" based on web.xml configurations.



How to Generate ApplicationRealm Credentials for Wildfly AS?
-------------------------------------------------------------------------------

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
# Properties declaration of users for the realm 'ApplicationRealm' which is the default realm
# for application services on a new installation.
student=82364171bad00c2c933b216cac1001d4


$ cat application-roles.properties 
# Properties declaration of users roles for the realm 'ApplicationRealm' which is the default realm
# for application services on a new installation.
student=user

