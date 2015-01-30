package de.mbs.db.elasticsearch.views;

import groovy.xml.MarkupBuilder;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.Pair;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.abstracts.mail.MailView;
import de.mbs.abstracts.mail.definition.Mail;
import de.mbs.crypt.Crypt;
import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.db.elasticsearch.utils.ElasticsearchHelper;
import de.mbs.handler.ServiceHandler;

public class ElasticsearchUserview extends UserView {

	// FIXME: es gibt keine register methode???

	private ElasticsearchView view;

	private String[] fieldList = [
		"userName",
		"firstName",
		"lastName",
		"email",
		"pw",
		"apiKey",
		"inGroups",
		"usesPortlets",
		"sessionID",
		"isActive"
	];

	public ElasticsearchUserview(ElasticsearchView view) {
		this.view = view;
	}

	@Override
	public String add(User data) {
		
		//FIXME: sicherstellen das jeder username nur einmal vergeben werden kann
		JSONObject jsonUser = new JSONObject();

		jsonUser.put("userName", data.getUsername());
		jsonUser.put("firstName", data.getFirstname());
		jsonUser.put("lastName", data.getLastname());
		jsonUser.put("email", data.getEmail());
		// FIXME: In meinen Augen ist es nicht korrekt das das Passwort hier hin
		// im klartext �bergeben wird
		jsonUser.put("pw", Crypt.getCryptedPassword(data.getPw()));

		//
		// apikey erzeugen
		jsonUser.put("apiKey", UUID.randomUUID().toString() );
		// FIXME Falls die anpassung in der User.java falsch war, muss diese
		// Stelle �berarbeitet werden
		jsonUser.put("sessionID", data.getSessionId());
		// FIXME pr�fen ob IDs g�ltig sind -> erst m�glich wenn Portlet und
		// Group Views laufen
		jsonUser.put("inGroups",
				ElasticsearchHelper.vectorToJSONArray(data.getMembership())
				.toJSONString());
		// FIXME pr�fen ob IDs g�ltig sind -> erst m�glich wenn Portlet und
		// Group Views laufen
		jsonUser.put("usesPortlets",
				ElasticsearchHelper.vectorToJSONArray(data.getPortlets())
				.toJSONString());
		jsonUser.put("isActive", data.isActive());

		return ElasticsearchHelper.add(view, "system", "user", jsonUser.toJSONString());
	}

	@Override
	public boolean remove(String id) {
		return ElasticsearchHelper.remove(view, "system", "user", id);
	}

	@Override
	public User edit(User data) 
	{
		User old = this.get(data.getId());
		if (old == null)
			return null;
		if(old.isActive() != data.isActive())
		{
			Mail m = new Mail(data.getEmail(),
					"Accountstatus am Multi Brain Cockpit",
					MailView.SENDER,
					"Ihr Account wurde "+(data.isActive()?"aktiviert":"deaktiviert"));
			ServiceHandler.getDatabaseView().sendHtmlMail(m);
		}

		JSONObject jsonUser = new JSONObject();
		jsonUser.put("userName", data.getUsername());
		jsonUser.put("firstName", data.getFirstname());
		jsonUser.put("lastName", data.getLastname());
		jsonUser.put("email", data.getEmail());
		jsonUser.put("pw", data.getPw());
		jsonUser.put("apiKey", data.getApikey());
		jsonUser.put("inGroups",ElasticsearchHelper.vectorToJSONArray(data.getMembership()).toJSONString());
		jsonUser.put("usesPortlets", ElasticsearchHelper.vectorToJSONArray(data.getPortlets()).toJSONString());
		jsonUser.put("sessionId", data.getSessionId());
		jsonUser.put("isActive", data.isActive());

		return ElasticsearchHelper.edit(view, "system", "user", jsonUser.toJSONString(), data);
	}

	@Override
	public User get(String id) 
	{
		GetResponse response = this.view.getESClient()
				.prepareGet("system", "user", id)
				.setFields(fieldList)
				.execute()
				.actionGet();
		if (response.isExists()) 
		{
			return responseToUser(response.getId(), response.getVersion(), response.getFields());
		} else
			return null;
	}

