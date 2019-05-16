JSON Web Signature (JWS)
-------------------------------------------------------------------------------
http://connect2id.com/products/nimbus-jose-jwt/examples

JSON Web Signatures (JWS) can secure content, such as text, JSON or binary 
data, with a digital signature (RSA or EC) or hash-based message authentication 
codes (HMAC).


JSON Web Signature (JWS) with HMAC protection
-------------------------------------------------------------------------------
It demonstrates how to create and verify a JSON Web Signature (JWS) based on a 
hash-based message authentication code (HMAC). 
This requires the producer and consumer to posses a shared secret, negotiated 
through some out-of-band mechanism before the JWS-protected object is communicated.
The payload is a simple "Hello, world!" string but can also be a JSON string or 
a BASE64 encoded byte array.
The Nimbus JOSE+JWT library supports all standard JWS algorithms for HMAC protection.
