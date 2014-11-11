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
		Portlet p = new Portlet(null);
		p.setName("Portlet 1");
		p.setPath("portlet1.groovy");
		p.setDescription("Test Portlet No. 1");
		p.setSizeXS(6);
		p.setSizeSM(6);
		p.setSizeMD(3);
		p.setSizeLG(2);

		p.addUseableGroup(this.view.getGroupView().getUserGroupId());
		this.add(p);
		Portlet p2 = new Portlet(null);
		p2.setName("Portlet 2");
		p2.setPath("portlet2.groovy");
		p2.setDescription("Test Portlet No. 2");
		p2.addUseableGroup(this.view.getGroupView().getUserGroupId());
		p2.setSizeXS(6);
		p2.setSizeSM(6);
		p2.setSizeMD(3);
		p2.setSizeLG(2);
		this.add(p2);
		Portlet p3 = new Portlet(null);
		p3.setName("Portlet 3");
		p3.setPath("portlet3.groovy");
		p3.setDescription("Test Portlet No. 3");
		p3.setSizeXS(12);
		p3.setSizeSM(12);
		p3.setSizeMD(6);
		p3.setSizeLG(8);
		p3.addUseableGroup(this.view.getGroupView().getUserGroupId());
		this.add(p3);
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
						if (!user.getPortlets().contains(portlet.getId())) {
							result.add(portlet);
							break;
						}
					}
				}
			}
			return result;
		}
		return null;
	}

	@Override
	public Vector<Portlet> getAll() {
		return this.portlets;
	}

}
