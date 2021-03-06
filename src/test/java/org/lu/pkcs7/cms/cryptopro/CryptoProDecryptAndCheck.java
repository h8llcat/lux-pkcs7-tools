package org.lu.pkcs7.cms.cryptopro;

import org.lu.pki.tools.CryptoProCryptoUtils;
import org.lu.pki.tools.CryptoUtils;
import org.lu.pki.tools.PKIXUtils;


public class CryptoProDecryptAndCheck {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		

		
		System.setProperty("http.proxyHost", "192.168.5.15");
		System.setProperty("http.proxyPort", "8080");  
		

		
		byte[] sourceData = "bu-bu777888-000-111-222".getBytes();
		
		CryptoUtils cputilsE = new CryptoProCryptoUtils("C:/Users/user1/Documents/444", "123"); 
		
		cputilsE.signer("luxoft-test2","st1", "luxoft-test1").recipients("st2", "barankevich2012.cer", "pivsaeva_2012_tcs");
		
		byte[] encrypted = cputilsE.actions(sourceData, "C:\\developer\\temp\\cryptopro_enveloped.p7m", "sign -> base64encode -> encrypt -> base64encode");
		
	
		CryptoUtils cputilsD = new CryptoProCryptoUtils("C:/Users/user1/Documents/444", "123"); 
		cputilsD.withVerificationOptions("STORED_CERT_ONLY, SKIP_SELFSIGNED_CERT");
		byte[] decrypted = cputilsD.actions(encrypted, null,  CryptoUtils.ACTION_DECRYPT, CryptoUtils.ACTION_VERIFY, CryptoUtils.ACTION_DETACH);
		
		System.out.println(new String(decrypted));
		
		
		
	}

}
