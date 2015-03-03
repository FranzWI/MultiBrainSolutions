package de.mbs.db.elasticsearch.views;

import groovy.xml.MarkupBuilder;

import java.util.Vector;

import javax.validation.metadata.ReturnValueDescriptor;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.json.JSONObject;

import de.mbs.abstracts.db.objects.Message;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.Pair;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.db.elasticsearch.ElasticsearchView;

public class ElasticsearchMessageview extends MessageView {
	
	//Not Implemented yet field file: "file",
	private String[] fieldList = ["subject", "timestamp","from", "toUser", "toGroup", "content", "prevMessage", "read"];
	
	private ElasticsearchView view; 
	
	public ElasticsearchMessageview(ElasticsearchView view) 
	{
		this.view = view;
	}
	
	@Override
	public Vector<Pair<SearchResult, String>> search(String search, User u) 
	{
		//not implemented yet
		return null;
	}
	
	@Override
	public Vector<Message> getMessagesForUser(String id) 
	{
		Vector<Message> AllMyMessages = new Vector<Message>();
		for(Message myMess : this.getAll())
		{
			if(myMess.getId() == id)
				AllMyMessages.add(myMess);
		}
		if(AllMyMessages != null)
			return AllMyMessages;
		else	
			return null;
	}

	@Override
	public Vector<Message> getUnreadMessagesForUser(String id) 
	{
		Vector<Message> AllMyUnreadedMessages = new Vector<Message>();
		for(Message myMess : this.getMessagesForUser(id))
		{
			if(myMess.isRead() != true)
				AllMyUnreadedMessages.add(myMess);
		}
		if(AllMyUnreadedMessages != null)
			return AllMyUnreadedMessages;
		else
			return null;
	}
	
	@Override
	public String add(Message data) 
	{
		JSONObject mess = new JSONObject();
		
		mess.put("subject", data.getTopic());
		mess.put("timestamp", data.getVersion());
		mess.put("from", data.getFromUser());
		mess.put("toUser", data.getToUser());
		mess.put("toGroup", data.getToGroup());
		mess.put("content", data.getContent());
		//Not Implemented yet:
		//mess.put("file", data.getFile());
		mess.put("prevMessage", data.getPreviousMessgage());
		mess.put("read", data.isRead());
		
		return ElasticsearchHelper.add(view, "system", "message", mess.toJSONString());
		
	}

	@Override
	public Message edit(Message data) 
	{
		JSONObject mess = new JSONObject();

		mess.put("subject", data.getTopic());
		mess.put("timestamp", data.getVersion());
		mess.put("from", data.getFromUser());
		mess.put("toUser", data.getToUser());
		mess.put("toGroup", data.getToGroup());
		mess.put("content", data.getContent());
		//Not Implemented yet:
		//mess.put("file", data.getFile());
		mess.put("prevMessage", data.getPreviousMessgage());
		mess.put("read", data.isRead());

		return ElasticsearchHelper.edit(view,"system","message",mess.toJSONString(), data);
	}

	@Override
	public Message get(String id) 
	{
		GetResponse response = this.view.getESClient().prepareGet("system", "message", id).setFields(fieldList).execute().actionGet();
		if (response.isExists()) {
			return responseToMessage(response.getId(), response.getVersion(), response.getFields());
		} else
			return null;
	}

	@Override
	public Vector<Message> getAll() 
	{
		Vector<Message> messages = new Vector<Message>();
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "message", fieldList)) {
			if(hit.getFields() != null){
				Message mess = this.responseToMessage(hit.getId(), hit.getVersion(), hit.getFields());
				if(mess != null)
					messages.add(mess);
			}
		}
		return messages;
	}

	@Override
	public boolean remove(String id) {
		return ElasticsearchHelper.remove(view, "system", "messages", id);
	}

	public Message responseToMessage(id, version, fields) 
	{
		Message message = new Message(id, version);
		
		if(fields == null)
			return null;
			
		for(String key : fields.keySet())
		{
			def field = fields.get(key);
			switch(key)
			{
				case "subject":
					message.setSubject(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "timestamp":
					message.setVersion(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "from":
					message.setFrom(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "toUser": 
					Vector<String> user = new Vector<String>();
					if (field.getValues() != null)
					{
						Vector<Object> values = field.getValues();
						for (Object o : values)
						{
							user.add(o.toString());
						}
					}
					message.setToUser(user);
					break;
				case "toGroup": 					
					Vector<String> groups = new Vector<String>();
					if (field.getValues() != null)
					{
						Vector<Object> values = field.getValues();
						for (Object o : values)
						{
							groups.add(o.toString());
						}
					}
					message.setToGroup(groups);
					break;
				case "content":
					message.setToContent(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "prevMessage":
					message.setPrevMessage(field.getValue() == null ? "" : field.getValue().toString());
					break;
				case "read":
					message.setRead(field.getValue() == null ? false : (field.getValue()));
					break;
			}
		}
		
		if(message==null)
			return null;
		return message;
		
	}


}
