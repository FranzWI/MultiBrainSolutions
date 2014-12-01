package de.mbs.rest.utils;

import java.util.Random;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.handler.ServiceHandler;

public class RESTHelper {

	public static JSONArray groupsToJSONArray(Vector<String> groupIds) {
		JSONArray groups = new JSONArray();
		for (String groupId : groupIds) {
			Group g = ServiceHandler.getDatabaseView().getGroupView()
					.get(groupId);
			// TODO G == null
			if (g != null) {
				JSONObject group = new JSONObject();
				group.put("id", g.getId());
				group.put("name", g.getName());
				group.put("description", g.getDescription());
				groups.add(group);
			}
		}
		return groups;
	}

	public static String randomPassword() {
		String[][] chars = new String[4][];
		chars[0] = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z" };
		chars[1] = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z" };
		chars[2] = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10" };
		chars[3] = new String[] { "#", "€", "$", "§", "&", "%", "?", "!", "+",
				"-", "*" };
		String password = "";
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			int index = Math.abs(r.nextInt()) % chars.length;
			int letter = Math.abs(r.nextInt()) % chars[index].length;
			password += chars[index][letter];

		}
		return password;
	}

	public static JSONObject stringToJSONObject(String s) throws ParseException {
		JSONParser parser = new JSONParser();
		s = s.replaceAll("\\(", "{").replaceAll("\\)", "}");
		return (JSONObject) parser.parse(s);
	}

	public static String decodeURIComponent(String encodedURI) {
		char actualChar;

		StringBuffer buffer = new StringBuffer();

		int bytePattern, sumb = 0;

		for (int i = 0, more = -1; i < encodedURI.length(); i++) {
			actualChar = encodedURI.charAt(i);

			switch (actualChar) {
			case '%': {
				actualChar = encodedURI.charAt(++i);
				int hb = (Character.isDigit(actualChar) ? actualChar - '0'
						: 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				actualChar = encodedURI.charAt(++i);
				int lb = (Character.isDigit(actualChar) ? actualChar - '0'
						: 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				bytePattern = (hb << 4) | lb;
				break;
			}
			case '+': {
				bytePattern = ' ';
				break;
			}
			default: {
				bytePattern = actualChar;
			}
			}

			if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
				sumb = (sumb << 6) | (bytePattern & 0x3f);
				if (--more == 0)
					buffer.append((char) sumb);
			} else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
				buffer.append((char) bytePattern);
			} else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
				sumb = bytePattern & 0x1f;
				more = 1;
			} else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
				sumb = bytePattern & 0x0f;
				more = 2;
			} else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
				sumb = bytePattern & 0x07;
				more = 3;
			} else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
				sumb = bytePattern & 0x03;
				more = 4;
			} else { // 1111110x
				sumb = bytePattern & 0x01;
				more = 5;
			}
		}
		return buffer.toString();
	}

}
