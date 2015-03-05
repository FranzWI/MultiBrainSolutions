package de.mbs.db.elasticsearch.views;

import java.util.Observable;
import java.util.Vector;

import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit
import org.json.simple.JSONObject

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.objects.UserPortlet;
import de.mbs.abstracts.db.views.UserPortletView;
import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.db.elasticsearch.utils.ElasticsearchHelper

public class ElasticsearchUserPortletview extends UserPortletView {

	private ElasticsearchView view;

	private String[] fieldList = [
		"userID",
		"portletID",
		"settings",
		"order",
		"size",
		"size.xs",
		"size.sm",
		"size.md",
		"size.lg"
	];

	public ElasticsearchUserPortletview(ElasticsearchView view){
		this.view = view;
	}

	@Override
	public String add(UserPortlet data) {
		JSONObject group = new JSONObject();

		group.put("userID", data.getOwnerId());
		group.put("portletID", data.getPortletId());
		group.put("settings", data.getSettings());
		group.put("order", data.getOrder());
		JSONObject size = new JSONObject();
		size.put("xs", data.getXs());
		size.put("sm", data.getSm());
		size.put("md", data.getMd());
		size.put("lg", data.getLg());
		group.put("size", size);

		return ElasticsearchHelper.add(view, "system", "userHasPortlets", group.toJSONString());
	}

	@Override
	public UserPortlet edit(UserPortlet data) {
		JSONObject group = new JSONObject();

		group.put("userID", data.getOwnerId());
		group.put("portletID", data.getPortletId());
		group.put("settings", data.getSettings());
		group.put("order", data.getOrder());
		JSONObject size = new JSONObject();
		size.put("xs", data.getXs());
		size.put("sm", data.getSm());
		size.put("md", data.getMd());
		size.put("lg", data.getLg());
		group.put("size", size);

		return ElasticsearchHelper.edit(view, "system", "userHasPortlets", group.toJSONString(), data);
	}

	@Override
	public boolean remove(String id) {
		return ElasticsearchHelper.remove(view, "system", "userHasPortlets", id);
	}

	@Override
	public UserPortlet get(String id) {
		GetResponse response = this.view.getESClient()
				.prepareGet("system", "userHasPortlets", id).setFields(fieldList)
				.execute().actionGet();
		if (response.isExists()) {
			return responseToGroup(response.getId(), response.getVersion(), response.getFields());
		} else
			return null;
	}

	@Override
	public Vector<UserPortlet> getAll() {
		Vector<UserPortlet> users = new Vector<UserPortlet>();

		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "userHasPortlets", fieldList)) {
			if(hit.getFields() != null) {
				UserPortlet u = this.responseToGroup(hit.getId(), hit.getVersion(), hit.getFields());
				if(u != null)
					users.add(u);
			}
		}

		return users;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<UserPortlet> byOwner(String userid) {
		Vector<UserPortlet> portlets = new Vector<UserPortlet>();
		SearchResponse response = this.view.getESClient()
				.prepareSearch("system").setTypes("userHasPortlets").addFields(fieldList)
				.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("userID", userid)) )
				.execute()
				.actionGet();
		for (SearchHit hit:response.getHits().getHits()) {
			if(hit.getFields() != null){
				UserPortlet u = this.responseToGroup(hit.getId(), hit.getVersion(), hit.getFields());
				if(u != null)
					portlets.add(u);
			}
		}
		return portlets;
	}

	private UserPortlet responseToGroup(id,version, fields) {

		UserPortlet group = new UserPortlet(id, version);

		for (String key : fields.keySet()) {
			def field = fields.get(key);

			switch (key) {
				case "userID":
					group.setOwnerId(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "portletID":
					group.setPortletId(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "settings":
					group.setSettings(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "order":
					group.setOrder(field.getValue() == null ? 0 : field.getValue());
					break;
				case "size.xs":
					group.setXs(field.getValue() == null ? 0 : field.getValue());
					break;
				case "size.sm":
					group.setSm(field.getValue() == null ? 0 : field.getValue());
					break;
				case "size.md":
					group.setMd(field.getValue() == null ? 0 : field.getValue());
					break;
				case "size.lg":
					group.setLg(field.getValue() == null ? 0 : field.getValue());
					break;
			}
		}

		if(group!=null)
			return group;

		return null;
	}
}
