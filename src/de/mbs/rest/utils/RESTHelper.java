package de.mbs.rest.utils;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.handler.ServiceHandler;

public class RESTHelper {

	public static JSONArray groupsToJSONArray(Vector<String> groupIds){
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
	
}
