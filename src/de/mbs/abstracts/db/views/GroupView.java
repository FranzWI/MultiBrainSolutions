package de.mbs.abstracts.db.views;

import java.util.Observable;
import java.util.Observer;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;

public abstract class GroupView extends Observable implements AddableView<Group>, EditableView<Group>,
		FindableView<Group>, RemoveableView<Group>, Observer {

	protected String adminGroupId, userGroupId;
	
	public String getAdminGroupId(){
		return this.adminGroupId;
	}
	
	public String getUserGroupId() {
		return this.userGroupId;
	}
	
	public void update(Observable o, Object arg) {}
	
}
