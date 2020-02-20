How to access the Servlet?
-------------------------------------------------------------------------------

URL: http://localhost:8080/Servlet-Filter-PasswortEncryption/


The ServletRequestWrapper provides a convenient implementation of the ServletRequest
interface that can be subclassed by developers wishing to adapt the request to a
Servlet.
This class implements the Wrapper or Decorator pattern.
Methods default to calling through to the wrapped request object.

java.lang.Object getAttribute(java.lang.String name)
The default behavior of this method is to call getAttribute(String name) on the
wrapped request object.

