# OpenSSL

The **openssl** program is a command line tool for using the various cryptography functions of OpenSSL's crypto library from the shell.
It can be used for:
*  Public key cryptographic operations
*  Calculation of Message Digests
*  Creation and management of private keys, public keys and parameters
*  Creation of X.509 certificates, CSRs and CRLs
*  Encryption and Decryption with Ciphers
*  SSL/TLS Client and Server Tests
*  Handling of S/MIME signed or encrypted mail

To find out if your system supports openssl, type:
```
$ openssl version
OpenSSL 1.1.1g  21 Apr 2020
```

## Generating Psuedo Random Numbers
The following command line generates a number of random bytes, which can either be output raw, as Base64 or as HEX:
```
$ openssl rand -hex 16
f05a4f9bef09d057c355c6e28fec5543

$ openssl rand -base64 16
gokRIzPgvBsnQd5fshD9fg==
```
Parameters:
* **rand**: Generate pseudo-random bytes.
* **-hex**: Show the output as a hex string.
* **-base64**: Perform base64 encoding on the output.
* **-out file**: Write to file instead of standard output.
 
## Generating a File Fingerprint (Hash Value)
The digest functions output the message digest of a supplied file or files in hexadecimal. 

To see the list of supported algorithms, use the `list --digest-commands` command.
```
$ openssl list -digest-commands
blake2b512        blake2s256        gost              md4               
md5               rmd160            sha1              sha224            
sha256            sha3-224          sha3-256          sha3-384          
sha3-512          sha384            sha512            sha512-224        
sha512-256        shake128          shake256          sm3   
```

```
$ openssl dgst -sha256 wordlist.txt 
SHA256(wordlist.txt)= fd17b0ff31d7fb7b4e202b0cd99e1f11aa1f4972aab81010544a4e562f42bac7
```

## Generating Private and Public Keys

To generate an RSA **private key** type the following command:
```
$ openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:4096 -out private-key.pem
$ cat private-key.pem
-----BEGIN PRIVATE KEY-----
MIIJQQIBADANBgkqhkiG9w0BAQEFAASCCSswggknAgEAAoICAQC18RulJsPhX8TM
H25/0yzcSB5zOXT6NiVxMloysok9c2va5HlauydRYCxnGLISAcaeKPg3JFmQ78Bb
ffkmWB8Aexw0fHdP38A0AE3tfrLUC9AAIRZ3SsgyaUQ04aTmDHVfCYBHa81ezjVa
...
0WzCDbcVjDorfaWLomo932bimsi2gMMLH/c93k9VXShKkQhKLPKbiocGlWqVcb5u
xGp7shRR6ykLLPKx75gj3KPjt0hqBP+4WXJRZdAt8k44YAt2FN11C/8M2bazYNdp
zK0Ot5tcBeH7KuM1BEzd78cIJBUW
-----END PRIVATE KEY-----
```
Parameters: 
* **genpkey**: Generate a private key.
* **-algorithm alg**: Public key algorithm to use such as **RSA**, DSA or DH.
* **-pkeyopt opt:value**: Set the public key algorithm option `opt` to `value`.
* **-out filename**: Output the key to the specified file. If this argument is not specified then standard output is used.
 
Having previously generated your private key, you may generate the corresponding **public key** using the following command:
```
$ openssl pkey -in private-key.pem -out public-key.pem -pubout
$ cat public-key.pem 
-----BEGIN PUBLIC KEY-----
MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtfEbpSbD4V/EzB9uf9Ms
3Egeczl0+jYlcTJaMrKJPXNr2uR5WrsnUWAsZxiyEgHGnij4NyRZkO/AW335Jlgf
AHscNHx3T9/ANABN7X6y1AvQACEWd0rIMmlENOGk5gx1XwmAR2vNXs41WjrdSPh1
aN2ipN/OFZt0MDGVxliXhUDZ6pvfrYbXwRYxDd1bNK8dHYaw73ybWK9gI6SCCsbB
...
LEgYQN1N5RURmiBI8WWOdv6JUH3Ddd3zOj+wTCv6qIUX4gLQ4pgHHc8MRmAmsoUQ
kWK+pn2FV8/KjGaYXhR+TeDg/TXj6db43GPXCPFjYtu/a6kWvT7xEK1tE+4r/P8X
h//Br01zuuLfBgtd633gT1UCAwEAAQ==
-----END PUBLIC KEY-----
```
Parameters:
* **pkey**: Public or private key processing tool.
* **-in filename**: This specifies the **input filename** to read a key from or standard input 
   if this option is not specified. 
   If the key is encrypted a pass phrase will be prompted for.
* **-out filename**: This specifies the **output filename** to write a key to or standard output 
   if this option is not specified. 
   If any encryption options are set then a pass phrase will be prompted for. 
   The output filename should **not be the same** as the input filename.
* **-pubout**: By default a private key is output: with this option a **public key** will be 
   output instead. This option is automatically set if the input is a public key.

