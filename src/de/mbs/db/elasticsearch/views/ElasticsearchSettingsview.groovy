package de.mbs.db.elasticsearch.views;

import java.util.Vector;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;

import de.mbs.abstracts.db.objects.Settings;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.db.elasticsearch.utils.ElasticsearchHelper

public class ElasticsearchSettingsview extends SettingsView {

	private String[] fieldList = [
		"mailProperties",
		"dbProperties",
		"proxyProperties"
	];

	private ElasticsearchView view;

	public ElasticsearchSettingsview(ElasticsearchView view) {
		this.view = view;
		if(this.getAll().size() == 0){
			JSONObject prob = new JSONObject();

			//wenn ich die Properties richtig verstehe und dazu tobis tabelle nehme gehe ich davon aus das ich direkt die get funktion nehmen muss damit ich auf die Objecte komme
			//nach meinem Verst�ndnis w�rde das getProperties die direkten Strings holen
			prob.put("mailProperties","");
			prob.put("dbProperties", "");
			prob.put("proxyProperties", "");
			
			ElasticsearchHelper.add(view, "system", "settings", prob.toJSONString());
			
		}
	}

	@Override
	public Settings edit(Settings data) {
		JSONObject prob = new JSONObject();

		//wenn ich die Properties richtig verstehe und dazu tobis tabelle nehme gehe ich davon aus das ich direkt die get funktion nehmen muss damit ich auf die Objecte komme
		//nach meinem Verst�ndnis w�rde das getProperties die direkten Strings holen
		prob.put("mailProperties",this.propertiesToString( data.getMailProperties()));
		prob.put("dbProperties", this.propertiesToString(data.getDbProperties()));
		prob.put("proxyProperties", this.propertiesToString(data.getProxyProperties()));

		//FIXME: Gibt Settings dann wirklich ein "Settings" object zur�ck?
		// public static <A extends DatabaseObject> A edit(...)
		return ElasticsearchHelper.edit(view,"system","settings",prob.toJSONString(), data);
	}

	//###################DONE#######################################
	@Override
	public Settings get(String id)
	{
		GetResponse response = this.view.getESClient().prepareGet("system", "settings", id).setFields(fieldList).execute().actionGet();
		if (response.isExists()) {
			return responseToSettings(response.getId(), response.getVersion(), response.getFields());
		} else
			return null;
	}

	@Override
	public Vector<Settings> getAll()
	{
		Vector<Settings> settings = new Vector<Settings>();

		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "settings", fieldList))
		{
			if(hit.getFields() != null)
			{
				Settings set = this.responseToSettings(hit.getId(), hit.getVersion(), hit.getFields());
				if(set != null)
					settings.add(set);
			}
		}
		return settings;
	}

	//FIXME: Da bin ich mir mit den Properties noch nicht ganz sicher ob das �berhaupt funktioniert.
	private Settings responseToSettings(id,version, fields)
	{
		if(fields == null)
			return null;

		Settings  settings = new Settings(id, version);

		for (String key : fields.keySet())
		{
			def field = fields.get(key);

			switch (key)
			{
				case "mailProperties":
					settings.setMailProperties(this.parsePropertiesString(field.getValue() == null ? "" : field.getValue()));
					break;

				case "dbProperties":
					settings.setDbProperties(this.parsePropertiesString(field.getValue() == null ? "" : field.getValue()));
					break;

				case "proxyProperties":
					settings.setProxyProperties(this.parsePropertiesString(field.getValue() == null ? "" : field.getValue()));
					break;
			}
		}
		if(settings==null)
			return null;
		return settings;
	}

	public String propertiesToString(Properties prop){
		StringWriter writer = new StringWriter();
		prop.store(new PrintWriter(writer),"");
		return writer.getBuffer().toString();
	}

	public Properties parsePropertiesString(String s) {
		Properties p = new Properties();
		p.load(new StringReader(s));
		return p;
	}

}
