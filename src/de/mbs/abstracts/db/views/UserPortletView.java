package de.mbs.abstracts.db.views;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import de.mbs.abstracts.db.objects.UserPortlet;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;

public abstract class UserPortletView extends Observable implements
		AddableView<UserPortlet>, EditableView<UserPortlet>,
		RemoveableView<UserPortlet>, FindableView<UserPortlet>, Observer {

	/**
	 * liefert alle Portlets eines Nutzers sortiert nach order
	 * 
	 * @param userid
	 * @return
	 */
	public abstract Vector<UserPortlet> byOwner(String userid);

	/**
	 * entfernt alle Portlets des nutzers, und fügt in der übergeben reihnfolge
	 * wieder hinzu
	 * 
	 * @param blub
	 * @param ownerID
	 * @return
	 */
	public boolean setPortlets(Vector<UserPortlet> blub, String ownerID) {
		boolean error = false;
		for (UserPortlet p : this.byOwner(ownerID)) {
			if (!this.remove(p.getId())) {
				error = true;
			}

		}
		if (error)
			return false;
		for (UserPortlet p : blub) {
			if (this.add(p) == null) {
				error = true;
			}
		}
		return !error;
	}

}
