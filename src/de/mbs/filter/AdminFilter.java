package de.mbs.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;

import de.mbs.handler.ServiceHandler;

@Provider
@Admin
public class AdminFilter extends PrivilegesFilter {

	@Override
	public void check(ContainerRequestContext arg0,
			de.mbs.abstracts.db.objects.User u) {
		String groupId = ServiceHandler.getDatabaseView().getGroupView()
				.getAdminGroupId();
		if (!u.getMembership().contains(groupId)) {
			this.deniePrivAccess(arg0);
		}
	}

}
