LabExam: API Basic Security
-------------------------------------------------------------------------------

A) TLS
$ curl -i -k https://localhost:8443/books
$ curl -i -k https://localhost:8443/books/2

B) Basic Authentication
$ curl -i -k -u student:student https://localhost:8443/books
$ curl -i -k -u student:student https://localhost:8443/books/1

C) Authorization
$ curl -i -k https://localhost:8443/infos
$ curl -i -k https://localhost:8443/books
=> 401 Unauthorized
$ curl -i -k -u student:student https://localhost:8443/books
=> 200 OK

D) UUID
$ curl -i -k -u student:student https://localhost:8443/books
