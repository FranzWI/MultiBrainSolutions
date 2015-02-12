package de.mbs.abstracts.db.objects;

import java.util.Map;
import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class User extends DatabaseObject implements Cloneable{

	private static final long serialVersionUID = 6807965873485985071L;

	private String username, firstname, lastname, email, pw, apikey, sessionId;

	private Vector<String> membership = new Vector<String>();
	private Vector<Map<String,String>> portlets = new Vector<Map<String,String>>();

	private boolean active = false;
	
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

	public Vector<Map<String,String>> getPortlets() {
		return portlets;
	}
	
	public void addMembership(String groupId) {
		this.membership.add(groupId);
	}
	
	public void removeMembership(String groupId){
		this.membership.remove(groupId);
	}

	public void setPortlets(Vector<Map<String,String>> portlets) {
		this.portlets = portlets;
	}

	public void addPortlet(Map<String,String> map){
		this.portlets.add(map);
	}
	
	public void removePortlet(String id){
		for(Map<String,String> map : this.portlets){
			if(map.containsValue(id)){
				this.portlets.remove(map);
				return;
			}
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	//TODO: Franz - 19.11.2014 -> get/set Session ID added
	public String getSessionId()
	{
		return sessionId;
	}
	
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
	
}
