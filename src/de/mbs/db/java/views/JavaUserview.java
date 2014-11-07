package de.mbs.db.java.views;

import groovy.xml.MarkupBuilder;

import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.crypt.Crypt;

public class JavaUserview extends UserView {

	private Vector<User> users = new Vector<User>();
	
	public JavaUserview(){
		// Admin anlegen
		User admin = new User(null);
		admin.setFirstname("Admin");
		admin.setLastname("Strator");
		admin.setUsername("admin");
		admin.setEmail("ich@michaelkuerbis.de");
		admin.setPw(Crypt.getCryptedPassword("admin"));
		admin.setApikey(UUID.randomUUID().toString());
		this.add(admin);
	}
	
	@Override
	public String add(User data) {
		data.setId(UUID.randomUUID().toString());
		this.users.add(data);
		return data.getId();
	}

	@Override
	public boolean remove(User data) {
		for(User u : this.users){
			if(u.getId().equals(data.getId())){
				this.users.remove(u);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Vector<User> data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User edit(User data) {
		for(int i=0; i< this.users.size();i++){
			User u = this.users.get(i);
			if(u.getId().equals(data.getId())){
				this.users.set(i, data);
				return data;
			}
		}
		return null;
	}

	@Override
	public User get(String id) {
		for(User u: this.users){
			if(u.getId().equals(id)){
				return u;
			}
		}
		return null;
	}

	@Override
	public String searchId() {
		return "User";
	}

	@Override
	public Vector<MarkupBuilder> search(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String username, String password) {
		for(User u:this.users){
			if(u.getUsername().equals(username) && Crypt.getCryptedPassword(password).equals(u.getPw())){
				return u.getId();
			}
		}
		return null;
	}

}
