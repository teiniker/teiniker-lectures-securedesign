How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-MessageAPI-Validation/OrderService?wsdl


How to attack the SOAP service?
-------------------------------------------------------------------------------

o) Valid SOAP request: process(Order)

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

=> Order [id=1, name=ORDER-20151213, lines=[OrderLine [quantity=5, product=Effective Java,3900]]]


o) Attack: Attribute injection (<name hack="true">ORDER-20151213</name>)

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

=> Order [id=1, name=ORDER-20151213, lines=[OrderLine [quantity=5, product=Effective Java,3900]]]
=> injected Attribute will be ignored


o) Attack: Element injection (<hack id="666" />)

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:process>
         <arg0 id="1">
            <name>ORDER-20151213</name>
            <hack id="666" />
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

=> Unmarshalling Error: unexpected element (uri:"", local:"hack"). Expected elements are <{}name>,<{}lines> 

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <soap:Fault>
         <faultcode>soap:Client</faultcode>
         <faultstring>Unmarshalling Error: unexpected element (uri:"", local:"hack"). Expected elements are &lt;{}name>,&lt;{}lines></faultstring>
      </soap:Fault>
   </soap:Body>
</soap:Envelope>


o) Attack: Overriding existing element (<name>ORDER-666</name>)

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

=> Order [id=1, name=ORDER-666, lines=[OrderLine [quantity=5, product=Effective Java,3900]]]



How to configure XML schema validation?
-------------------------------------------------------------------------------
Change the standalone.xml from:

        <subsystem xmlns="urn:jboss:domain:webservices:2.0">
            <wsdl-host>${jboss.bind.address:127.0.0.1}</wsdl-host>
            <endpoint-config name="Standard-Endpoint-Config"/>
            <endpoint-config name="Recording-Endpoint-Config">
                <pre-handler-chain name="recording-handlers" protocol-bindings="##SOAP11_HTTP ##SOAP11_HTTP_MTOM ##SOAP12_HTTP ##SOAP12_HTTP_MTOM">
                    <handler name="RecordingHandler" class="org.jboss.ws.common.invocation.RecordingServerHandler"/>
                </pre-handler-chain>
            </endpoint-config>
            <client-config name="Standard-Client-Config"/>
        </subsystem>

to:
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


o) Attack: Attribute injection (<lines hack="true">)

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:process>
         <arg0 id="1">
            <name>ORDER-20151213</name>
            <lines hack="true">
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

=> org.xml.sax.SAXParseException; lineNumber: 7; columnNumber: 13; cvc-complex-type.3.2.2: 
	Attribute 'hack' is not allowed to appear in element 'lines'.

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <soap:Fault>
         <faultcode>soap:Client</faultcode>
         <faultstring>Unmarshalling Error: cvc-type.3.1.1: Element 'name' is a simple type, so it cannot have attributes, excepting those whose namespace name is identical to 'http://www.w3.org/2001/XMLSchema-instance' and whose [local name] is one of 'type', 'nil', 'schemaLocation' or 'noNamespaceSchemaLocation'. However, the attribute, 'hack' was found.</faultstring>
      </soap:Fault>
   </soap:Body>
</soap:Envelope>
         
         
o) Attack: Element injection (<hack id="666" />)

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:process>
         <arg0 id="1">
            <name>ORDER-20151213</name>
            <hack id="666" />
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

=> org.xml.sax.SAXParseException; lineNumber: 7; columnNumber: 13; cvc-complex-type.2.4.a: 
	Invalid content was found starting with element 'hack'. One of '{lines}' is expected.

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <soap:Fault>
         <faultcode>soap:Client</faultcode>
         <faultstring>Unmarshalling Error: cvc-complex-type.2.4.a: Invalid content was found starting with element 'hack'. One of '{lines}' is expected.</faultstring>
      </soap:Fault>
   </soap:Body>
</soap:Envelope>


o) Attack: Overriding existing element (<name>ORDER-666</name>)

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

=> org.xml.sax.SAXParseException; lineNumber: 14; columnNumber: 13; cvc-complex-type.2.4.a: 
	Invalid content was found starting with element 'name'. One of '{lines}' is expected.

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <soap:Fault>
         <faultcode>soap:Client</faultcode>
         <faultstring>Unmarshalling Error: cvc-complex-type.2.4.a: Invalid content was found starting with element 'name'. One of '{lines}' is expected.</faultstring>
      </soap:Fault>
   </soap:Body>
</soap:Envelope>

         