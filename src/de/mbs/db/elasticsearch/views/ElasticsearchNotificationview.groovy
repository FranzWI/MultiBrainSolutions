package de.mbs.db.elasticsearch.views;

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date;
import java.util.Vector;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject

import de.mbs.abstracts.db.objects.Notification;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.db.elasticsearch.ElasticsearchView;
import de.mbs.db.elasticsearch.utils.ElasticsearchHelper

public class ElasticsearchNotificationview extends NotificationView {

	private String[] fieldList = ["subject", "icon","link", "creation", "release", "to.User","to.Group"];
	
	private ElasticsearchView view;
	
	public ElasticsearchNotificationview(ElasticsearchView view)
	{
		this.view = view;
	}
	
	//FIXME: Hier m�ssen noch die Anpassung umgezogen werden, da die Entrs im falschen Sub sysstem sind
	//FIXME: Funktionstest.
	
	@Override
	public Vector<Notification> getNotificationsForUser(String userId) 
	{
		Date currentTime = new Date();
		Vector<Notification> notification = new Vector<Notification>();
		User u = this.view.getUserView().get(userId);
		if (u != null) {
			for (Notification n : this.getAll()) {
				if (n.getToUser().contains(u.getId())) {
					if (n.getReleaseTimestamp().getTime() <= currentTime
							.getTime())
						notification.add(n);
				} else {
					for (String groupId : u.getMembership()) {
						if (n.getToGroup().contains(groupId)) {
							if (n.getReleaseTimestamp().getTime() <= currentTime
									.getTime()) {
								notification.add(n);
								break;
							}
						}
					}
				}
			}
			return notification;
		}
		return null;
	}
	
	//################################DONE############################################
	
	@Override
	public String add(Notification data) 
	{
		JSONObject not = new JSONObject();
		not.put("subject", data.getSubject());
		not.put("icon", data.getIcon());
		not.put("link", data.getLink());
		not.put("creation", ElasticsearchHelper.DATETIME_NO_MILLIS_FORMATER.format( data.getCreateTimestamp()));
		not.put("release", ElasticsearchHelper.DATETIME_NO_MILLIS_FORMATER.format(data.getReleaseTimestamp()));
		JSONObject to = new JSONObject();
		to.put("User", ElasticsearchHelper.vectorToJSONArray( data.getToUser()));
		to.put("Group", ElasticsearchHelper.vectorToJSONArray( data.getToGroup()));
		not.put("to", to);
		
		return ElasticsearchHelper.add(view, "system", "notification", not.toJSONString());
	}

	@Override
	public Notification edit(Notification data) 
	{
		JSONObject not = new JSONObject();
		
		not.put("subject", data.getSubject());
		not.put("icon", data.getIcon());
		not.put("link", data.getLink());
		not.put("creation", ElasticsearchHelper.DATETIME_NO_MILLIS_FORMATER.format( data.getCreateTimestamp()));
		not.put("release", ElasticsearchHelper.DATETIME_NO_MILLIS_FORMATER.format(data.getReleaseTimestamp()));
		JSONObject to = new JSONObject();
		to.put("User", ElasticsearchHelper.vectorToJSONArray( data.getToUser()));
		to.put("Group", ElasticsearchHelper.vectorToJSONArray( data.getToGroup()));
		not.put("to", to);
		
		return ElasticsearchHelper.edit(view,"system","notification",not.toJSONString(), data);
	}

	@Override
	public Notification get(String id) 
	{
		GetResponse response = this.view.getESClient().prepareGet("system", "notification", id).setFields(fieldList).execute().actionGet();
		if (response.isExists()) {
			return responseToNotification(response.getId(), response.getVersion(), response.getFields());
		} else
			return null;
	}

	@Override
	public Vector<Notification> getAll() 
	{
		Vector<Notification> notification = new Vector<Notification>();
		
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "notification", fieldList)) 
		{
			if(hit.getFields() != null){
				Notification not = this.responseToNotification(hit.getId(), hit.getVersion(), hit.getFields());
				if(not != null)
					notification.add(not);
			}
		}
		return notification;
	}

	@Override
	public boolean remove(String id) {
		return ElasticsearchHelper.remove(view, "system", "notification", id);
	}
	
	public Notification responseToNotification(id, version, fields)
	{
			if(fields == null)
				return null;
				
			Notification  notification = new Notification(id, version);
			
			for (String key : fields.keySet()) 
			{
				def field = fields.get(key);
				
				switch (key) 
				{
					case "subject":
						notification.setSubject(field.getValue() == null ? "" : field.getValue());
						break;
					case "icon":
						notification.setIcon(field.getValue() == null ? "" : field.getValue());
						break;
					case "link":
						notification.setLink(field.getValue() == null ? "" : field.getValue());
						break;
					case "creation":
						notification.setCreateTimestamp(field.getValue() == null ? new Date() :  ElasticsearchHelper.DATETIME_NO_MILLIS_FORMATER.parse(field.getValue()));
						break;
					case "release":
						notification.setReleaseTimestamp(field.getValue() == null ? new Date() : ElasticsearchHelper.DATETIME_NO_MILLIS_FORMATER.parse(field.getValue()));
						break;
					case "to.User":
						Vector<String> users = new Vector<String>();
						if (field.getValues() != null) 
						{
							Vector<Object> values = field.getValues();
							for(Object o : values) {
								users.add(o.toString());
							}
						}
						notification.setToUser(users);
						break;
					case "to.Group": //Vector
						Vector<String> groups = new Vector<String>();
						if (field.getValues() != null) 
						{
							Vector<Object> values = field.getValues();
							for (Object o : values) 
							{
								groups.add(o.toString());
							}
						}
						notification.setToGroup(groups);
						break;
				}
			}
			return notification;
	} 

}				
