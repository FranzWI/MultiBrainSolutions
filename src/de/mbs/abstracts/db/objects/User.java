package de.mbs.abstracts.db.objects;

import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class User extends DatabaseObject {

	private String username, firstname, lastname, email, pw, apikey, sessionId;

	private Vector<String> membership = new Vector<String>();
	private Vector<String> portlets = new Vector<String>();

	private boolean active = true;
	
	public User(String id) {
		super(id);
	}
	
	public User(String id, long version){
		super(id, version);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public Vector<String> getMembership() {
		return membership;
	}

	public void setMembership(Vector<String> membership) {
		this.membership = membership;
	}

	public Vector<String> getPortlets() {
		return portlets;
	}
	
	public void addMembership(String groupId) {
		this.membership.add(groupId);
	}
	
	public void removeMembership(String groupId){
		this.membership.remove(groupId);
	}

	public void setPortlets(Vector<String> portlets) {
		this.portlets = portlets;
	}

	public void addPortlet(String id){
		this.portlets.add(id);
	}
	
	public void removePortlet(String id){
		this.portlets.remove(id);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	//TODO: Franz - 19.11.2014 -> get/set Session ID added
	public void getSessionId()
	{
		return sessionId;
	}
	
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
}