	@Override
	public String login(String username, String password) {
		// Passwort in klartext
		SearchResponse response = this.view.getESClient()
				.prepareSearch("system")
				.setTypes("user")
				.addFields(fieldList)
				.setQuery(
				QueryBuilders.boolQuery()
				.must(QueryBuilders.matchQuery("userName", username))
				.must(QueryBuilders.matchQuery("pw", Crypt.getCryptedPassword(password))))
				.execute()
				.actionGet();


		SearchHit[] hits = response.getHits().getHits();
		if(hits.length == 1 ){
			User u = this.responseToUser(hits[0].getId(), hits[0].getVersion(), hits[0].getFields());
			if(u != null)
				return u;
		}
		return null;
	}

	// Achtung diese suche ist für das Frontend gedacht
	@Override
	public Vector<Pair<SearchResult, String>> search(String search, User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<User> getAll() {
		Vector<User> users = new Vector<User>();
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "user", fieldList)) {
			if(hit.getFields() != null){
				User u = this.responseToUser(hit.getId(), hit.getVersion(), hit.getFields());
				if(u != null)
					users.add(u);
			}
		}
		return users;
	}

	@Override
	public User getUserByApikey(String apikey) {
		SearchResponse response = this.view.getESClient()
				.prepareSearch("system")
				.setTypes("user")
				.addFields(fieldList)
				.setQuery(QueryBuilders.matchQuery("apiKey", apikey) )
				.execute()
				.actionGet();

		SearchHit[] hits = response.getHits().getHits();
		if(hits.length == 1 ){
			User u = this.responseToUser(hits[0].getId(), hits[0].getVersion(), hits[0].getFields());
			if(u != null)
				return u;
		}
		return null;

	}
	
	//INFO: auf wunsch von Denise nachger�stet
	public User getUserByUserName(String username)
	{
		SearchResponse response = this.view.getESClient()
				.prepareSearch("system")
				.setTypes("user")
				.addFields(fieldList)
				.setQuery(QueryBuilders.matchQuery("userName", username) )
				.execute()
				.actionGet();

		SearchHit[] hits = response.getHits().getHits();
		if(hits.length == 1 )
		{
			System.out.println("getUserByUserName() hat einen user mit dem Username " +username +"gefunden ");
			User user = this.responseToUser(hits[0].getId(), hits[0].getVersion(), hits[0].getFields());
			if(user != null)
			{
				return user;
			}
			else
			{
				System.out.println("ResponseToUser() ging schief!");
			}
		}
			System.out.println("getUserByUserName() konnte keinen user mit dem namen " +username +"finden");
			return null;
		}

	private User responseToUser(id,version, fields) {
		try {
			User user = new User(id, version);
			for (String key : fields.keySet()) {
				def field = fields.get(key);
				switch (key) {
					case "userName":
						user.setUsername(field.getValue() == null ? "" : field
						.getValue().toString());
						break;
					case "firstName":
						user.setFirstname(field.getValue() == null ? "" : field
						.getValue().toString());
						break;
					case "lastName":
						user.setLastname(field.getValue() == null ? "" : field
						.getValue().toString());
						break;
					case "email":
						user.setEmail(field.getValue() == null ? "" : field
						.getValue().toString());
						break;
					case "pw":
						user.setPw(field.getValue() == null ? "" : field
						.getValue().toString());
						break;
					case "apiKey":
						user.setApikey(field.getValue() == null ? "" : field
						.getValue().toString());
						break;
					case "sessionID":
						user.setSessionId(field.getValue() == null ? "" : field
						.getValue().toString());
						break;
					case "isActive":
						user.setActive(field.getValue() == null ? false : Boolean
						.getBoolean(field.getValue().toString()));
						break;

					case "inGroups":
						Vector<String> groups = new Vector<String>();
						if (field.getValues() != null) {
							List<Object> values = field.getValues();
							for (Object o : values) {
								groups.add(o.toString());
							}
						}
						user.setMembership(groups);
						break;
					case "usesPortlets":
						Vector<String> portlets = new Vector<String>();
						if (field.getValues() != null) {
							List<Object> values = field.getValues();
							for (Object o : values) {
								portlets.add(o.toString());
							}
						}
						user.setPortlets(portlets);
						break;
				}
			}

			return user;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
}