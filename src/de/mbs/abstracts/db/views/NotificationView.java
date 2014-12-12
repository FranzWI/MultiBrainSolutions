package de.mbs.abstracts.db.views;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Notification;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;

public abstract class NotificationView extends Observable implements AddableView<Notification>,
		EditableView<Notification>, FindableView<Notification>, RemoveableView<Notification>, Observer {

	/**
	 * 
	 * @param userId - des Nutzers für den die BEnachrichtigungen geholt werden sollen
	 * @return null - falls die userID ungültig ist
	 * 		   einen leeren Vector falls keine Einträge vorhanden sind
	 */
	public abstract Vector<Notification> getNotificationsForUser(String userId);
	
	public void update(Observable o, Object arg) {}
	
}
