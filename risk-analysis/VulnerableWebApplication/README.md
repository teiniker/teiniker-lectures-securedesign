# Vulnerable Web Application

In order to be able to try out the present example, we have to carry out a few setup steps.

## Setup the Database Server
Start the MySQL server (in the VM, the database server is already running):
```
[student@localhost ~]$ sudo systemctl start mariadb.service 
```
 
Start a MySQL client (as student):
```
$ mysql -u student -p
Enter password: student
MariaDB [(none)]> use testdb;
MariaDB [testdb]> show tables;

MariaDB [testdb]> source sql/createUser.sql;	
```
We can also copy and paste the SQL commands from the SQL file into the client terminal.


## Setup the Application Server

In order to be able to access the database from the web application, we need a **DataSource**.
The following steps are necessary once to create a DataSource for the MariaDB database. 
All other applications can use these settings.

See: [Wildfly-Configurations](https://github.com/teiniker/teiniker-lectures-securedesign/tree/master/risk-analysis/VulnerableWebApplication)


**Start Wildfly** in a terminal (as student):
```
$ cd install/wildfly-x.y.z.Final
$ bin/standalone.sh
```

## Deploy the Web Application

Within the project directory invoke **Maven**:
```
$ cd teiniker-lectures-securedesign/risk-analysis/VulnerableWebApplication
$ mvn wildfly:deploy
```

## Access the Web Application

Type the following URL into your browser:

URL: http://localhost:8080/VulnerableWebApplication

To log in, use username=**student** password=**student** ;-)


*Egon Teiniker, 2019-2021, GPL v3.0*
