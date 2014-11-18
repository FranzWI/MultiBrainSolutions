package de.mbs.abstracts.db.objects;

import java.util.Date;
import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class Notification extends DatabaseObject {

	private String subject, icon, link;

	private Date createTimestamp = new Date(), releaseTimestamp = new Date();

	private Vector<String> toUser = new Vector<String>(),
			toGroup = new Vector<String>();

	public Notification(String id) {
		super(id);
	}

	public Notification(String id, int version) {
		super(id, version);
	}

	public Notification(String id, long timestamp) {
		super(id, timestamp);
	}

	public String getSubject() {
		return subject;
	}

	public String getIcon() {
		return icon;
	}

	public String getLink() {
		return link;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public Date getReleaseTimestamp() {
		return releaseTimestamp;
	}

	public Vector<String> getToUser() {
		return toUser;
	}

	public Vector<String> getToGroup() {
		return toGroup;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public void setReleaseTimestamp(Date releaseTimestamp) {
		this.releaseTimestamp = releaseTimestamp;
	}

	public void setToUser(Vector<String> toUser) {
		this.toUser = toUser;
	}

	public void setToGroup(Vector<String> toGroup) {
		this.toGroup = toGroup;
	}

	public void addUser(String userid) {
		this.toUser.add(userid);
	}

	public void addGroup(String groupid) {
		this.toGroup.add(groupid);
	}

	public boolean removeUser(String userid) {
		return this.toUser.remove(userid);
	}

	public boolean removeGroup(String groupid) {
		return this.toGroup.remove(groupid);
	}

}
