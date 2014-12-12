package de.mbs.abstracts.db.views;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;

public abstract class PortletView extends Observable implements AddableView<Portlet>,
		EditableView<Portlet>, FindableView<Portlet>, RemoveableView<Portlet>, Observer {

	/**
	 * 
	 * @param id
	 *            - ID des Nutzers
	 * @return Liste der Portlets die der Nutzer verwenden darf, falls möglich,
	 *         wenn nicht dann null
	 */
	public abstract Vector<Portlet> getPossiblePortletsForUser(String id);

	public void update(Observable o, Object arg) {}
	
}
