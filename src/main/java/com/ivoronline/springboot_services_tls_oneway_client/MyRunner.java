package com.ivoronline.springboot_services_tls_oneway_client;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;

@Component
public class MyRunner implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {

    //LOAD TRUST STORE
    ClassPathResource classPathResource = new ClassPathResource("ClientTrustStore.jks");
    InputStream       inputStream       = classPathResource.getInputStream();
    KeyStore          trustStore        = KeyStore.getInstance("JKS");
                      trustStore.load(inputStream, "mypassword".toCharArray());

    //SPECIFY TRUST STORE
    SSLContext sslContext = new SSLContextBuilder()
      .loadTrustMaterial(trustStore, null)
      .build();

    //PLUMBING
    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
      sslContext,
      NoopHostnameVerifier.INSTANCE
    );
    CloseableHttpClient                    httpClient     = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
    RestTemplate                           restTemplate   = new RestTemplate();
                                           restTemplate.setRequestFactory(requestFactory);

    //CALL SERVER
    String result = restTemplate.getForObject(new URI("https://localhost:8085/Hello"), String.class);

    //DISPLAY RESULT
    System.out.println(result);

  }

}
