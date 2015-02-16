package de.mbs.db.elasticsearch.views;

import java.util.Vector;

import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.search.SearchHit
import org.elasticsearch.index.query.QueryBuilders;
import org.json.simple.JSONObject

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.views.GroupView;
import de.mbs.db.elasticsearch.ElasticsearchView
import de.mbs.db.elasticsearch.utils.ElasticsearchHelper;


public class ElasticsearchGroupview extends GroupView {

	private ElasticsearchView view;

	private String[] fieldList = ["name", "description"];

	public ElasticsearchGroupview(ElasticsearchView view){
		this.view = view;

		String adminId = this.checkIfGroupExists("Admin")
		String userId = this.checkIfGroupExists("Nutzer");
		
		if( adminId!=null && userId!=null)
		{
			this.adminGroupId = adminId;
			this.userGroupId = userId;
		}
		else
		{
			if(adminId == null)
			{
				Group admins = new Group(null);
				admins.setName("Admin");
				admins.setDescription("Administratoren")
				this.adminGroupId = this.add(admins);
			}
			if(userId == null)
			{
				Group users = new Group(null);
				users.setName("Nutzer");
				users.setDescription("Nutzer")
				this.userGroupId = this.add(users);
			}
			if(this.adminGroupId==null || this.userGroupId == null)
			{
				System.err.println("ES: GV Gruppen Admin und / oder Nutzer sind nicht angelegt");
				System.exit(1);
			}
		}
	}

	private String checkIfGroupExists(String group){
		SearchResponse response = this.view.getESClient()
				.prepareSearch("system")
				.setTypes("group")
				.addFields(fieldList)
				.setQuery(QueryBuilders.matchQuery("name", group) )
				.execute()
				.actionGet();

		SearchHit[] hits = response.getHits().getHits();
		if(hits.length == 1 )
		{
			Group myGroup = this.responseToGroup(hits[0].getId(),hits[0].getVersion(), hits[0].getSource());
			if(myGroup != null)
				return myGroup.getId();
		}
		return null;
	}
	
	@Override
	public String add(Group data) {
		JSONObject group = new JSONObject();

		group.put("name", data.getName());
		group.put("description", data.getDescription());

		return ElasticsearchHelper.add(view, "system", "group", group.toJSONString());
	}

	@Override
	public Group edit(Group data) {
		JSONObject group = new JSONObject();

		group.put("name", data.getName());
		group.put("description", data.getDescription());

		return ElasticsearchHelper.edit(view,"system","group",group.toJSONString(), data);
	}

	@Override
	public Group get(String id) {
		GetResponse response = this.view.getESClient()
				.prepareGet("system", "group", id).setFields(fieldList)
				.execute().actionGet();
		if (response.isExists()) {
			return responseToGroup(response.getId(), response.getVersion(), response.getSource());
		} else
			return null;
	}

	@Override
	public boolean remove(String id) {
		//TODO Achtung Nutzer & Portlets aktualisieren
		return ElasticsearchHelper.remove(view, "system", "group", id);
	}

	@Override
	public Vector<Group> getAll() {
		Vector<Group> users = new Vector<Group>();
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "group", fieldList)) {
			if(hit.getSource() != null){
				Group u = this.responseToGroup(hit.getId(), hit.getVersion(), hit.getSource());
				if(u != null)
					users.add(u);
			}
		}
		return users;
	}

	private Group responseToGroup(id,version, fields) {
		
		Group group = new Group(id, version);
		
		for (String key : fields.keySet()) 
		{
			def field = fields.get(key);
			
			switch (key) 
			{
				case "name":
					group.setName(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "description":
					group.setDescription(field.getValue() == null ? "" : field.getValue().toString());
					break;
			}
		}
		
		if(group!=null)
			return group;
			
		return null;
		
	}
}
