package com.ivoronline.springboot_services_tls_oneway_client;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import javax.net.ssl.SSLContext;
import java.security.KeyStore;

public class UtilClientRestTemplate {

  //=======================================================================================
  // GET REQUEST FACTORY FOR ONE WAY TLS
  //=======================================================================================
  // Difference is in SSLContext => TrustMaterial
  public static HttpComponentsClientHttpRequestFactory getRequestFactoryForOneWayTLS(
    String trustStoreName,     //"/MyKeyStore.jks"
    String trustStorePassword, //"mypassword";
    String trustStoreType      //"JKS"
  ) throws Exception {

    //LOAD TRUST STORE (For One-Way TLS)
    KeyStore trustStore = UtilKeys.getStore(trustStoreName, trustStorePassword, trustStoreType);

    //CONFIGURE REQUEST FACTORY
    SSLContext sslContext = new SSLContextBuilder()
      .loadTrustMaterial(trustStore, null) //For One-Way TLS
      .build();

    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
      sslContext,
      NoopHostnameVerifier.INSTANCE
    );

    CloseableHttpClient httpClient= HttpClients
      .custom()
      .setSSLSocketFactory(socketFactory)
      .build();

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

    //RETURN REQUEST FACTORY
    return requestFactory;

  }

}
