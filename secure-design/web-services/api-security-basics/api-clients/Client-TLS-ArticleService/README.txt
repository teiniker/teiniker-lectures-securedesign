Certificate Setup
-------------------------------------------------------------------------------

To verify the server-side certificate a client has to have the public key of
the CA which signed the certificate.
In our case the CA and the server use the same private key, thus, we use a
self-signed certificate.

As a shortcut, instead of exporting the CA certificate from the server's key
store and import the certificate into the client's trust store, we just
point to the server's key store (see http.properties).

Exercise: Create a client truststore.

