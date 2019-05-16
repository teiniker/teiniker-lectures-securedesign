How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-UserService-BasicAuth/UserService?wsdl

$  curl --user student:student --verbose http://localhost:8080/SOAP-EJB-UserService-BasicAuth/UserService?wsdl

How to use SoapUI to access the SOAP service?
-------------------------------------------------------------------------------

Request:
	GET /SOAP-EJB-UserService-BasicAuth/UserService?wsdl HTTP/1.1
	Host: localhost:8080
	User-Agent: Apache-HttpClient/4.1.1 (java 1.5)

Response:
	HTTP/1.1 401 Unauthorized
	Expires: 0
	Cache-Control: no-cache, no-store, must-revalidate
	X-Powered-By: Undertow/1
	Server: WildFly/9
	Pragma: no-cache
	Date: Wed, 11 Nov 2015 14:21:44 GMT
	Connection: keep-alive
	WWW-Authenticate: Basic realm="ApplicationRealm"
	Content-Type: text/html;charset=UTF-8
	Content-Length: 71
	
	<html><head><title>Error</title></head><body>Unauthorized</body></html>
	

=> SoapUI asks for username + password (student/student)

Request:
	GET /SOAP-EJB-UserService-BasicAuth/UserService?wsdl HTTP/1.1
	Host: localhost:8080
	User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
	Authorization: Basic c3R1ZGVudDpzdHVkZW50

Response:
	HTTP/1.1 200 OK
	Expires: 0
	Cache-Control: no-cache, no-store, must-revalidate
	X-Powered-By: Undertow/1
	Server: WildFly/9
	Pragma: no-cache
	Date: Wed, 11 Nov 2015 14:21:57 GMT
	Connection: keep-alive
	Content-Type: text/xml;charset=UTF-8
	Content-Length: 8268
	
	<?xml version="1.0" ?>
	<wsdl:definitions xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:tns="http://service.lab.se.org/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:ns1="http://schemas.xmlsoap.org/soap/http" name="UserResourceEJBService" targetNamespace="http://service.lab.se.org/">
	...
	</wsdl:definitions>

Note that you can also add a HTTP header to a SOAP request in SoapUI.



