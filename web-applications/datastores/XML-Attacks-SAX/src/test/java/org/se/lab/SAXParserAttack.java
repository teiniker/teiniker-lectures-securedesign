package org.se.lab;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class SAXParserAttack 
{
    private SAXParserFactory factory;
    
    @Before
    public void setup()  
    {
        factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
    }
    
    @Test
    public void testExternalEntityVulnerability()
            throws ParserConfigurationException, SAXException, IOException
    {
        SAXParser parser = factory.newSAXParser();
        parser.parse("src/test/resources/xml/session-entity-accessing-local-file.xml", new SAXPrinter());
    }


    @Test
    public void testEntityExpansionInjection() // a.k.a. XML Bomb Attack 
        throws ParserConfigurationException, SAXException, IOException 
    {
        SAXParser parser = factory.newSAXParser();        
        parser.parse("src/test/resources/xml/session-entity-expansion.xml", new SAXPrinter());
    }
}
