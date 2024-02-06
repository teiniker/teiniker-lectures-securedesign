# Web Service Description Language

Web services use XML messages that follow the **Simple Object Access Protocol (SOAP)** standard, an XML language defining a message architecture and message formats. 

SOAP services contain a machine-readable description of the operations offered by the service, written in the 
**Web Services Description Language (WSDL)**, an XML language for defining interfaces syntactically.


## Anatomy of WSDL Documents

The WSDL is used to specify the exact message format, Internet protocol, and address that a client must use to communicate with a particular web service.

WSDL is well suited for code generators, which can read a WSDL document and generate a programmatic interface 
for accessing a web service.
WSDL is a complex interface definition language because it is not tied to any specific protocol, programming language or operating system.

A WSDL document contains the following elements:

* **`<definitions>`**\
   This is is the root element of a WSDL document, and encapsulates the entire document and defines 
   the document’s name.
   Contains several XML namespace declarations:
   * xmlns=“http://schema.xmlsoap.org/wsdl/”
   * xmlns:soap=“http://schema.xmlsoap.org/wsdl/soap”
   * xmlns:xsd=“http://www.w3.org/2001/XMLSchema”
   * targetNamespace=“…” xmlns:tns=“…”

* **`<types>`**\
   The types element serves as a container for **defining any data types** that are not described by the 
   XML schema build-in types: complex types and custom simple types.

* **`<import>`**
   * Import elements make available in the present WSDL document the definitions from a specified namespace 
      in another WSDL document.

   * Import elements must declare two attributes:
      * **namespace**: must match the targetNamespace declared by the WSDL document being imported.
      * **location**: must point to an actual WSDL document.

* **`<message>`**
   * A message element can describe the payload of outgoing or incoming messages.
   * A message element can also describe the contents of SOAP header blocks and fault elements.
   * The way to define a message element depends on whether we use RPC-style or document-style:
      * **RPC-style**: the message elements describe the payloads of the SOAP request and reply messages 
      (call parameters, return values and faults).
      * **Document-style**: the message definition refers to a top-level element in the types definition. 

* **`<portType>`**
   * A portType defines the abstract interface of a web service which is implemented by the **binding** 
      and **service** elements.
   * A portType may have one or more **operation** elements, each of which defines an RPC- or document-style web service method. 

* **`<operation>`**
   * Each operation element uses one or more message definitions to define its input, output and faults.
      * An **input message** represents the payload sent to the web service.
      * An **output message** represents the payload sent to the client.
      * An operation may include zero or more **fault messages**, each of which describes a different 
         kind of error.

* **`<binding>`**
   * The binding element maps an abstract **portType** to a set of concrete protocols such as SOAP and HTTP, 
      messaging styles (RPC or Document), and encoding styles (Literal or SOAP encoding).
   * The children of the binding element (**operation**, **input**, **output**) map directly to the corresponding 
      children of the portType element.

* **`<service>`**
   * The service element contains one or more **port** elements, each of which represents a different 
      web service.
   * The port element assigns the URL to a specific **binding**.  
   * It is possible for two or more port elements to assign different URLs to the same binding 
      (e.g. load balancing).


## SOAP API Validation

```
http://localhost:8080/SOAP-EJB-MessageAPI-Validation/OrderService?wsdl
```

### Valid SOAP Request

We send the following request using SoapUI.

```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:process>
         <arg0 id="1">
            <name>ORDER-20151213</name>
            <lines>
               <quantity>5</quantity>
               <product>
                  <description>Effective Java</description>
                  <price>3900</price>
               </product>
            </lines>
         </arg0>
      </ser:process>
   </soapenv:Body>
</soapenv:Envelope>
```
On the Wildfly console output we can see that the send data has been processed.
```
=> Order [id=1, name=ORDER-20151213, lines=[OrderLine [quantity=5, product=Effective Java,3900]]]
```

### Attack: Attribute Injection 

In the following request, we add an additional attribute `hack="true"` to the `process` message.
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:process>
         <arg0 id="1">
            <name hack="true">ORDER-20151213</name>
            <lines>
               <quantity>5</quantity>
               <product>
                  <description>Effective Java</description>
                  <price>3900</price>
               </product>
            </lines>
         </arg0>
      </ser:process>
   </soapenv:Body>
</soapenv:Envelope>
```
From the Wildfly console output we can see, that this **injected attribute** will be ignored.

```
=> Order [id=1, name=ORDER-20151213, lines=[OrderLine [quantity=5, product=Effective Java,3900]]]
```

### Attack: Overriding Existing Element 

Now we override an existing XML element in the request: `<name>ORDER-666</name>`  
```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:process>
         <arg0 id="1">
            <name>ORDER-20151213</name>
            <lines>
               <quantity>5</quantity>
               <product>
                  <description>Effective Java</description>
                  <price>3900</price>
               </product>
            </lines>
            <name>ORDER-666</name>
         </arg0>
      </ser:process>
   </soapenv:Body>
</soapenv:Envelope>
```
From the Wildfly console output we can see that our injection attack has been successful.
The injected XML element has replaced the original value.
```
=> Order [id=1, name=ORDER-666, lines=[OrderLine [quantity=5, product=Effective Java,3900]]]
```


### Activate Wildfly XML Schema Validation

```
We can activate the XML Schema validation in the `standalone.xml` file:

        <subsystem xmlns="urn:jboss:domain:webservices:2.0">
            <wsdl-host>${jboss.bind.address:127.0.0.1}</wsdl-host>
<!-- BENGIN -->
            <endpoint-config name="Standard-Endpoint-Config">
            	<property name="schema-validation-enabled" value="true"/>
            </endpoint-config>
<!-- END -->
            <endpoint-config name="Recording-Endpoint-Config">
                <pre-handler-chain name="recording-handlers" protocol-bindings="##SOAP11_HTTP ##SOAP11_HTTP_MTOM ##SOAP12_HTTP ##SOAP12_HTTP_MTOM">
                    <handler name="RecordingHandler" class="org.jboss.ws.common.invocation.RecordingServerHandler"/>
                </pre-handler-chain>
            </endpoint-config>
            <client-config name="Standard-Client-Config"/>
        </subsystem>
```

With that configuration we can see that the attacks are not possible anymore:
* Attack: Attribute injection `<lines hack="true">`: 
```
    org.xml.sax.SAXParseException; lineNumber: 7; columnNumber: 13; cvc-complex-type.3.2.2: 
    Attribute 'hack' is not allowed to appear in element 'lines'.
```

* Attack: Overriding existing element `<name>ORDER-666</name>`:
```
    org.xml.sax.SAXParseException; lineNumber: 14; columnNumber: 13; cvc-complex-type.2.4.a: 
	Invalid content was found starting with element 'name'. One of '{lines}' is expected.
```

## References
[Working with WSDL Files ](https://www.soapui.org/docs/soap-and-wsdl/working-with-wsdls/)


*Egon Teiniker, 2016-2022, GPL v3.0*
