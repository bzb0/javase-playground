package com.bzb.javase.crypto.tls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class TLSContextHelper {

  private final static String DESIRED_TLS_VERSION = "TLSv1.2";

  private static KeyFactory getKeyFactoryInstance() throws NoSuchAlgorithmException {
    return KeyFactory.getInstance("RSA");
  }

  private static X509Certificate createX509CertificateFromFile(String certificateFileName) throws IOException, CertificateException {
    File file = new File(certificateFileName);
    if (!file.isFile()) {
      throw new IOException(String.format("The certificate file %s doesn't exist.", certificateFileName));
    }
    try (InputStream inputStream = new FileInputStream(file)) {
      return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
    }
  }

  private static PrivateKey createPrivateKeyFromPemFile(String keyFileName) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
    try (PemReader pemReader = new PemReader(new FileReader(keyFileName))) {
      PemObject pemObject = pemReader.readPemObject();
      byte[] pemContent = pemObject.getContent();
      pemReader.close();
      return getKeyFactoryInstance().generatePrivate(new PKCS8EncodedKeySpec(pemContent));
    }
  }

  private static KeyManagerFactory createKeyManagerFactory(String clientCertificateFileName,
      String clientKeyFileName, String clientKeyPassword) throws InvalidKeySpecException, NoSuchAlgorithmException, KeyStoreException,
      IOException, CertificateException, UnrecoverableKeyException {
    X509Certificate clientCertificate = createX509CertificateFromFile(clientCertificateFileName);
    PrivateKey privateKey = createPrivateKeyFromPemFile(clientKeyFileName);
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(null, null);
    keyStore.setCertificateEntry("certificate", clientCertificate);
    keyStore.setKeyEntry("private-key", privateKey, clientKeyPassword.toCharArray(), new Certificate[]{clientCertificate});
    KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    keyManagerFactory.init(keyStore, clientKeyPassword.toCharArray());
    return keyManagerFactory;
  }

  private static TrustManagerFactory createTrustManagerFactory(String caCertificateFileName)
      throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException {
    X509Certificate caCertificate = createX509CertificateFromFile(caCertificateFileName);
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(null, null);
    keyStore.setCertificateEntry("ca-certificate", caCertificate);
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    trustManagerFactory.init(keyStore);
    return trustManagerFactory;
  }

  public static SSLContext createAndInitTLS12Context(String caCertificateFileName, String clientCertificateFileName,
      String clientKeyFileName) throws Exception {
    String clientKeyPassword = "";
    try {
      Security.addProvider(new BouncyCastleProvider());
      KeyManager[] keyManagers = createKeyManagerFactory(clientCertificateFileName, clientKeyFileName, clientKeyPassword).getKeyManagers();
      TrustManager[] trustManagers = createTrustManagerFactory(caCertificateFileName).getTrustManagers();
      SSLContext context = SSLContext.getInstance(DESIRED_TLS_VERSION);
      context.init(keyManagers, trustManagers, new SecureRandom());
      return context;
    } catch (Exception e) {
      throw new Exception("TLSv1.2 context cannot be initialized.", e);
    }
  }
}
