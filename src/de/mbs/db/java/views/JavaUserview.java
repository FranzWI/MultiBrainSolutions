package de.mbs.db.java.views;

import groovy.xml.MarkupBuilder;

import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.crypt.Crypt;
import de.mbs.db.java.JavaView;
import de.mbs.db.java.utils.JavaHelper;

public class JavaUserview extends UserView {

	private Vector<User> users = new Vector<User>();

	private JavaView view;

	public JavaUserview(JavaView view) {
		// Admin anlegen
		this.view = view;
		User admin = new User(null);
		admin.setFirstname("Admini");
		admin.setLastname("Strator");
		admin.setUsername("admin");
		admin.setEmail("ich@michaelkuerbis.de");
		admin.setPw("admin");
		admin.setApikey(UUID.randomUUID().toString());
		for (Group group : view.getGroupView().getAll()) {
			admin.addMembership(group.getId());
		}
		this.add(admin);

		// Nutzer anlegen
		User user = new User(null);
		user.setFirstname("Default");
		user.setLastname("'User");
		user.setUsername("user");
		user.setEmail("ich@michaelkuerbis.de");
		user.setPw("user");
		user.setApikey(UUID.randomUUID().toString());
		for (Group group : view.getGroupView().getAll()) {
			if (group.getName().equals("Nutzer"))
				user.addMembership(group.getId());
		}
		this.add(user);
	}

	@Override
	public String add(User data) {
		// ID des neuen Nutzers setzen
		data.setId(UUID.randomUUID().toString());
		// Passwort verschlüsseln
		data.setPw(Crypt.getCryptedPassword(data.getPw()));
		this.users.add(data);
		return data.getId();
	}

	@Override
	public boolean remove(String id) {
		return JavaHelper.remove(id, this.users);
	}

	@Override
	public User edit(User data) {
		return JavaHelper.edit(data, this.users);
	}

	@Override
	public User get(String id) {
		return JavaHelper.get(id, this.users);
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
		for (User u : this.users) {
			if (u.isActive() && u.getUsername().equals(username)
					&& Crypt.getCryptedPassword(password).equals(u.getPw())) {
				return u.getId();
			}
		}
		return null;
	}

	@Override
	public Vector<User> getAll() {
		return this.users;
	}

	@Override
	public User getUserByApikey(String apikey) {
		for (User u : this.users) {
			if (u.getApikey().equals(apikey))
				return u;
		}
		return null;
	}

}
