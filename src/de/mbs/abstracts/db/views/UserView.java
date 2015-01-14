package de.mbs.abstracts.db.views;

import java.util.Observable;
import java.util.Observer;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.definition.AddableView;
import de.mbs.abstracts.db.views.definition.EditableView;
import de.mbs.abstracts.db.views.definition.FindableView;
import de.mbs.abstracts.db.views.definition.RemoveableView;
import de.mbs.abstracts.db.views.definition.SearchableView;

public abstract class UserView extends Observable implements AddableView<User>,
		RemoveableView<User>, EditableView<User>, FindableView<User>,SearchableView, Observer {

	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return null falls keine Gültige Kombination aus Passwort und Nutzernamen
	 *         vorliegt, oder der nutzer nicht aktiv ist, ansonsten die ID des betroffenen Nutzers
	 */
	public abstract String login(String username, String password);

	/**
	 * 
	 * @param apikey - nach dem gesucht werden soll
	 * @return null falls der Apikey keinem Nutzer zugewiesen werden kann, den User falls doch
	 */
	public abstract User getUserByApikey(String apikey);
	public abstract User getUserByUserName(String userName);
	
	public void update(Observable o, Object arg) {}
	
	public String getTabName(){
		return "Nutzer";
	}
}
