Example: SQL Injection - Login
-------------------------------------------------------------------------------

Start a MySQL client (as student):
$ mysql -u student -p
Enter password: student
MariaDB [(none)]> use testdb;
MariaDB [testdb]> show tables;

MariaDB [testdb]> source sql/createUser.sql;

$ mvn wildfly:deploy

URL: URL: http://localhost:8080/Servlet-SQLi-Login

username: student' OR 1 #
password: blah

see: UserDAOMySQLImpl.isValidUser(String username, String password)
