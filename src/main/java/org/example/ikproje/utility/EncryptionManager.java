package org.example.ikproje.utility;

import org.example.ikproje.exception.ErrorType;
import org.example.ikproje.exception.IKProjeException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionManager {
	private static final String ALGORITHM="AES";
	private static final String KEY = "qAz1+!rUt7w$eS5#";
	
	public static String encrypt(String data)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
			       BadPaddingException {
		Cipher cipher=Cipher.getInstance(ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] encryptedData= cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptedData);
	}
	public static String decrypt(String data)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
			       BadPaddingException {
		Cipher cipher=Cipher.getInstance(ALGORITHM);
		SecretKeySpec keySpec=new SecretKeySpec(KEY.getBytes(), ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decodedData=Base64.getDecoder().decode(data);
		byte[] originalData=cipher.doFinal(decodedData);
		return new String(originalData);
	}
	
	public static String getEncryptedPassword(String password) {
		String encryptedPassword = "";
		try {
			encryptedPassword = EncryptionManager.encrypt(password);
		}
		catch (Exception e) {
			throw new IKProjeException(ErrorType.ENCRYPTION_FAILED);
		}
		return encryptedPassword;
	}
	
}