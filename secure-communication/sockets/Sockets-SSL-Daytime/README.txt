How to Generate a Certificate?
-------------------------------------------------------------------------------

$ keytool -genkey -keystore SSLKeyStore -alias SSLCertificateWithRSA -keyalg RSA

=> password: student



How to Start the Server?
-------------------------------------------------------------------------------

$ java -cp ./target/classes -Djavax.net.ssl.keyStore=./SSLKeyStore  -Djavax.net.ssl.keyStorePassword=student org.se.lab.SSLDaytimeServer


How to Start the Client?
-------------------------------------------------------------------------------

$ java -cp ./target/classes -Djavax.net.ssl.trustStore=./SSLKeyStore -Djavax.net.ssl.trustStorePassword=student org.se.lab.SSLDaytimeClient

To get more information about the network communication we can add the following flag: -Djavax.net.debug=all 

Cipher Suites
-------------------------------------------------------------------------------
TLS uses different algorithms at various stages of creating and managing a secure
socket. These combinations of algorithms are referred to as cipher suites.

If the two endpoints of a network connection do not share a common suite, the
connection will fail before any application data is transmitted.

Example: TLS_RSA_WITH_AES_256_CBC_SHA256

TLS                 Identifies the suites as TLS (opposed to SSL)
RSA                 The key exchange and authentication algorithm.
WITH_AES_256_CBC    The specification of the bulk encryption cipher, its key size and mode.
SHA256              The message authentication algorithm

Note that this pattern can vary but general rules are:
- Don't use suites that list ANON for authentication (they don't provide authentication)
- Don't use suites that contain NULL.
- Avoid use of suites that contain EXPORT.
- Use bulk ciphers with key size of 128 bits or larger
- Try to avoid suites using RC4, DES and 3DES
Prefer ECDHE and DHE for key agreement. While they are slower, they provide stronger
protection even if the private keys are later compromised (forward security)

In TLS 1.3 there are now just five recommended cipher suites:
        TLS_AES_256_GCM_SHA384
        TLS_CHACHA20_POLY1305_SHA256
        TLS_AES_128_GCM_SHA256
        TLS_AES_128_CCM_8_SHA256
        TLS_AES_128_CCM_SHA256

TLS 1.3 has eliminated support for algorithms and ciphers that are both
theoretically and practically vulnerable. This includes:
        RC4 Stream Cipher
        RSA Key Exchange
        SHA-1 Hash Function
        CBC (Block) Mode Ciphers
        MD5 Algorithm
        Various non-ephemeral Diffie-Hellman groups
        EXPORT-strength ciphers
        DES
        3DES

References:
-------------------------------------------------------------------------------
Jim Manico, August Detlefsen
Iron-Clad Java: Building Secure Web Applications
Chapter 6: Protecting Sensitive Data
Oracle Press, 2014

TLS 1.3: Everything you need to know
https://securityboulevard.com/2019/07/tls-1-3-everything-you-need-to-know/

Galois/Counter Mode (GCM)
https://en.wikipedia.org/wiki/Galois/Counter_Mode


