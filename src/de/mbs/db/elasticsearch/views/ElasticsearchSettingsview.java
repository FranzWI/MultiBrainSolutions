package de.mbs.db.elasticsearch.views;

import java.util.Vector;

import org.elasticsearch.action.get.GetResponse;

import de.mbs.abstracts.db.objects.Settings;
import de.mbs.abstracts.db.views.SettingsView;

public class ElasticsearchSettingsview extends SettingsView {

	@Override
	public Settings edit(Settings data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Settings get(String id) 
	{
		//GetResponse response = this.view.getESClient().prepareGet("system", "settings", id).execute().actionGet();
		/*
		if (response.isExists()) 
		{
			return responseToSettings(response);

		} 
		else
		*/
			return null;
	}

	@Override
	public Vector<Settings> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Settings responseToSettings(GetResponse response)
	{
		Settings settings = new Settings(response.getId());
		
		
		return settings;
	}

}
