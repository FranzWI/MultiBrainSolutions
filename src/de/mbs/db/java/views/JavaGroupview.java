package de.mbs.db.java.views;

import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.views.GroupView;
import de.mbs.db.java.utils.JavaHelper;

public class JavaGroupview extends GroupView {

	private Vector<Group> groups = new Vector<Group>();

	public JavaGroupview() {
		Group admins = new Group(null);
		admins.setName("Admin");
		admins.setDescription("Die System betreuer");
		this.adminGroupId = this.add(admins);
		
		Group user = new Group(null);
		user.setName("Nutzer");
		user.setDescription("Nutzer des Systems");
		this.userGroupId = this.add(user);
	}

	@Override
	public String add(Group data) {
		data.setId(UUID.randomUUID().toString());
		this.groups.add(data);
		return data.getId();
	}

	@Override
	public Group edit(Group data) {
		return JavaHelper.edit(data, this.groups);
	}

	@Override
	public Group get(String id) {
		return JavaHelper.get(id, this.groups);
	}

	@Override
	public boolean remove(String id) {
		return JavaHelper.remove(id, this.groups);
	}

	@Override
	public Vector<Group> getAll() {
		return this.groups;
	}

}
