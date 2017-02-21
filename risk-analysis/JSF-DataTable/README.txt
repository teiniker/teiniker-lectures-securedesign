How to setup the database?
-------------------------------------------------------------------------------

1. Start DB Server

$ su
root66
# systemctl start mariadb.service


2. Create table and insert test data

$ cd JSF-DataTable
$ mysql -u student -p 
student
> use testdb;
> source sql/createTable.sql

Tipp: Have a look into src/main/resources/META-INF/persistence.xml


How to start the Web application from the command line?
-------------------------------------------------------------------------------

$ cd JSF-DataTable
$ mvn wildfly:run


How to access the Web application?
-------------------------------------------------------------------------------

URL: http://localhost:8080/JSF-DataTable

