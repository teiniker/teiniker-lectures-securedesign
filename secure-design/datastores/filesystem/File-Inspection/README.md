 # File Content Analysis	

The **Apache Tika** toolkit detects and extracts metadata and text from over a thousand 
different file types: [Supported Document Formats](https://tika.apache.org/1.26/formats.html)
 
All of these file types can be parsed through a single interface, making Tika useful 
for search engine indexing, content analysis, translation, and much more. 

_Example_: Check for PNG file format
```Java
@Test
public void testPNGFile() throws IOException
{
    Tika tika = new Tika();
    File file = new File("picture.png");
    String mimeType = tika.detect(file);
    Assert.assertEquals(mimeType, "image/png");
}
```

The library relies on **magic markers in the stream prefix** and not on file extensions, 
for type resolution.

## References

* [Getting a File’s Mime Type in Java](https://www.baeldung.com/java-file-mime-type)
	
* [Apache Tika](https://tika.apache.org/)
    
* Jim Manico, August Detlefsen. **Iron-Clad Java: Build Secure Web Applications**. Oracle Press, 2015
     * Chapter 8: Safe File Upload and File I/O
		
*Egon Teiniker, 2019-2021, GPL v3.0*	