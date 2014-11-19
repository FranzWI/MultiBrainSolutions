package de.mbs.db.elasticsearch.views;

import java.util.Vector;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.db.elasticsearch.utils.ElasticsearchHelper;

public class ElasticsearchUserview extends UserView {

	// FIXME: es gibt keine register methode???
		
	private ElasticsearchView view;

	public ElasticsearchUserview(ElasticsearchView view) {
		this.view = view;
	}

	@Override
	public String add(User data) 
	{
		JSONObject jsonUser = new JSONObject();
		
		jsonUser.put("userName", data.getUsername());
		jsonUser.put("firstName", data.getFirstname());
		jsonUser.put("lastName", data.getLastname());
		jsonUser.put("email", data.getEmail());
		// FIXME: In meinen Augen ist es nicht korrekt das das Passwort hier hin im klartext übergeben wird
		jsonUser.put("pw", new Crypt().getCryptedPassword(data.getPw()));
		jsonUser.put("apiKey", data.getApikey());
		// FIXME Falls die anpassung in der User.java falsch war, muss diese Stelle überarbeitet werden
		jsonUser.put("sessionID", data.getSessionId());
		// FIXME prüfen ob IDs gültig sind -> erst möglich wenn Portlet und Group Views laufen
		jsonUser.put("inGroups", ElasticsearchHelper.vectorToJSONArray(data.getMembership()).toJSONString());
		// FIXME prüfen ob IDs gültig sind -> erst möglich wenn Portlet und Group Views laufen
		jsonUser.put("usesPortlets", ElasticsearchHelper.vectorToJSONArray(data.getPortlets()).toJSONString());
		jsonUser.put("isActive", data.isActive());
		
		IndexResponse response = this.view.getESClient().prepareIndex("system", "user").setSource(jsonUser.toJSONString()).execute().actionGet();
		// Logisch das ich nicht selbst drauf gekommen bin das im Kommentar der AddableView der gewünschte Rückgabewert steht :D :D :D :D :D
		if (response.isCreated()) 
		{
			return response.getId();
		} 
		else 
		{
			return null;
		}
	}

	@Override
	public boolean remove(String id) 
	{
		DeleteResponse response = view.getESClient().prepareDelete("system", "user", id).execute().actionGet();
		return response.isFound();
	}

	@Override
	public User edit(User data) 
	{
	
		// FIXME: Funktioniert das so überhaupt?
		
		BulkResponse response = client().prepareBulk()
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user").setScript("ctx._source.userName" = data.getUsername()))
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user").setScript("ctx._source.firstName" = data.getFirstname()))
				.add(this.view.getESClient().prepareIndex().setIndex("system")	
					.setType("user").setScript("ctx._source.lastName" = data.getLastname()))
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user").setScript("ctx._source.email" = data.getEmail()))
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user").setScript("ctx._source.pw" = data.getPW()))
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user").setScript("ctx._source.apiKey" = data.getApikey()))
			//FIXME: ist das hier an der stelle überhaupt möglich JSON Strings darein zu bauen?
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user")
					.setScript("ctx._source.inGroups" = ElasticsearchHelper.vectorToJSONArray(data.getMembership()).toJSONString()))
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user")
					.setScript("ctx._source.usesPortlets" = ElasticsearchHelper.vectorToJSONArray(data.getPortlets()).toJSONString()))
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user").setScript("ctx._source.sessionID" = data.getSessionId()))
				.add(this.view.getESClient().prepareIndex().setIndex("system")
					.setType("user").setScript("ctx._source.isActive" = data.isActive()))
				.execute.actionGet();
		
		//Wenn update funktioniert hat, dann datensatz, sonst null
		if (response.isCreated()) 
		{
			return this.responseToUser(response);
		} 
		else 
		{
			return null;
		}
	}

	@Override
	public User get(String id) 
	{
		GetResponse response = this.view.getESClient().prepareGet("system", "user", id).execute().actionGet();
		
		if (response.isExists()) 
		{
			responseToUser(user);

			return user;
		} 
		else
			return null;
	}

	@Override
	public String login(String username, String password) 
	{
		//TODO: kommt das passwort hier schon crypted oder klartext?
		Vector<SearchHit> userFound = search(username);
        
		if(userFound.size()!=1)
		{
			User user = this.get(userFound(1).getId());
			if(user.getPw() != new Crypt().getCryptedPassword(password))
				if(user.isActive)
					return user.getId();
		}
		else
			return null;
	}

	@Override
	public Vector<SearchHit> search(String search) 
	{
		SearchResponse response = this.view.getESClient().prepareSearch("system")
				.setTypes("user")
				.setQuery(QueryBuilder.queryString(search))
				.execute().actionGet();

		SearchHit[] hit = response.getHits().getHits();
		
		Vector<SearchHit> hits = new Vector<SearchHit>();
		
		for(SearchHit  myhit : hit)
		{
			hits.add(myhit);
		}
		
		if (hits!=null)
			return hits;
		else
			return null;
	}

	@Override
	public Vector<User> getAll() 
	{
		
		// FIXME: GEHT NICHT GIBTS NICHT :-*
		//    Ich habe mir hier ein Workarround überlegt: 
		//	  Suche einen Type druch mit allen Indexen != 0 => Wir erhalten alle indexe 
		//    Nun itteriere durch alle erhaltenen Indexe und mach ein get
		//    erzeuge aus den Response von Get User und add into vector
		Vector<User> users = new Vector<User>();
		
		//Suche alle Elemente die im Index System unter dem Typ user abgelegt wurden
		SearchResponse response = this.view.getESClient().prepareSearch("system")
				.setTypes("user")
				.execute().actionGet();
		
		// TODO: @Franz -> Warum doppelt .getHits()??? http://stackoverflow.com/questions/14297329/elasticsearch-full-text-search-using-java-api
		SearchHit[] results = respones.getHits().getHits();
		
		for(SearchHit hit : results)
		{
			users.add(this.get(hit.getId()));
		}
		
		if(users != null)
			return users;
		else
			return null;
	}

	@Override
	public User getUserByApikey(String apikey) 
	{
		SearchResponse response = this.view.getESClient().prepareSearch("system")
				.setTypes("user")
				.setQuery(QueryBuilder.matchQuery("apiKey",apikey))
				.execute().actionGet();
		
		SearchHit result = response.getHits().getHits();
		
		User user = responseToUser(get(result.getId()));
		
		if(user!=null)
			return user;
		else
			return null;
	}
	
	private User responseToUser(Response response)
	{
		User user = new User(response.getId(), response.getVersion());
		
		user.setUsername(response.getField("userName"));
		user.setFirstname(response.getField("firstName"));
		user.setLastName(response.getField("lastName"));
		user.setEmail(response.getField("email"));
		user.setPw(response.getField("pw"));
		user.setApikey(response.getField("apiKey"));
		user.setMembership(response.getField("inGroups"));
		user.setPortlets(response.getField("usesPortlets"));
		user.setSessionId(response.getField("sessionID"));
		user.setActive(response.getField("isActive"));
	}

}
