package de.mbs.db.elasticsearch.views;

import java.util.Vector;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.search.SearchHit;
import org.json.JSONObject;

import de.mbs.abstracts.db.objects.Settings;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.db.elasticsearch.ElasticsearchView;

public class ElasticsearchSettingsview extends SettingsView {

	private String[] fieldList = ["mailProperties","dbProperties", "proxyProperties"];
	
	private ElasticsearchView view;
	
	public ElasticsearchNotificationview(ElasticsearchView view)
	{
		this.view = view;
	}
	
	@Override
	public Settings edit(Settings data) 
	{
		JSONObject prob = new JSONObject();
		
		//wenn ich die Properties richtig verstehe und dazu tobis tabelle nehme gehe ich davon aus das ich direkt die get funktion nehmen muss damit ich auf die Objecte komme
		//nach meinem Verständnis würde das getProperties die direkten Strings holen
		prob.put("mail", data.getMailProperties());
		prob.put("db.user", data.getDbProperties());
		prob.put("proxy", data.getProxyProperties());
		
		//FIXME: Gibt Settings dann wirklich ein "Settings" object zurück?
		// public static <A extends DatabaseObject> A edit(...) 
		return ElasticsearchHelper.edit(view,"system","settings",prob.toJSONString(), data);
	}

	@Override
	public Settings get(String id) 
	{
		GetResponse response = this.view.getESClient().prepareGet("system", "settings", id).setFields(fieldList).execute().actionGet();
		if (response.isExists()) {
			return responseToGroup(response.getId(), response.getVersion(), response.getFields());
		} else
			return null;
	}

	@Override
	public Vector<Settings> getAll() {
				
		Vector<Settings> settings = new Vector<Settings>();
		
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "settings", fieldList)) 
		{
			if(hit.getFields() != null){
				Settings set = this.responseToGroup(hit.getId(), hit.getVersion(), hit.getFields());
				if(not != null)
					settings.add(set);
			}
		}
		return settings;
	}
	
	private Settings responseToSettings(GetResponse response)
	{
		Settings settings = new Settings(response.getId());
		
		return settings;
	}

}
