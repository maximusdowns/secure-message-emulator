package com.maxdowns.securemessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;

public class TlsContextFactory {

    // Step 5
    // dev note: created after the private helpers below
    public static SSLContext createServerContext(
            String keyStorePath,
            char[] password
    ) throws GeneralSecurityException, IOException {

        KeyStore keyStore = loadKeyStore(keyStorePath, password);

        KeyManagerFactory keyManagerFactory =
                createKeyManagerFactory(keyStore, password);

        SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(
                keyManagerFactory.getKeyManagers(), // server identity
                null,                               // default trust managers
                null                                // default secure randomness
        );

        return sslContext;
    }

    // Step 6
    public static SSLContext createClientContext(
            String trustStorePath,
            char[] password
    ) throws GeneralSecurityException, IOException {

        KeyStore trustStore = loadKeyStore(trustStorePath, password);  // note: A keystore and a truststore can both be represented by Java's Keystore class

        TrustManagerFactory trustManagerFactory =
                createTrustManagerFactory(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(
                null,                               // Who am I?       → nobody / no client certificate
                trustManagerFactory.getTrustManagers(), // Who do I trust? → our server certificate
                null                                    // Randomness      → defaults
        );

        return sslContext;
    }

    // Step 1
    private static KeyStore loadKeyStore(
            String path,
            char[] password  //Java security APIs conventionally accept passwords as character arrays b/c they're mutable
    ) throws GeneralSecurityException, IOException {

        // this does not load your file, it says
        // Java, give me a KeyStore implementation capable of understanding the PKCS#12 format.
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        // open a stream of bytes from the file
        try (FileInputStream inputStream = new FileInputStream(path)) {
            // Load the PKCS12 data from the provided file into the KeyStore object
            keyStore.load(inputStream, password);
        }

        // Return the loaded KeyStore to the caller
        return keyStore;
    }

    // step 2
    private static KeyManagerFactory createKeyManagerFactory(
            KeyStore keyStore,
            char[] password
    ) throws GeneralSecurityException {

        // we are going to build the proper KeyManagerFactory based on the environment
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(  // Give me a KeyManagerFactory implementation...
                        KeyManagerFactory.getDefaultAlgorithm()  // ...using the default algorithm for this environment
                );

        keyManagerFactory.init(keyStore, password);  //config the KeyManagerFactor with my key material

        return keyManagerFactory;
    }

    // Step 3
    private static TrustManagerFactory createTrustManagerFactory (
            KeyStore trustStore
    ) throws GeneralSecurityException {

        TrustManagerFactory trustManagerFactory  =
            TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm()
            );

        trustManagerFactory.init(trustStore);  // doesn't need a password to access a private key, because this truststore contains a trustedCertEntry, not a PrivateKeyEntry

        return trustManagerFactory;
    }

    // Step 4
    private static SSLContext createSslContext(
            KeyManagerFactory keyManagerFactory,
            TrustManagerFactory trustManagerFactory
    ) throws GeneralSecurityException {

        SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(
                keyManagerFactory.getKeyManagers(),     // Who can I identify myself as?
                trustManagerFactory.getTrustManagers(), // Who am I willing to trust?
                null                                    // Use Java's default secure randomness
        );

        return sslContext;
    }
}
