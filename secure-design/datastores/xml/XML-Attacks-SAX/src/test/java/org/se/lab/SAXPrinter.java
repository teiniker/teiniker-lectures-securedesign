package org.se.lab;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXPrinter
    extends DefaultHandler
{
    /*
     * Receive notification of the start of an element.
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException
    {
        System.out.print("<" + qName );
        
        for(int i=0; i< attributes.getLength(); i++)
        {
            System.out.print(attributes.getQName(i) + "=" + attributes.getValue(i) + " ");
        }
        System.out.print(">");
    }
        
    /*
     * Receive notification of the end of an element.
     */
    public void endElement (String uri, String localName, String qName)
        throws SAXException
    {
        System.out.println("</" + qName + ">");
    }
    
    
    /*
     * Receive notification of character data inside an element.
     */
    public void characters (char ch[], int start, int length)
        throws SAXException
    {
        String text = new String(ch, start, length); 
        System.out.print(text);
    }
}
