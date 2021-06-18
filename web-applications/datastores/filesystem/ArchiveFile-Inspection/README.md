# Archive File Analysis

Care should be taken when **uploading archive files** (ZIP, RAR, etc.). 
The content of these files can be surprisingly extensive.

A **ZIP Bomb** is a malicious ZIP file that is of small size compressed but it is of
a giant size uncompressed.

Most ZIP or other archive APIs allow to **inspect the total uncompressed size** before conducting the actual uncompress 
action.

_Example_: Calculate the total uncompressed size of all ZIP entries 
```Java
    ZipFile zip = new ZipFile(file);
    long totalUncompressedSize = 0;
    Enumeration<? extends ZipEntry> entry = zip.entries();
    while(entry.hasMoreElements())
    {
        ZipEntry e = entry.nextElement();
        totalUncompressedSize += e.getSize();
    }
    System.out.println("Total Uncompressed Size = " + totalUncompressedSize);
```

A strong defensive control should not only check the reported size prior to unzipping, but also check that the 
actual unzipped size of each file matches.

## References
* [42.zip](https://unforgettable.dk/)

* Jim Manico, August Detlefsen. **Iron-Clad Java: Build Secure Web Applications**. Oracle Press, 2015
     * Chapter 8: Safe File Upload and File I/O
     
*Egon Teiniker, 2019-2021, GPL v3.0*	