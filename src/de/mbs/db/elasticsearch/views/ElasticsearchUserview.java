package de.mbs.db.elasticsearch.views;


import groovy.xml.MarkupBuilder;
import java.util.Vector;
import org.elasticsearch.client.Client;
import org.json.JSONException;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.db.elasticsearch.ElasticsearchView;

public class ElasticsearchUserview extends UserView {

	@Override
	public String add(User data) 
	{
		org.json.simple.JSONObject jsUser;
		ElasticsearchView myEV = new ElasticsearchView();
		
		try
		{
			//TODO: Memo an mich selbst ElasticsearchView.getProperties(String[][] data) verwenden
			//TODO: Welche SessionID soll bei erster initialisierung gegeben werden? 
			//TODO: Membership = Groups???? -> WORDING!
			
			jsUser = myEV.getProperties(new Object[][]{
						{"userName",data.getUsername()},
						{"firstName",data.getFirstname()},
						{"lastName",data.getLastname()},
						{"email",data.getEmail()},
						{"pw",data.getPw()},
						{"apiKey",data.getApikey()},
						{"sessionID",0},
						{"inGroups",data.getMembership()},
						{"userPortlets", data.getPortlets()} 
					});

			//TODO: Jetzt muss das JSON Objekt noch in die Elasticsearch ecke geworfen werden.
			//Dafür benötige ich jetzt ebend einfach die passenden Indexes wenn ich das ES bisher richtig 
			//verstanden habe
			myEV.getESClient().prepareIndex().setSource(jsUser); 
						
			return "Der user wurde erfolgreich angelegt";
		}
		catch(JSONException jsErr)
		{
			jsErr.printStackTrace();
		}
				
		return null;
	}

	@Override
	public boolean remove(User data) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Vector<User> data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User edit(User data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Diese Methode gibt alle angelegten User zurück
	 * @return Gibt null zurück, insofern es keine Nutzer gibt
	 */
	public Vector<User> getAll()
	{
		Vector<User> myUsers = new Vector<User>();
		
		try
		{
		
			
			return myUsers;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public MarkupBuilder getFormatedContent(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
