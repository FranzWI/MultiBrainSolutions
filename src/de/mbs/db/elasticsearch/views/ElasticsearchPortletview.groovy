package de.mbs.db.elasticsearch.views;

import java.util.Map;
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

	private String[] fieldList = [
		"name",
		"path",
		"description",
		"usedByGroups",
		"sizeXS",
		"sizeSM",
		"sizeMD",
		"sizeLG",
		"isMultiple"
	];

	private ElasticsearchView view;

	public ElasticsearchPortletview(ElasticsearchView view){
		this.view = view;
	}

	@Override
	public String add(Portlet data) {
		JSONObject portlet = new JSONObject();

		portlet.put("name", data.getName());
		portlet.put("path", data.getPath());
		portlet.put("description", data.getDescription());
		portlet.put("usedByGroups", ElasticsearchHelper.vectorToJSONArray(data.getUsedByGroups()).toJSONString());
		portlet.put("sizeXS", data.getSizeXS());
		portlet.put("sizeSM", data.getSizeSM());
		portlet.put("sizeMD", data.getSizeMD());
		portlet.put("sizeLG", data.getSizeLG());
		portlet.put("isMultiple", data.isMultiple());

		return ElasticsearchHelper.add(view, "system", "portlet", portlet.toJSONString());
	}

	@Override
	public Portlet edit(Portlet data) {

		JSONObject portlet = new JSONObject();

		portlet.put("name", data.getName());
		portlet.put("path", data.getPath());
		portlet.put("description", data.getDescription());
		portlet.put("usedByGroups", ElasticsearchHelper.vectorToJSONArray(data.getUsedByGroups()).toJSONString());
		portlet.put("sizeXS", data.getSizeXS());
		portlet.put("sizeSM", data.getSizeSM());
		portlet.put("sizeMD", data.getSizeMD());
		portlet.put("sizeLG", data.getSizeLG());
		portlet.put("isMultiple", data.isMultiple());

		return ElasticSearchHelper.edit(view, "system", "portlet", portlet.toJSONString(), data);
	}

	@Override
	public Portlet get(String id) {
		if(id == null)
			return null;
		System.out.println("DEBUG "+id);
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
	public Vector<Portlet> getPossiblePortletsForUser(String id) {
		User user = this.view.getUserView().get(id);
		if (user != null) {
			Vector<String> userGroups = user.getMembership();
			Vector<Portlet> result = new Vector<Portlet>();
			for (Portlet portlet : this.getAll()) {
				Vector<String> portletGroups = portlet.getUsedByGroups();
				for (String groupId : portletGroups) {
					if (userGroups.contains(groupId)) {
						boolean add = true;
						if(!portlet.isMultiple()){
							for(Map<String, String> map: user.getPortlets()){
								if(map.containsValue(portlet.getId())){
									add = false;
								}
							}
						}
						if (add) {
							result.add(portlet);
							break;
						}
					}
				}
			}
			return result;
		}
		return null;
	}

	@Override
	public Vector<Portlet> getAll() {
		Vector<Portlet> portlets = new Vector<Portlet>();
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "portlet", fieldList)) {
			if(hit.getFields()!=null) {
				Portlet port = this.responseToPortlet(hit.getId(), hit.getVersion(), hit.getFields());
				if(port != null)
					portlets.add(port);
			}
		}
		return portlets;
	}

	private Portlet responseToPortlet(id,version, fields) {
		Portlet p = new Portlet(id, version);
		if(fields == null)
			return null;
		for(String key : fields.keySet()){
			def field = fields.get(key);
			switch(key){
				case "name":
					p.setName(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "path":
					p.setPath(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "description":
					p.setDescription(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "usedByGroups":
					Vector<String> groups = new Vector<String>();
					if (field.getValues() != null) 
					{
						List<Object> values = field.getValues();
						for (Object o : values) 
						{
							groups.add(o.toString());
						}
					}
					p.setUsedByGroups(groups);
					break;
				case "sizeXS":
					p.setSizeXS(field.getValue() == null ? 1 : ((int) field.getValue()));
					break;
				case "sizeSM":
					p.setSizeSM(field.getValue() == null ? 1 : ((int) field.getValue()));
					break;
				case "sizeMD":
					p.setSizeMD(field.getValue() == null ? 1 : ((int) field.getValue()));
					break;
				case "sizeLG":
					p.setSizeLG(field.getValue() == null ? 1 : ((int)  field.getValue()));
					break;
				case "isMultiple":
					p.setMultiple(field.getValue() == null ? false : (field.getValue()));
					break;
			}
		}

		return p;
	}
}