## Generating Self-Signed Certificates

A common server-side operation is to generate a self-signed certificate for testing or encrypting communications between 
internal servers.

To **generate a private key and public certificate**, run the following command and answer the questions:
```
$ openssl req -x509 -newkey rsa:4096 -nodes -keyout key.pem  -days 365 -out certificate.pem
enerating a RSA private key
................................................................................................................................................................................++++
........................++++
writing new private key to 'key.pem'
-----
You are about to be asked to enter information that will be incorporated
into your certificate request.
What you are about to enter is what is called a Distinguished Name or a DN.
There are quite a few fields but you can leave some blank
For some fields there will be a default value,
If you enter '.', the field will be left blank.
-----
Country Name (2 letter code) [AU]:at
State or Province Name (full name) [Some-State]:styria
Locality Name (eg, city) []:graz
Organization Name (eg, company) [Internet Widgits Pty Ltd]:fhj
Organizational Unit Name (eg, section) []:stm
Common Name (e.g. server FQDN or YOUR name) []:teini
Email Address []:egon.teiniker@fhj.at
```

Parameters:
* **req**: Command passed to OpenSSL intended for creating and processing certificate requests usually in the PKCS#10 format.
* **-x509**: This multipurpose command allows OpenSSL to sign the certificate somewhat like a certificate authority. 
    X.509 refers to a digitally signed document according to RFC 5280.
* **-nodes**: This command is for no DES, which means that the private key will not be password protected.
* **-days**: The number of days that the certificate will be valid.
* **-newkey**: The format of the key, in this case an RSA key with 4096 bit encryption.
* **-keyout**: The location to output the private key of the self-signed certificate.
* **-out**: The location to output the certificate file itself.

To **review the certificate** type:
```
$ openssl x509 -text -noout -in certificate.pem
Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number:
            04:c0:8c:b2:f1:ba:82:a1:3f:d7:5b:2c:ea:bc:ed:20:30:97:fa:22
        Signature Algorithm: sha256WithRSAEncryption
        Issuer: C = at, ST = styria, L = graz, O = fhj, OU = stm, CN = teini, emailAddress = egon.teiniker@fhj.at
        Validity
            Not Before: Jan  5 22:28:29 2021 GMT
            Not After : Jan  5 22:28:29 2022 GMT
        Subject: C = at, ST = styria, L = graz, O = fhj, OU = stm, CN = teini, emailAddress = egon.teiniker@fhj.at
        Subject Public Key Info:
            Public Key Algorithm: rsaEncryption
                RSA Public-Key: (4096 bit)
                Modulus:
                    00:ea:30:10:bf:70:8e:e4:99:12:76:d4:3d:d3:36:
                    59:e1:fa:73:83:b6:2e:3d:3e:e7:1f:35:50:0d:66:
                    9a:9a:ce:3c:e2:ed:36:bb:99:71:04:2b:e0:f1:72:
                    ...
                    47:dd:7e:c9:07:cc:c4:88:5e:6d:61:16:5d:ee:61:
                    cc:b0:6c:b2:3e:5f:99:3c:12:c8:d4:bf:8d:19:75:
                    ba:dd:e7
                Exponent: 65537 (0x10001)
        X509v3 extensions:
            X509v3 Subject Key Identifier: 
                47:E6:69:3B:CD:DA:F8:7D:FF:4C:9B:A6:5B:F0:9F:55:8E:C2:F5:F2
            X509v3 Authority Key Identifier: 
                keyid:47:E6:69:3B:CD:DA:F8:7D:FF:4C:9B:A6:5B:F0:9F:55:8E:C2:F5:F2

            X509v3 Basic Constraints: critical
                CA:TRUE
    Signature Algorithm: sha256WithRSAEncryption
         76:c4:fd:de:d7:27:47:e9:a5:63:97:44:3f:91:1e:cb:97:fc:
         e8:ba:9d:ae:79:e1:d4:3e:59:3c:07:c3:85:63:07:d3:aa:1b:
         83:da:23:02:64:03:71:05:94:83:63:bd:70:ff:4e:4c:8a:2b:
         ...
         cf:71:b0:d4:69:00:73:84:06:96:49:48:e6:9d:b0:d1:d7:8a:
         bc:6d:94:ca:3a:25:07:55:b0:79:c1:b8:26:ca:35:af:3d:5a:
         15:2c:50:77:9b:30:46:15
```

Parameters:
* **x509**: This is a multipurpose command, and when combined with the other parameters here, it is for retrieving 
        information about the passed in the certificate.
* **-text**: Strips the text headers from the output.
* **-noout**: Needed not to output the encoded version of the certificate
* **-in**: The certificate that we are verifying.

## References
* [openssl](https://www.openssl.org/docs/man1.1.1/man1/openssl.html)
* [How To Use OpenSSL To Generate Certificates](https://blog.ipswitch.com/how-to-use-openssl-to-generate-certificates)