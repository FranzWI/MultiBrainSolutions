package de.mbs.abstracts.db.views;

import de.mbs.abstracts.db.objects.Group;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;

public abstract class GroupView implements AddableView<Group>, EditableView<Group>,
		FindableView<Group>, RemoveableView<Group> {

}
