Apache HTTP Client
-------------------------------------------------------------------------------
http://hc.apache.org/httpclient-3.x/


Preemptive authentication
-------------------------------------------------------------------------------
HttpClient does not support preemptive authentication out of the box, because if 
misused or used incorrectly the preemptive authentication can lead to significant 
security issues, such as sending user credentials in clear text to an unauthorized 
third party. 
Therefore, users are expected to evaluate potential benefits of preemptive authentication 
versus security risks in the context of their specific application environment.
Nonetheless one can configure HttpClient to authenticate preemptively by 
prepopulating the authentication data cache.

Add the following client code to enable preemptive authentication:

		// Begin preemptive authentication ----------------
        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();

        // Generate BASIC scheme object and add it to the local
        // auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(target, basicAuth);

        // Add AuthCache to the execution context
        localContext.setAuthCache(authCache);
		// End preemptive authentication ------------------
        