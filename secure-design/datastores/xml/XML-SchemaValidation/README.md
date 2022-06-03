# XML Schema Validation

An XML Schema describes the **structure of an XML document**.
The XML Schema language is also referred to as **XML Schema Definition (XSD)**.

The purpose of an XML Schema is to define the legal **building blocks of an XML document**:
* the elements and attributes that can appear in a document
* the number of (and order of) child elements
* data types for elements and attributes
* default and fixed values for elements and attributes

_Example_: XML schema validation in Java
```Java
@Test
public void testOrderValidation() throws IOException, SAXException
{
    SchemaFactory schemaFactory=SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    Schema xsd=schemaFactory.newSchema(new File("src/test/resources/xsd","order.xsd"));
    Validator validator=xsd.newValidator();
    
    Source xml=new StreamSource(new FileReader(new File("src/test/resources/xml","order.xml")));
    validator.validate(xml);
}
```

## Resources

* [XML Schema Tutorial](https://www.w3schools.com/xml/schema_intro.asp)

* [XML Schema Part 1: Structures Second Edition](https://www.w3.org/TR/xmlschema-1/)

* [Package javax.xml.validation](https://docs.oracle.com/en/java/javase/11/docs/api/java.xml/javax/xml/validation/package-summary.html)

*Egon Teiniker, 2017 - 2022, GPL v3.0*