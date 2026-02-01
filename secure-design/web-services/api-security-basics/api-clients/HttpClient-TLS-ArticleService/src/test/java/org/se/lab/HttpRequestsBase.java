package org.se.lab;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class HttpRequestsBase
{
    // Replacement for system property setting on the command line
    // -Djavax.net.ssl.trustStore=...
    // -Djavax.net.ssl.trustStorePassword=...
    SSLContext sslContextFromTruststore(String trustStorePath, String trustStorePassword)
            throws Exception
    {
        KeyStore ts = KeyStore.getInstance("PKCS12");
        try (InputStream in = Files.newInputStream(Path.of(trustStorePath)))
        {
            ts.load(in, trustStorePassword.toCharArray());
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ts);
        SSLContext ctx = SSLContext.getInstance("TLS"); // negotiates best supported TLS version
        ctx.init(null, tmf.getTrustManagers(), new java.security.SecureRandom());
        return ctx;
    }
}