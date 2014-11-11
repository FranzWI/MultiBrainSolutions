package de.mbs.abstracts.db.views;

import java.util.Vector;

import de.mbs.abstracts.db.objects.Message;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;
import de.mbs.abstracts.db.views.definition.SearchableView;

public abstract class MessageView implements SearchableView,
		AddableView<Message>, EditableView<Message>, FindableView<Message>,
		RemoveableView<Message> {

	/**
	 * 
	 * @param id- des Nutzers
	 * @return liefert alle Nachrichten für einen Nutzer<br>
	 *         falls keine Vorliegen wird ein leerer Vector geliefert<br>
	 *         sollte die Nutzer ID ungültig sein wird null zurück gegeben
	 */
	public abstract Vector<Message> getMessagesForUser(String id);

	/**
	 * 
	 * @param id - des Nutzers
	 * @return liefert alle ungelesenen Nachrichten für einen Nutzer<br>
	 *         falls keine Vorliegen wird ein leerer Vector geliefert<br>
	 *         sollte die Nutzer ID ungültig sein wird null zurück gegeben
	 */
	public abstract Vector<Message> getUnreadMessagesForUser(String id);
	
	@Override
	public String searchId() {
		return "Message";
	}

}
