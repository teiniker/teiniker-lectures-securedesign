Servlet: Session Management
---------------------------------------------------------------------

The simplest approach to sending a session ID is to simply append it as 
a GET or POST parameter. In the case of GET parameters, session IDs may
show up in the browser history, Web server logs, and other areas, thereby 
allowing malicious users to potentially steal the session.

The concept of cookies was introduced to abstract the session handling
functionality from core applications. Cookies are simple structures that
are either persisted to files or stored in memory.
The cookie for a particular domain are sent automatically with each user 
request to that domain.


1. CounterServlet without Session Management
---------------------------------------------------------------------
Note that every client connecting to this Servlet can change its value.

URL: https://localhost:8443/Servlet-SessionManagement/counter.html


2. SessionServlet
---------------------------------------------------------------------

URL: https://localhost:8443/Servlet-SessionManagement/session.html

The Servlet container creates an HttpServletRequest object and passes it 
as an argument to the servlet's service methods (doGet, doPost, etc). 


Interface HttpServletRequest
----------------------------

HttpSession getSession()
    Returns the current session associated with this request, or if the 
    request does not have a session, creates one.

HttpSession getSession(boolean create)
    Returns the current HttpSession associated with this request or, if 
    there is no current session and create is true, returns a new session.
    If create is false and the request has no valid HttpSession, this 
    method returns null.
    To make sure the session is properly maintained, you must call this 
    method before the response is committed. If the container is using 
    cookies to maintain session integrity and is asked to create a new 
    session when the response is committed, an IllegalStateException is 
    thrown. 

boolean isRequestedSessionIdValid()
    Checks whether the requested session ID is still valid.
   
boolean isRequestedSessionIdFromCookie()
    Checks whether the requested session ID came in as a cookie.    
                  
boolean isRequestedSessionIdFromURL()
    Checks whether the requested session ID came in as part of the 
    request URL.
    

Interface HttpSession
---------------------    
Provides a way to identify a user across more than one page request or 
visit to a Web site and to store information about that user.

The servlet container uses this interface to create a session between 
an HTTP client and an HTTP server. The session persists for a specified 
time period, across more than one connection or page request from the user. 
A session usually corresponds to one user, who may visit a site many times. 
The server can maintain a session in many ways such as using cookies or 
rewriting URLs.

This interface allows servlets to:

    - View and manipulate information about a session, such as the session 
      identifier, creation time, and last accessed time

    - Bind objects to sessions, allowing user information to persist across 
      multiple user connections 

A servlet should be able to handle cases in which the client does not 
choose to join a session, such as when cookies are intentionally turned 
off. Until the client joins the session, isNew returns true. 
If the client chooses not to join the session, getSession will return a 
different session on each request, and isNew will always return true.

Session information is scoped only to the current web application 
( ServletContext), so information stored in one context will not be 
directly visible in another.


java.lang.String getId()
    Returns a string containing the unique identifier assigned to this 
    session. The identifier is assigned by the servlet container and is 
    implementation dependent. 

long getLastAccessedTime()
    Returns the last time the client sent a request associated with this 
    session, as the number of milliseconds since midnight January 1, 1970 GMT, 
    and marked by the time the container received the request.
    Actions that your application takes, such as getting or setting a value 
    associated with the session, do not affect the access time. 


void setMaxInactiveInterval(int interval)
    Specifies the time, in seconds, between client requests before the servlet
    container will invalidate this session. A zero or negative time indicates 
    that the session should never timeout. 

int getMaxInactiveInterval()
    Returns the maximum time interval, in seconds, that the servlet container 
    will keep this session open between client accesses. After this interval, 
    the servlet container will invalidate the session. The maximum time 
    interval can be set with the setMaxInactiveInterval method. 
    A zero or negative time indicates that the session should never timeout. 


java.lang.Object getAttribute(java.lang.String name)
    Returns the object bound with the specified name in this session, or 
    null if no object is bound under the name.
    
java.util.Enumeration<java.lang.String> getAttributeNames()
    Returns an Enumeration of String objects containing the names of all 
    the objects bound to this session.
    
void setAttribute(java.lang.String name, java.lang.Object value)
    Binds an object to this session, using the name specified. If an 
    object of the same name is already bound to the session, the object 
    is replaced.
    If the value passed in is null, this has the same effect as calling 
    removeAttribute().
    
void removeAttribute(java.lang.String name)
    Removes the object bound with the specified name from this session. 
    If the session does not have an object bound with the specified name, 
    this method does nothing.

    
void invalidate()
    Invalidates this session then unbinds any objects bound to it.
    
boolean isNew()
    Returns true if the client does not yet know about the session or if 
    the client chooses not to join the session. 
    For example, if the server used only cookie-based sessions, and the 
    client had disabled the use of cookies, then a session would be new 
    on each request.
  