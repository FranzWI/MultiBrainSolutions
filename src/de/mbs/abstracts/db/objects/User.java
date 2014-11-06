package de.mbs.abstracts.db.objects;

import java.util.Vector;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class User extends DatabaseObject {

	private String username, firstname, lastname, email, pw, apikey;
	
	private Vector<String> membership;
	private Vector<String> portlets;
	
	public User(String id) {
		super(id);
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

	public void setPortlets(Vector<String> portlets) {
		this.portlets = portlets;
	}

}