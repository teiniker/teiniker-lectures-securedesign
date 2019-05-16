How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-UserService-BasicAuth/UserService?wsdl



How to generate client-side stubs?
-------------------------------------------------------------------------------

Create a text file containing the user's username and password:

$ vi auth.txt 
http://student:student@localhost:8080/SOAP-EJB-UserService-BasicAuth/UserService?wsdl

$ wsimport -verbose -Xnocompile -Xauthfile auth.txt -s src/generated/java -p org.se.lab.client http://localhost:8080/SOAP-EJB-UserService-BasicAuth/UserService?wsdl



How to set user credentials in the client code?
-------------------------------------------------------------------------------

Make sure that you create an Authenticator object before you instantiate a 
client proxy for the SOAP WS. 

See USerServiceTest:

		Authenticator myAuth = new Authenticator() 
		{
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication()
		    {
		        return new PasswordAuthentication(USERNAME, PASSWORD.toCharArray());
		    }
		};
		Authenticator.setDefault(myAuth);

		UserResourceEJBService ws = new UserResourceEJBService();
		service = ws.getUserServicePort();	


Note: In practice we should always combine the Basic Authentication with TLS!!!

