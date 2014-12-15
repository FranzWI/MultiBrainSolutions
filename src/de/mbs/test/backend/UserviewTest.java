package de.mbs.test.backend;

import static org.junit.Assert.*;

import java.util.UUID;

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
		testUser.setFirstname("De");
		testUser.setLastname("Nizzle");
		testUser.setUsername("deNizzle");
		testUser.setPw("Snickers123");
		
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
		assertEquals("Username nicht gleich", testUser.getUsername(),
				newUser.getUsername());
				newUser.getPw();
		System.out.println("übergebenes PW"+testUser.getPw());
		System.out.println("gespeichertes PW"+newUser.getPw());
		assertFalse("Passwort wurde nicht verschlüsselt",testUser.getPw().equals(newUser.getPw()));
		assertNotNull("Nutzer konnte nicht am ApiKey abgerufen werden!", userView.getUserByApikey(newUser.getApikey()));
		assertNull("Ungültigen APIKey übergeben und dennoch einen Nutzer erhalten!!", userView.getUserByApikey(UUID.randomUUID().toString()));
	}
	
	/*@Test
	public void testEditUser() {
		// ähnlich addUser nur das wir uns einen Nutzerauswählen
		// und diesen dann einfach editiren --> speichern --> prüfen
	}

	
	@Test
	public void testLogin() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Login als Admin funktioniert nicht!", userView.login("admin", "admin"));
		assertNotNull("Login als User funktioniert nicht!", userView.login("user", "user"));
	}*/
}
