package de.mbs.db.java.views;

import java.util.Observable;
import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.UserPortlet;
import de.mbs.abstracts.db.views.UserPortletView;
import de.mbs.db.java.utils.JavaHelper;

public class JavaUserPortletview extends UserPortletView {

	private Vector<UserPortlet> data = new Vector<UserPortlet>();

	@Override
	public String add(UserPortlet data) {
		data.setId(UUID.randomUUID().toString());
		this.data.add(data);
		return data.getId();
	}

	@Override
	public UserPortlet edit(UserPortlet data) {
		return JavaHelper.edit(data, this.data);
	}

	@Override
	public boolean remove(String id) {
		return JavaHelper.remove(id, data);
	}

	@Override
	public UserPortlet get(String id) {
		return JavaHelper.get(id, data);
	}

	@Override
	public Vector<UserPortlet> getAll() {
		return data;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector<UserPortlet> byOwner(String userid) {
		Vector<UserPortlet> portlets = new Vector<UserPortlet>();
		for (UserPortlet portlet : data) {
			if (portlet.getOwnerId().equals(userid)) {
				portlets.add(portlet);
			}
		}
		return portlets;
	}

}
