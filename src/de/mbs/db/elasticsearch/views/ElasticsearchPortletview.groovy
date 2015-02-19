package de.mbs.db.elasticsearch.views;

import java.util.Vector;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.json.simple.JSONObject

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.db.elasticsearch.ElasticsearchView
import de.mbs.db.elasticsearch.utils.ElasticsearchHelper;

public class ElasticsearchPortletview extends PortletView {

	private String[] fieldList = ["name", "path", "description", "usedByGroups", "sizeXS", "sizeSM", "sizeMD", "sizeLG"];
	
	private ElasticsearchView view;
	
	public ElasticsearchPortletview(ElasticsearchView view){
		this.view = view; 
	}
	
	@Override
	public String add(Portlet data) 
	{
		JSONObject portlet = new JSONObject();
		
		portlet.put("name", data.getName());
		portlet.put("path", data.getPath());
		portlet.put("description", data.getDescription());
		portlet.put("usedByGroups", data.getUsedByGroups());
		portlet.put("sizeXS", data.getSizeXS());
		portlet.put("sizeSM", data.getSizeSM());
		portlet.put("sizeMD", data.getSizeMD());
		portlet.put("sizeLG", data.getSizeLG());

		return ElasticsearchHelper.add(view, "system", "portlet", portlet.toJSONString());
	}

	@Override
	public Portlet edit(Portlet data) 
	{
		
		JSONObject portlet = new JSONObject();
		
		portlet.put("name", data.getName());
		portlet.put("path", data.getPath());
		portlet.put("description", data.getDescription());
		portlet.put("usedByGroups", data.getUsedByGroups());
		portlet.put("sizeXS", data.getSizeXS());
		portlet.put("sizeSM", data.getSizeSM());
		portlet.put("sizeMD", data.getSizeMD());
		portlet.put("sizeLG", data.getSizeLG());
		
		return ElasticSearchHelper.edit(view, "system", "group", portlet.toJSONString(), data);
	}

	@Override
	public Portlet get(String id) 
	{
		GetResponse response = this.view.getESClient().prepareGet("system", "portlet", id).setFields(fieldList).execute().actionGet();
		
		if(response.isExists())
			return responseToPortlet(response.getId(), response.getVersion(), response.getFields());
		else
			return null;
	}

	@Override
	public boolean remove(String id) {
		// TODO aktualisierung erforderlich? 
		return ElasticseachHelper.remove(view, "system", "portlet", id);
	}

	@Override
	public Vector<Portlet> getPossiblePortletsForUser(String id) 
	{
		//FIXME: Ich glaube ich habe wieder zu kompliziert gedacht, ich werde einfach sp�ter nochmal dr�ber schauen
		
		// TODO Hier muss jetzt die Auswahl pro user selektiert werden:
		// �bergebene ID entspricht user ID
		// 1. Checke welcher Benutzergruppe user zugewiesen ist
		// 2. Lese mithilfe von Gruppe die m�glichen Portlets aus
		// 3. Werfe die passenden Portlets zur�ck
		
		Vector<Portlet> myPortlets = new Vector<Portlet>();
		Vector<Portlet> allPortlets = this.getAll();
		
		User user = new ElasticsearchUserview(view).get(id);
		
		for(String memberOf : user.getMembership())
		{
			for(Portlet portle : allPortlets)
			{
				Vector<String> usedBy = portle.getUsedByGroups();
				for(String usedByG : usedBy)
				{
					if(usedByG == memberOf)
					{
						//Jetzt muss noch sichergestellt werden, das es dieses Portlet noch nicht in diesem Vector gibt
						for(Portlet exPortl : myPortlets)
						{
							if(exPortl != portle)
								myPortlets.add(portle);
						}
					}
				}
			}
			
		}
		
		//-----------------
		myPortlets.add(this.get())
		ElasticsearchGroupview groupview = new ElasticsearchGroupview(view).get(id);
		Group userGroup = groupview.get(id);
		userGroup
		//--------------------------------
		
		return myPortlets;
	}

	@Override
	public Vector<Portlet> getAll() {
		Vector<Portlet> portlets = new Vector<Portlet>();
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "portlet", fieldList))
		{
			if(hit.getFields()!=null)
			{
				Portlet port = this.responseToPortlet(hit.getId(), hit.getVersion(), hit.getFields());
				if(port != null)
					portlets.add(port);
			}
		}
		return portlets;
	}

}
