package de.mbs.db.java.views;

import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.objects.UserPortlet;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.db.java.JavaView;
import de.mbs.db.java.utils.JavaHelper;

public class JavaPortletview extends PortletView {

	private Vector<Portlet> portlets = new Vector<Portlet>();

	private JavaView view;

	public JavaPortletview(JavaView view) {
		this.view = view;
		Portlet p = new Portlet(null);
		p.setName("Festplattenauslastung");
		p.setPath("admin/hdd.groovy");
		p.setDescription("Test Portlet No. 1");
		p.setSizeXS(12);
		p.setSizeSM(6);
		p.setSizeMD(3);
		p.setSizeLG(2);
		p.addUseableGroup(this.view.getGroupView().getAdminGroupId());
		this.add(p);
		
		Portlet p2 = new Portlet(null);
		p2.setName("RAM-Auslastung");
		p2.setPath("admin/ram.groovy");
		p2.setDescription("Test Portlet No. 2");
		p2.addUseableGroup(this.view.getGroupView().getAdminGroupId());
		p2.setSizeXS(12);
		p2.setSizeSM(6);
		p2.setSizeMD(3);
		p2.setSizeLG(2);
		this.add(p2);
		
		Portlet p4 = new Portlet(null);
		p4.setName("Angemeldete Nutzer");
		p4.setPath("admin/loginuser.groovy");
		p4.setDescription("Test Portlet No. 2");
		p4.addUseableGroup(this.view.getGroupView().getAdminGroupId());
		p4.setSizeXS(12);
		p4.setSizeSM(6);
		p4.setSizeMD(3);
		p4.setSizeLG(2);
		this.add(p4);
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
			for (Portlet portlet : this.getAll()) {
				Vector<String> portletGroups = portlet.getUsedByGroups();
				for (String groupId : portletGroups) {
					if (userGroups.contains(groupId)) {
						boolean addable = true;
						if(!portlet.isMultiple()){
							for(UserPortlet map: view.getUserPortletView().byOwner(user.getId())){
								if(map.getPortletId().equals((portlet.getId()))){
									addable = false;
								}
							}
						}
						if (addable) {
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
