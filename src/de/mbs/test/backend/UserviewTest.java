package de.mbs.test.backend;

import static org.junit.Assert.*;

import org.junit.Test;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.test.TestExecuter;

/**
 * 
 * @author MKuerbis
 *
 */
public class UserviewTest {

	@Test
	public void testIsUserViewImplemented() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Userview nicht implementiert", userView);
	}

	@Test
	public void testAddUser() {
		UserView userView = TestExecuter.getView().getUserView();
		// Daten des neuen Nutzers anlegen
		User testUser = new User(null);
		testUser.setFirstname("Michael");
		testUser.setLastname("Kürbis");
		// ..
		// die anderen Felder füllen
		// ..
		// Nutzer in Datenbank schreiben lassen
		String id = userView.add(testUser);
		// wenn die ID nicht null ist
		assertNotNull("Nutzer wurde nicht angelegt", id);

		// okey scheinbar wurde der Nutzer angelegt
		// prüfen ob die Datengleich sind
		User newUser = userView.get(id);

		assertNotNull("Neuer Nutzer wurde nicht gefunden!", newUser);

		assertEquals("Vorname sind nicht gleich", testUser.getFirstname(),
				newUser.getFirstname());
		assertEquals("Nachname sind nicht gleich", testUser.getLastname(),
				newUser.getLastname());

	}
	
	@Test
	public void testEditUser() {
		// ähnlich addUser nur das wir uns einen Nutzerauswählen
		// und diesen dann einfach editiren --> speichern --> prüfen
	}
	
	@Test
	public void testLogin() {
		
	}
}
