# File Upload

In HTTP, a file upload is typically done using a **POST request** with the 
**Content-Type: multipart/form-data**. The request body is split into parts, 
where each part has its own headers and one part contains the binary file data 
(plus optional form fields). 

In SpringBoot, the framework parses this multipart request for us and exposes 
uploaded files as `MultipartFile`, letting our controller read metadata (name, 
size, content type) and stream or store the file without manually handling raw 
HTTP bytes.


## Setup 

We can start the service as a separate process:
```
$ mvn spring-boot:run
```

## Attacking the File Upload

To upload a file we can use the following `curl` statement:

```bash
$ curl -ki --proxy http://localhost:8010 -F "file=@./images/tux.jpeg" https://localhost:8443/upload
```

* **--proxy http://localhost:8010**: Sends the request through an HTTP proxy running on `localhost:8010`.

* **-F "file=@./images/tux.jpeg"**: Sends a `multipart/form-data` request with a form field named file; 
    `@` tells curl to read and upload the contents of `./images/tux.jpeg`.

* **https://localhost:8443/upload**: The target URL, an HTTPS server on port 8443 with an /upload endpoint.


### Path Traversal Attack

The **request is routed through an interception proxy** (such as ZAP), allowing us 
to pause it, edit the filename, and forward the crafted request to the server.
In this way, a path traversal attack can be carried out.

```
Content-Disposition: form-data; name="file"; filename="../tux.jpeg"
Content-Type: image/jpeg
```
The file is saved parallel to the `upload/` folder.


## References

* Jim Manico, August Detlefsen. **Iron-Clad Java: Build Secure Web Applications**. Oracle Press, 2015
    * Chapter 8: Safe File Upload and File I/O


*Egon Teiniker, 2016-2026, GPL v3.0*
