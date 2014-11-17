package de.mbs.db.java.views;

import groovy.xml.MarkupBuilder;

import java.util.Date;
import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Message;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.db.java.JavaView;
import de.mbs.db.java.utils.JavaHelper;

public class JavaMessageview extends MessageView {

	private Vector<Message> messages = new Vector<Message>();

	private JavaView view;

	public JavaMessageview(JavaView view) {
		this.view = view;
		User admin = null;
		for(User u : view.getUserView().getAll()){
			if(u.getUsername().equals("admin")){
				admin = u;
				break;
			}
		}
		if(admin != null){
			Message m = new Message(null);
			m.setFromUser(admin.getId());
			m.addToUser(admin.getId());
			m.setRead(false);
			m.setContent("Test Nachricht");
			m.setTopic("Test");
			m.setSendDate(new Date());
			this.add(m);
		}
	}

	@Override
	public Vector<SearchResult> search(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(Message data) {
		data.setId(UUID.randomUUID().toString());
		this.messages.add(data);
		return data.getId();
	}

	@Override
	public Message edit(Message data) {
		return JavaHelper.edit(data, this.messages);
	}

	@Override
	public Message get(String id) {
		return JavaHelper.get(id, this.messages);
	}

	@Override
	public Vector<Message> getAll() {
		return this.messages;
	}

	@Override
	public boolean remove(String id) {
		return JavaHelper.remove(id, this.messages);
	}

	@Override
	public Vector<Message> getMessagesForUser(String id) {
		Vector<Message> data = new Vector<Message>();
		User u = this.view.getUserView().get(id);
		if (u == null)
			return null;
		for (Message m : this.messages) {
			if (m.getToUser().contains(id)) {
				data.add(m);
			} else {
				for (String ugroupId : u.getMembership()) {
					if (m.getToGroup().contains(ugroupId)) {
						data.add(m);
						break;
					}
				}
			}
		}
		return data;
	}

	@Override
	public Vector<Message> getUnreadMessagesForUser(String id) {
		Vector<Message> data = new Vector<Message>();
		User u = this.view.getUserView().get(id);
		if (u == null)
			return null;
		for (Message m : this.messages) {
			if (!m.isRead()) {
				if (m.getToUser().contains(id)) {
					data.add(m);
				} else {
					for (String ugroupId : u.getMembership()) {
						if (m.getToGroup().contains(ugroupId)) {
							data.add(m);
							break;
						}
					}
				}
			}
		}
		return data;
	}

}
