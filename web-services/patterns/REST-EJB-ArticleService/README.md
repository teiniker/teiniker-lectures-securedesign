# Example: REST-EJB-ArticleService

## Database Setup

Make sure that your database server is running:
```
$ sudo systemctl status mariadb.service
```
If the server is not running, type:
```
$ sudo systemctl start mariadb.service
``` 
To stop the server, type:
```
$ sudo systemctl stop mariadb.service
```

After the server is running you can use the **mysql** client to access it:
```
$ mysql -u student -p 
Enter password: 
MariaDB [(none)]> use testdb;
Database changed
MariaDB [testdb]> show tables;
Empty set (0.000 sec)
```
We use the pre-configured **testdb** database and execute the SQL statements 
stored in the **src/test/resources/sql/createTable.sql** file (use copy-paste).

An SQL **select** statement can be used to verify the data in our table:
```
MariaDB [testdb]> select * from article;
+----+------------------------------+-------+
| ID | DESCRIPTION                  | PRICE |
+----+------------------------------+-------+
|  1 | Design Patterns              |  4295 |
|  2 | Effective Java (2nd Edition) |  3336 |
+----+------------------------------+-------+
```
In the same way, we can use the **src/test/resources/sql/dropTable.sql** script
to remove our test data from the database.

## Web Service Deployment

First, we start the application server:
```
$ cd local/wildfly-x.y.z.Final/
$ bin/standalone 
...
WildFly Full x.y.z.Final (WildFly Core x.y.z.Final) started in 14530ms 
...
```
While the application server is running we can always deploy and undeploy our
Web service via Maven:
```
$ cd teiniker-lectures-securedesign/web-services/patterns/
$ mvn wildfly:deploy
...
$ mvn wildfly:undeploy
```

## Using the Web Service via curl

With a simple curl command, we can test our deployed Web service:
 
### Find 
To read all Article data or just a single record, we use **GET** requests:
```
$ curl -i -H "Accept: application/xml" -X GET http://localhost:8080/REST-EJB-ArticleService/v1/articles
HTTP/1.1 200 OK
Connection: keep-alive
Content-Type: application/xml;charset=UTF-8
Content-Length: 420
Date: Fri, 02 Oct 2020 09:34:39 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<collection>
    <article id="1" price="4295">
        <description>Design Patterns</description>
    </article>
    <article id="2" price="3336">
        <description>Effective Java (2nd Edition)</description>
    </article>   
</collection>
```

To specify an Article we pass its **id** as a path parameter:
 
``` 
$ curl -i -H "Accept: application/xml" -X GET http://localhost:8080/REST-EJB-ArticleService/v1/articles/1
HTTP/1.1 200 OK
Connection: keep-alive
Content-Type: application/xml;charset=UTF-8
Content-Length: 136
Date: Fri, 02 Oct 2020 09:37:25 GMT

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<article id="1" price="4295">
    <description>Design Patterns</description>
</article>
``` 

We can also change the representation using the **Accept** header:
```
$ curl -i -H "Accept: application/json" -X GET http://localhost:8080/REST-EJB-ArticleService/v1/articles/1
HTTP/1.1 200 OK
Connection: keep-alive
Content-Type: application/json
Content-Length: 53
Date: Fri, 02 Oct 2020 09:38:54 GMT

{"id":1,"description":"Design Patterns","price":4295}
```

### Insert
To insert data, we have to use a **POST** request with data:
```
$ curl -i -H "Content-Type: application/xml" -d '<article price="4295"><description>Design Patterns</description></article>' -X POST http://localhost:8080/REST-EJB-ArticleService/v1/articles
HTTP/1.1 204 No Content
Date: Fri, 02 Oct 2020 09:43:56 GMT
```

*Egon Teiniker, 2020, GPL v3.0*