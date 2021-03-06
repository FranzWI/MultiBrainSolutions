package de.mbs.abstracts.db.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class Message extends DatabaseObject implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -63279819451435228L;
	private Date sendDate;
	private Vector<String> toUser = new Vector<String>();
	private Vector<String> toGroup = new Vector<String>();

	private String fromUser;

	private String topic;
	private String content;

	private String previousMessgage;

	private boolean read = false;

	public Message(String id) {
		super(id);
	}

	public Message(String id, long version) {
		super(id, version);
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Vector<String> getToUser() {
		return toUser;
	}

	public void setToUser(Vector<String> toUser) {
		this.toUser = toUser;
	}

	public Vector<String> getToGroup() {
		return toGroup;
	}

	public void setToGroup(Vector<String> toGroup) {
		this.toGroup = toGroup;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPreviousMessgage() {
		return previousMessgage;
	}

	public void setPreviousMessgage(String previousMessgage) {
		this.previousMessgage = previousMessgage;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public void addToUser(String id) {
		this.toUser.add(id);
	}

	public void addToGroup(String id) {
		this.toGroup.add(id);
	}

}
