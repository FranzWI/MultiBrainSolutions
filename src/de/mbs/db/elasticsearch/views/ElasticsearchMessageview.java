package de.mbs.db.elasticsearch.views;

import java.util.Vector;

import de.mbs.abstracts.db.objects.Message;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.MessageView;

public class ElasticsearchMessageview extends MessageView {

	@Override
	public Vector<SearchResult> search(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(Message data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message edit(Message data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Message> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector<Message> getMessagesForUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Message> getUnreadMessagesForUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
