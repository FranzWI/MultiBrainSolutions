package de.mbs.abstracts.db.views;

import groovy.xml.MarkupBuilder;

import java.util.Vector;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;
import de.mbs.abstracts.db.views.definition.SearchableView;

public abstract class UserView implements AddableView<User>,
		RemoveableView<User>, EditableView<User>, FindableView<User>,SearchableView {

	/**
	 * 
	 * @param username
	 * @param password
	 * @return null falls keine Gültige Kombination aus Passwort und Nutzernamen
	 *         vorliegt ansonsten die ID des betroffenen Nutzers
	 */
	public abstract String login(String username, String password);
		
}