# XML Attacks (SAX Parser)

## XXL EXternal Entity Attack (XXE)

An XML External Entity attack is a type of attack against an application that 
parses XML input. This attack occurs when **XML input containing a reference 
to an external entity** is processed by a weakly configured XML parser. 

This attack may lead to the disclosure of confidential data, denial of service, 
server side request forgery, port scanning from the perspective of the machine 
where the parser is located, and other system impacts.

_Example_: External Entity Attack
```
<!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd" >]>  
<SessionRoot>
    <Sessions>
        <Session id="one" valid="true"/>
        <Session id="two" valid="false" />
    </Sessions>    
    <Hack>&xxe;</Hack>
</SessionRoot>
```

The safest way to prevent XXE is always to **disable DTDs (External Entities)** completely.

Disabling DTDs also makes the parser secure against denial of services (DOS) attacks. 
If it is not possible to disable DTDs completely, then **external entities and external 
document type declarations must be disabled** in the way that's specific to each parser.


## XML Bomb

Another common vulnerability associated with XML parsing is called 
**A Billion Laughs Attack** (a.k.a. XML Bomb). 

It uses an entity to resolve itself cyclically thereby consuming more CPU usage and 
causing a denial of service attack.

The entity keeps getting resolved to itself cyclically thereby slowing down requests and
causing a DOS attack on the application.

A billion laughs attack can be prevented by **disabling DOCTYPE**
or **setting a maximum limit on the evaluation of entities**.

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

Since JDK 1.4, a limit on the evaluation of entities has been configured:
```
"The parser has encountered more than "64,000" entity expansions in this document..."
```

## Resources
* [XML External Entity (XXE) Processing](https://owasp.org/www-community/vulnerabilities/XML_External_Entity_(XXE)_Processing)

* [XML External Entity Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html)

* [XML External Entity (XXE) and Billion Laughs attack](https://www.geeksforgeeks.org/xml-external-entity-xxe-and-billion-laughs-attack/)

*Egon Teiniker, 2017 - 2022, GPL v3.0*