How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-UserService-TLS/UserService?wsdl

This request will be redirected to:

=> https://localhost:8443/SOAP-EJB-UserService-TLS/UserService?wsdl



How to Generate Client-Side Stubs?
-------------------------------------------------------------------------------

!! Direct usage of wsimport (or the corresponding maven plugin) does not work
for a TLS connection...

Workaround: 
- use curl to get the WSDL
	$ curl --insecure https://localhost:8443/SOAP-EJB-UserService-TLS/UserService?wsdl > src/wsdl/UserService.wsdl

	Manually change location URL from:
	  <wsdl:service name="UserResourceEJBService">
	    <wsdl:port binding="tns:UserResourceEJBServiceSoapBinding" name="UserServicePort">
	      <soap:address location="http://localhost:8080/SOAP-EJB-UserService-TLS/UserService"></soap:address>
	    </wsdl:port>
	  </wsdl:service>

	to the following location:
	  <wsdl:service name="UserResourceEJBService">
	    <wsdl:port binding="tns:UserResourceEJBServiceSoapBinding" name="UserServicePort">
	      <soap:address location="https://localhost:8443/SOAP-EJB-UserService-TLS/UserService"></soap:address>
	    </wsdl:port>
	  </wsdl:service>
	
- generate stubs
	$ wsimport -verbose -Xnocompile -s src/generated/java -p org.se.lab.client src/wsdl/UserService.wsdl
	Note that the generated client code also uses the local WSDL file.	

- add TLS properties to the client code (see HttpsSoapTestBase)
	// read path to the used keystore file
    System.setProperty( "javax.net.ssl.trustStore", properties.getProperty("ssl.trustStore"));
    System.setProperty( "javax.net.ssl.trustStorePassword", properties.getProperty("ssl.trustStorePassword"));
    HttpsURLConnection.setDefaultHostnameVerifier(new LocalhostVerifyer());

