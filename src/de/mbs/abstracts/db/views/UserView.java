package de.mbs.abstracts.db.views;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;

public abstract class UserView implements AddableView<User>, RemoveableView<User>,
		EditableView<User>, FindableView<User> {

}
