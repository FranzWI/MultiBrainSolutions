package de.mbs.db.elasticsearch.views;

import groovy.xml.MarkupBuilder;

import java.util.Vector;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.UserView;

public class ElasticsearchUserview extends UserView {

	
	@Override
	public String add(User data) 
	{
		ElasticsearchView esView = new ElasticsearchView();
		
		JSONObject  jsonUser = new JSONObject();
		jsonUser.put("userName",data.getUsername());
		jsonUser.put("firstName",data.getFirstname());
		jsonUser.put("lastName", data.getLastname());
		jsonUser.put("email",data.getEmail());
		jsonUser.put("pw",data.getPw());
		jsonUser.put("apiKey",data.getApikey);
		//TODO: No data.getSessionID() yet
		jsonUser.put("sessionID","");
		//TODO: Hier gilt das gleiche wie bei den Portlets
		jsonUser.put("inGroups",data.getMembership());
		//TODO: Hier muss ich mal schauen ob er richtig damit umgeht bezogen auf die Zerlegung der zurückgegeben Values: Vector<String>
		jsonUser.put("usesPortlets", data.getPortlets());
		jsonUser.put("isActive",data.isActive());
		
		//TODO: Anpassen
		IndexResponse response = esView.getESClient().prepareIndex("system","user").setSource("JSON OBJECT").execute().actionGet();
		
		//Soll es wirklich ein String sein, und wenn ja was? -> Added / Username /etc...
		return "User added!";
	}

	@Override
	public boolean remove(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User edit(User data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(String id) 
	{
		IndexRequest indexRequest = new IndexRequest("system","user",id);
		IndexRequest response = getESClient().index(indexRequest).actionGet();
		
		User user = new User();
		
		//TODO: Here I should clarify how to work with the response!
		data.setUsername("");
		
		
		return null;
	}

	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<SearchResult> search(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByApikey(String apikey) {
		// TODO Auto-generated method stub
		return null;
	}

}
