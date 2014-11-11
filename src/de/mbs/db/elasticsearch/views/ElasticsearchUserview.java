package de.mbs.db.elasticsearch.views;

import groovy.xml.MarkupBuilder;

import java.util.Vector;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.UserView;

public class ElasticsearchUserview extends UserView {

	@Override
	public String add(User data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User edit(User data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<MarkupBuilder> search(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
