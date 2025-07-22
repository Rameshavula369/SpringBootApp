package com.effort.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



public class UrlSigner {
	
	private static byte[] key;
	
	public UrlSigner(String keyString) throws IOException {
		keyString = keyString.replace('-', '+');
		keyString = keyString.replace('_', '/');
		Log.info(UrlSigner.class, "Key: " + keyString);
		this.key = Base64.decode(keyString);
	}
	public String signRequest(String path, String query, String body,
			String keyString) throws NoSuchAlgorithmException,
			InvalidKeyException, UnsupportedEncodingException,
			URISyntaxException {

		String resource = path + query + body + keyString;
		 Log.info(getClass(), "signRequest() // resource = "+resource);
		 Log.info(getClass(), "signRequest() // resource length = "+resource.length());

		SecretKeySpec sha1Key = new SecretKeySpec(key, "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(sha1Key);

		// compute the binary signature for the request
		byte[] sigBytes = mac.doFinal(resource.getBytes());

		// base 64 encode the binary signature
		String signature = Base64.encodeBytes(sigBytes);

		// convert the signature to 'web safe' base 64
		signature = signature.replace('+', '-');
		signature = signature.replace('/', '_');
		Log.info(getClass(), "signRequest() // signature = "+signature);
		return signature;
	}
	
	public static String getSignedUrl(String urlString, String body,
			String keyString) throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, URISyntaxException {

		URL url = new URL(urlString);
		UrlSigner signer = new UrlSigner(keyString);
		String request = signer
				.signRequest(
						url.getPath(),
						url.getQuery().substring(0,
								url.getQuery().lastIndexOf("&")), body,
						keyString);
		// String request = signer.signRequest(body,keyString);

		Log.info(UrlSigner.class, "Signed URL :" + request);
		return request;
	}

}
