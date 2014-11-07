package de.mbs.db.java.views;

import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.db.java.JavaView;
import de.mbs.db.java.utils.JavaHelper;

public class JavaPortletview extends PortletView {

	private Vector<Portlet> portlets = new Vector<Portlet>();

	private JavaView view;

	public JavaPortletview(JavaView view) {
		this.view = view;
	}

	@Override
	public String add(Portlet data) {
		data.setId(UUID.randomUUID().toString());
		this.portlets.add(data);
		return data.getId();
	}

	@Override
	public Portlet edit(Portlet data) {
		return JavaHelper.edit(data, this.portlets);
	}

	@Override
	public Portlet get(String id) {
		return JavaHelper.get(id, this.portlets);
	}

	@Override
	public boolean remove(String id) {
		return JavaHelper.remove(id, this.portlets);
	}

	@Override
	public Vector<Portlet> getPossiblePortletsForUser(String id) {
		User user = this.view.getUserView().get(id);
		if (user != null) {
			Vector<String> userGroups = user.getMembership();
			Vector<Portlet> result = new Vector<Portlet>();
			for (Portlet portlet : this.portlets) {
				Vector<String> portletGroups = portlet.getUsedByGroups();
				for (String groupId : portletGroups) {
					if (userGroups.contains(groupId)) {
						result.add(portlet);
						break;
					}
				}
			}
			return result;
		}
		return null;
	}

}
