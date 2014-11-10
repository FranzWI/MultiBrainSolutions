package de.mbs.frontend;

import java.awt.Color;
import java.math.BigInteger;

import de.mbs.abstracts.db.objects.User;

public class FrontendHelper {

	public static String getColors(User u) {
		String colors = "";
		String hex = u.getFirstname() + " " + u.getLastname();
		hex = String.format("%x", new BigInteger(hex.getBytes()));
		hex = hex.substring(hex.length()-6);
		colors = "#"+hex+":#"+(useBlack(hex2Rgb(hex))?"000":"fff");
		return colors;
	}
	
	private static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 0, 2 ), 16 ),
	            Integer.valueOf( colorStr.substring( 2, 4), 16 ),
	            Integer.valueOf( colorStr.substring( 4, 6 ), 16 ) );
	}
	
	private static boolean useBlack(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color
				.getBlue()) / 1000;
		return y >= 128 ? true : false;
	}

}
