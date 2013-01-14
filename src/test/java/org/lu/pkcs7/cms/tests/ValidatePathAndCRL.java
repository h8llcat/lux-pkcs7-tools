package org.lu.pkcs7.cms.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.lu.pki.tools.CertificateVerificationException;
import org.lu.pki.tools.CertificateVerificationResult;
import org.lu.pki.tools.CertificateVerifier;
import org.lu.pki.tools.PKIXUtils;


public class ValidatePathAndCRL {
	
	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		
		System.out.println(PKIXUtils.isIBMJ9());
		
		System.setProperty("http.proxyHost", "192.168.5.15");
		System.setProperty("http.proxyPort", "8080");  
		
		System.setProperty("com.ibm.security.enableCRLDP", String.valueOf(true));
		System.setProperty("com.sun.security.enableCRLDP", String.valueOf(true));
		Security.setProperty("ocsp.enable", String.valueOf(false));
		
	  
        // уникальное имя сертификата открытого ключа
        final String aliasEndCert = "piv";
        
       //инициализация хранилища доверенных сертификатов и ключевого носителя
        final KeyStore keyStore = KeyStore.getInstance("HDImageStore");

        // загрузка содержимого хранилища
        keyStore.load(new FileInputStream("C:\\var\\CPROcsp\\certstore"), null);
        
        final X509Certificate certEnd = (X509Certificate) keyStore.getCertificate(aliasEndCert);
        
        System.out.println("Certificate " + certEnd.getSerialNumber() + " existed in store: " + PKIXUtils.containsCertificateInStore(certEnd, keyStore));
        
        printSubjectAndIssuerDN(certEnd);
        
        int ok = 0;
        int fail = 0;
       for (int z = 0; z < 1; z++) {
        
	        try {
	        	CertificateVerificationResult result = CertificateVerifier.verifyCertificate((X509Certificate)certEnd, keyStore, true);
	        	//System.out.println(result);
	        	ok++;
			} catch (CertificateVerificationException e) {
				System.err.println(e.getMessage());
				fail++;
			}
	     
	        System.err.println("----------------------------------------------------------------------------");
       }
       System.out.println("Ok " + ok);
       System.out.println("Fail " + fail);
	}


	private static void printSubjectAndIssuerDN(final X509Certificate certEnd) {
		if (certEnd instanceof X509Certificate) {
            X509Certificate x509cert = (X509Certificate)certEnd;

            // Get subject
            Principal principal = x509cert.getSubjectDN();
            String subjectDn = principal.getName();
            System.out.println("subjectDn: " + subjectDn);

            // Get issuer
            principal = x509cert.getIssuerDN();
            String issuerDn = principal.getName();
            System.out.println("getIssuerDN: " + issuerDn);
        }
	}

}