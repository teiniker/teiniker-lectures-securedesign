How to access the WSDL definition?
-------------------------------------------------------------------------------

URL: http://localhost:8080/SOAP-EJB-ArticleService/ArticleService?wsdl

$ curl -i -X GET http://localhost:8080/SOAP-EJB-ArticleService/ArticleService?wsdl


findAll()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findAll/>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:findAllResponse xmlns:ns2="http://service.lab.se.org/">
         <return id="1" price="4295">
            <description>Design Patterns</description>
         </return>
         <return id="2" price="3336">
            <description>Effective Java (2nd Edition)</description>
         </return>
      </ns2:findAllResponse>
   </soap:Body>
</soap:Envelope>


findById()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findById>
         <arg0>1</arg0>
      </ser:findById>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:findByIdResponse xmlns:ns2="http://service.lab.se.org/">
         <return id="1" price="4295">
            <description>Design Patterns</description>
         </return>
      </ns2:findByIdResponse>
   </soap:Body>
</soap:Envelope>


insert()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:insert>
         <arg0 price="9999">
            <description>Book: Effective Python</description>
         </arg0>
      </ser:insert>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:insertResponse xmlns:ns2="http://service.lab.se.org/"/>
   </soap:Body>
</soap:Envelope>


update()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:update>
          <arg0 id="1" price="1999">
            <description>Design Patterns</description>
         </arg0>
      </ser:update>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:updateResponse xmlns:ns2="http://service.lab.se.org/"/>
   </soap:Body>
</soap:Envelope>


delete()
-------------------------------------------------------------------------------
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.lab.se.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:delete>
         <arg0 id="100" price="9999">
            <description>Book: Effective Python</description>
         </arg0>
      </ser:delete>
   </soapenv:Body>
</soapenv:Envelope>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:deleteResponse xmlns:ns2="http://service.lab.se.org/"/>
   </soap:Body>
</soap:Envelope>


How to generate the DB schema from entity classes?
-------------------------------------------------------------------------------
see persistence.xml
    <property name="hibernate.hbm2ddl.auto" value="create-drop" />

