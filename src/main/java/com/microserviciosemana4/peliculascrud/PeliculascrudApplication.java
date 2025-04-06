package com.microserviciosemana4.peliculascrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeliculascrudApplication {

	public static void main(String[] args) {
		
		// Configuración del almacén de claves y almacén de confianza
        //System.setProperty("javax.net.ssl.keyStore", "C:/Users/Gonzalo/Oracle/WalletBD2025/keystore.jks");
        //System.setProperty("javax.net.ssl.keyStorePassword", "OracleCloud123");
        //System.setProperty("javax.net.ssl.trustStore", "C:/Users/Gonzalo/Oracle/WalletBD2025/truststore.jks");
        //System.setProperty("javax.net.ssl.trustStorePassword", "OracleCloud123");
		SpringApplication.run(PeliculascrudApplication.class, args);
	}

}
