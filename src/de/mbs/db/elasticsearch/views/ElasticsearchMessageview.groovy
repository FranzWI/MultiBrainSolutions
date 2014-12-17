package de.mbs.db.elasticsearch.views;

import groovy.xml.MarkupBuilder;

import java.util.Vector;

import javax.validation.metadata.ReturnValueDescriptor;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.search.SearchHit;
import org.json.JSONObject;

import de.mbs.abstracts.db.objects.Message;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.Pair;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.db.elasticsearch.ElasticsearchView;

public class ElasticsearchMessageview extends MessageView {
	
	private String[] fieldList = ["subject", "timestamp","from", "toUser", "toGroup", "content","file","prevMessage", "read"];
	
	private ElasticsearchView view; 
	
	public ElasticsearchMessageview(ElasticsearchView view) 
	{
		this.view = view;
	}
	
	@Override
	public Vector<Pair<SearchResult, String>> search(String search, User u) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//################################DONE############################################
	
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
		mess.put("timestamp", data.getTimestamp());
		mess.put("from", data.getFromUser());
		mess.put("toUser", data.getToUser());
		mess.put("toGroup", data.getToGroup());
		mess.put("content", data.getContent());
		mess.put("file", data.getFile());
		mess.put("prevMessage", data.getPreviousMessgage());
		mess.put("read", data.isRead());
		
		return ElasticsearchHelper.add(view, "system", "messages", mess.toJSONString());
		
	}

	@Override
	public Message edit(Message data) 
	{
		JSONObject mess = new JSONObject();

		mess.put("subject", data.getTopic());
		mess.put("timestamp", data.getTimestamp());
		mess.put("from", data.getFromUser());
		mess.put("toUser", data.getToUser());
		mess.put("toGroup", data.getToGroup());
		mess.put("content", data.getContent());
		mess.put("file", data.getFile());
		mess.put("prevMessage", data.getPreviousMessgage());
		mess.put("read", data.isRead());

		return ElasticsearchHelper.edit(view,"system","messages",mess.toJSONString(), data);
	}

	@Override
	public Message get(String id) 
	{
		GetResponse response = this.view.getESClient().prepareGet("system", "messages", id).setFields(fieldList).execute().actionGet();
		if (response.isExists()) {
			return responseToGroup(response.getId(), response.getVersion(), response.getFields());
		} else
			return null;
	}

	@Override
	public Vector<Message> getAll() 
	{
		Vector<Message> messages = new Vector<Message>();
		for (SearchHit hit : ElasticsearchHelper.getAll(view, "system", "messages", fieldList)) {
			if(hit.getFields() != null){
				Message mess = this.responseToGroup(hit.getId(), hit.getVersion(), hit.getFields());
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



}
