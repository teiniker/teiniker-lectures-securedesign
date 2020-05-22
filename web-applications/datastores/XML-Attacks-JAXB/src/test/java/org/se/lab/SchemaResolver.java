package org.se.lab;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class SchemaResolver extends SchemaOutputResolver
{
    public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException
    {
        File file = new File("order.xsd");
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());
        return result;
    }
}
