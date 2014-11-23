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

}
