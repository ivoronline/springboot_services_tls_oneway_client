package com.ivoronline.springboot_services_tls_oneway_client;

import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.security.KeyStore;

public class UtilKeys {

  //================================================================================
  // GET STORE
  //================================================================================
  // KeyStore trustStore = UtilKeys.getStore(storeName, storePassword, storeType);
  public static KeyStore getStore(
    String storeName,     //"/MyKeyStore.jks"
    String storePassword, //"mypassword";
    String storeType      //"JKS"
  ) throws Exception {

    //LOAD KEY STORE
    ClassPathResource classPathResource = new ClassPathResource(storeName);
    InputStream       inputStream       = classPathResource.getInputStream();
    KeyStore          keyStore          = KeyStore.getInstance(storeType);
                      keyStore.load(inputStream, storePassword.toCharArray());

    //RETURN KEY STORE
    return keyStore;

  }

}
