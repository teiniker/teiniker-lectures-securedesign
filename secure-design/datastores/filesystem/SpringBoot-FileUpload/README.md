# File Upload

## Setup 

We can start the service as a separate process:
```
$ mvn spring-boot:run
```

## Attacking the File Upload

To upload a file we can use the following `curl` statement:

```
$ curl -ki --proxy http://localhost:8010 -F "file=@./images/tux.jpeg" https://localhost:8443/upload
```

### Path Traversal Attack
To perform the attack, use the interception proxy and change
the filename:
```
Content-Disposition: form-data; name="file"; filename="../tux.jpeg"
Content-Type: image/jpeg
```
The file is saved parallel to the `upload/` folder.


## References

* Jim Manico, August Detlefsen. **Iron-Clad Java: Build Secure Web Applications**. Oracle Press, 2015
    * Chapter 8: Safe File Upload and File I/O


*Egon Teiniker, 2016-2024, GPL v3.0*
