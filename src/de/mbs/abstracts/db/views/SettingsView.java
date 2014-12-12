package de.mbs.abstracts.db.views;

import java.util.Observable;
import java.util.Observer;

import de.mbs.abstracts.db.objects.Settings;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;

public abstract class SettingsView extends Observable implements EditableView<Settings>,
		FindableView<Settings>,Observer {

	public void update(Observable o, Object arg) {}
	
}
