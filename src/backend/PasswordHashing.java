package backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {

	public String convertPassword(String password)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			
			byte[] resultByteArray = md.digest(); 
			StringBuilder sb = new StringBuilder(); 
			
			for(byte b : resultByteArray)
			{
				sb.append(String.format("%02x",  b)); 
			}
			
			return sb.toString(); 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ""; 
	}
	
	
}
