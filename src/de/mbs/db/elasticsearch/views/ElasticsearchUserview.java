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

	private ElasticsearchView view;

	public ElasticsearchUserview(ElasticsearchView view) {
		this.view = view;
	}

	@Override
	public String add(User data) {
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("userName", data.getUsername());
		jsonUser.put("firstName", data.getFirstname());
		jsonUser.put("lastName", data.getLastname());
		jsonUser.put("email", data.getEmail());
		// TODO Passwort verschlüsseln
		jsonUser.put("pw", data.getPw());
		jsonUser.put("apiKey", data.getApikey());
		// TODO: No data.getSessionID() yet
		jsonUser.put("sessionID", "");
		// TODO: Hier gilt das gleiche wie bei den Portlets
		// FIXME prüfen ob die IDs gültig sind
		jsonUser.put("inGroups",
				ElasticsearchHelper.vectorToJSONArray(data.getMembership())
						.toJSONString());
		// siehe ElasticsearchHelper ...
		// FIXME prüfen ob die IDs gültig sind
		jsonUser.put("usesPortlets",
				ElasticsearchHelper.vectorToJSONArray(data.getPortlets())
						.toJSONString());
		jsonUser.put("isActive", data.isActive());

		IndexResponse response = this.view.getESClient()
				.prepareIndex("system", "user")
				.setSource(jsonUser.toJSONString()).execute().actionGet();
		// siehe definition der add Methode -->
		// de.mbs.abstracts.db.views.definition.AddableView
		if (response.isCreated()) {
			return response.getId();
		} else {
			return null;
		}
	}

	@Override
	public boolean remove(String id) {
		DeleteResponse response = view.getESClient()
				.prepareDelete("system", "user", id).execute().actionGet();
		return response.isFound();
	}

	@Override
	public User edit(User data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(String id) {
		GetResponse response = this.view.getESClient()
				.prepareGet("system", "user", id).execute().actionGet();
		// wenn es einen solchen datensatz gibt :)
		if (response.isExists()) {
			// bei jeden DB objekt was wir abholen
			User user = new User(response.getId(), response.getVersion());

			// TODO: Here I should clarify how to work with the response!
			user.setUsername("");

			return user;
		} else
			// kein treffer für die ID, gibt null
			return null;
	}

	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<SearchResult> search(String search) {
		// TODO
		return null;
	}

	@Override
	public Vector<User> getAll() {
		Vector<User> users = new Vector<User>();
		// TODO geht nicht ....
		return users;
	}

	@Override
	public User getUserByApikey(String apikey) {
		// TODO Auto-generated method stub
		return null;
	}

}
