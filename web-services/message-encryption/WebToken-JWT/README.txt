JSON Web Token (JWT)
===============================================================================
https://jwt.io/introduction/
https://tools.ietf.org/html/rfc7519

JWT is an open standard (RFC 7519) that defines a compact and self-contained 
way for securely transmitting information between parties as a JSON object. 

This information can be verified and trusted because it is digitally signed. 
JWTs can be signed using a secret (with the HMAC algorithm) or a public/private 
key pair using RSA.

Here are some scenarios where JSON Web Tokens are useful:

o) Authentication: This is the most common scenario for using JWT. Once the user 
	is logged in, each subsequent request will include the JWT, allowing the user 
	to access routes, services, and resources that are permitted with that token. 
	Single Sign On is a feature that widely uses JWT nowadays, because of its small 
	overhead and its ability to be easily used across different domains.

o) Information Exchange: JSON Web Tokens are a good way of securely transmitting 
	information between parties, because as they can be signed, for example using 
	public/private key pairs, you can be sure that the senders are who they say they 
	are. 
	Additionally, as the signature is calculated using the header and the payload, 
	you can also verify that the content hasn't been tampered with.

JSON Web Tokens consist of three parts separated by dots (.), which are:

o) Header: The header typically consists of two parts: the type of the token, 
	which is JWT, and the hashing algorithm being used, such as HMAC SHA256 or RSA.

o) Payload: The second part of the token is the payload, which contains the claims. 
	Claims are statements about an entity (typically, the user) and additional metadata. 
	There are three types of claims: reserved, public, and private claims.
	- Reserved claims: These is a set of predefined claims which are not mandatory but 
	  recommended, to provide a set of useful, interoperable claims. Some of them are: 
	  iss (issuer), exp (expiration time), sub (subject), aud (audience), and others.
	- Public claims: These can be defined at will by those using JWTs. But to avoid 
	  collisions they should be defined in the IANA JSON Web Token Registry or be defined 
	  as a URI that contains a collision resistant namespace.  
	- Private claims: These are the custom claims created to share information between 
	  parties that agree on using them.  
	The payload is then Base64Url encoded to form the second part of the JSON Web Token.

o) Signature: To create the signature part you have to take the encoded header, the 
   encoded payload, a secret, the algorithm specified in the header, and sign that.
   The signature is used to verify that the sender of the JWT is who it says it is and 
   to ensure that the message wasn't changed along the way.	


Therefore, a JWT typically looks like the following: xxxxx.yyyyy.zzzzz

Example: eyJhbGciOiJIUzI1NiJ9.SU1TMTUtV1MyMDE2.rmEXeUKr6unu2vZ4shlJcXfnzAiGEzGjI5U-LL1AoPs


Nimbus JOSE + JWT
-------------------------------------------------------------------------------
The most popular and robust Java library for JSON Web Tokens (JWT)
Supports all standard signature (JWS) and encryption (JWE) algorithms

	POST /resource HTTP/1.1
	Host: server.example.com
	Authorization: Bearer mF_s9.B5f-4.1JqM


JSON Web Token (JWT) with HMAC protection
-------------------------------------------------------------------------------
http://connect2id.com/products/nimbus-jose-jwt/examples/jwt-with-hmac

JSON Web Tokens (JWT) can be integrity protected with a hash-based message 
authentication code (HMAC). The producer and consumer must posses a shared secret, 
negotiated through some out-of-band mechanism before the JWS-protected object is 
communicated (unless the producer secures the JWS object for itself).



Nested signed and encrypted JSON Web Token (JWT)
-------------------------------------------------------------------------------
http://connect2id.com/products/nimbus-jose-jwt/examples/signed-and-encrypted-jwt

JSON Web Tokens (JWT) can be signed then encrypted to provide confidentiality of 
the signed claims.
While it’s technically possible to perform the operations in any order to create 
a nested JWT, normally senders should first sign the JWT, then encrypt the 
resulting message.



References
-------------------------------------------------------------------------------
https://www.toptal.com/web/cookie-free-authentication-with-json-web-tokens-an-example-in-laravel-and-angularjs
http://blog.brainattica.com/restful-json-api-jwt-go/

http://connect2id.com/products/nimbus-jose-jwt

