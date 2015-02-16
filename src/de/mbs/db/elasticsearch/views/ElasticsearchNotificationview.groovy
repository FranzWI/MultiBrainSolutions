package de.mbs.db.elasticsearch.views;

import java.util.Vector;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.search.SearchHit;
import org.json.JSONObject;

import de.mbs.abstracts.db.objects.Notification;
import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.db.elasticsearch.ElasticsearchView;

public class ElasticsearchNotificationview extends NotificationView {

	private String[] fieldList = ["subject", "icon","link", "creation", "release", "toUser","toGroup"];
	
	private ElasticsearchView view;
	
	public ElasticsearchNotificationview(ElasticsearchView view)
	{
		this.view = view;
	}
	
	//FIXME: Hier müssen noch die Anpassung umgezogen werden, da die Entrs im falschen Sub sysstem sind
	
	
	//FIXME: Funktionstest.
	
	@Override
	public Vector<Notification> getNotificationsForUser(String userId) 
	{
		Vector<Notification> AllMyNotifications = new Vector<Notification>();

		for(Notification myNotifications : this.getAll())
		{
			if(myNotifications.getId() == userId)
				AllMyNotifications.add(myNotifications);
		}
		if(AllMyNotifications != null)
			return AllMyNotifications;
		else
			return null;
	}
	
	//################################DONE############################################
	
	@Override
	public String add(Notification data) 
	{
		JSONObject not = new JSONObject();
		
		//FIXME: Hier heit es .getSubject() bei den Messages heiï¿½t die gleiche funktion .getTopic() wollen wir das noch vereinheitlich?
		not.put("subject", data.getSubject());
		not.put("icon", data.getIcon());
		not.put("link", data.getLink());
		not.put("creation", data.getCreateTimestamp());
		not.put("release", data.getReleaseTimestamp());
		not.put("toUser", data.getToUser());
		not.put("toGroup", data.getToGroup());
		
		return ElasticsearchHelper.add(view, "news", "notification", not.toJSONString());
	}

	@Override
	public Notification edit(Notification data) 
	{
		JSONObject not = new JSONObject();
		
		not.put("subject", data.getSubject());
		not.put("icon", data.getIcon());
		not.put("link", data.getLink());
		not.put("creation", data.getCreateTimestamp());
		not.put("release", data.getReleaseTimestamp());
		not.put("toUser", data.getToUser());
		not.put("toGroup", data.getToGroup());
		
		return ElasticsearchHelper.edit(view,"news","notification",not.toJSONString(), data);
	}

	@Override
	public Notification get(String id) 
	{
		GetResponse response = this.view.getESClient().prepareGet("news", "notification", id).setFields(fieldList).execute().actionGet();
		if (response.isExists()) {
			return responseToGroup(response.getId(), response.getVersion(), response.getFields());
		} else
			return null;
	}

	@Override
	public Vector<Notification> getAll() 
	{
		Vector<Notification> notification = new Vector<Notification>();
		
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "news", "notification", fieldList)) 
		{
			if(hit.getFields() != null){
				Notification not = this.responseToGroup(hit.getId(), hit.getVersion(), hit.getFields());
				if(not != null)
					notification.add(not);
			}
		}
		return notification;
	}

	@Override
	public boolean remove(String id) {
		return ElasticsearchHelper.remove(view, "news", "notification", id);
	}

}
