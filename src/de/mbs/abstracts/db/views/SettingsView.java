package de.mbs.abstracts.db.views;

import de.mbs.abstracts.db.objects.Settings;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;

public abstract class SettingsView implements EditableView<Settings>,
		FindableView<Settings> {

}
