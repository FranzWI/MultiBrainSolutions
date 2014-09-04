package de.mbs.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class Crypt 
{
	
	private static String getPepper()
	{
		return "ThisIsOurNewPepperBasqualWepper";
	}
	
	private static String getSalt()
	{
		return "124124##+1241§=4";
	}
	
	private static String getSHA1Hash(String string) 
	{
		try 
		{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			return (new HexBinaryAdapter()).marshal(md.digest(string.getBytes()));
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String getCryptedPassword(String password)
	{
		return getSHA1Hash((getSHA1Hash(password+getPepper()))+getSalt());
	}
	

	
	
}
