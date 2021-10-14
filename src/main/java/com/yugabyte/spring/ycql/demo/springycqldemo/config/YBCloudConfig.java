package com.yugabyte.spring.ycql.demo.springycqldemo.config;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YBCloudConfig {
	
	
	@Value("${yugabyte.cloud.ssl.cert.path}")
	private String certfilePath;

	@Bean
	public CqlSessionBuilderCustomizer cqlSessionBuilderCustomizer() {
		return cqlSessionBuilder -> {
			try {
				cqlSessionBuilder.withSslContext(createSSLHandler(certfilePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

	}
	
	 private static SSLContext createSSLHandler(String certfile) {
	        try {
	            CertificateFactory cf = CertificateFactory.getInstance("X.509");
	            FileInputStream fis = new FileInputStream(certfile);
	            X509Certificate ca;
	            try {
	              ca = (X509Certificate) cf.generateCertificate(fis);
	            } catch (Exception e) {
	              System.err.println("Exception generating certificate from input file: " + e);
	              return null;
	            } finally {
	              fis.close();
	            }

	            // Create a KeyStore containing our trusted CAs
	            String keyStoreType = KeyStore.getDefaultType();
	            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
	            keyStore.load(null, null);
	            keyStore.setCertificateEntry("ca", ca);

	            // Create a TrustManager that trusts the CAs in our KeyStore
	            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
	            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
	            tmf.init(keyStore);

	            SSLContext sslContext = SSLContext.getInstance("TLS");
	            sslContext.init(null, tmf.getTrustManagers(), null);
	            return sslContext;
	        } catch (Exception e) {
	            System.err.println("Exception creating sslContext: " + e);
	            return null;
	        }
	    }
}
