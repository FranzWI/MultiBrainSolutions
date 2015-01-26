package de.mbs.test.backend;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.test.TestExecuter;

/**
 * 
 * @author MKuerbis
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserviewTest 
{
	private String testUserName;
	
	public UserviewTest() 
	{
		testUserName = "user";
	}
	
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
		testUser.setUsername(this.testUserName);
		testUser.setPw("Snickers123");
		
		// ..
		// die anderen Felder füllen
		// ..
		// Nutzer in Datenbank schreiben lassen 
		String id = userView.add(testUser);
		// wenn die ID nicht null ist
		assertNotNull("Nutzer wurde nicht angelegt", id);
		System.out.println("id "+id);

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
		assertFalse("Passwort wurde nicht verschlüsselt",testUser.getPw().equals(newUser.getPw()));
		assertNotNull("Nutzer konnte nicht am ApiKey abgerufen werden!", userView.getUserByApikey(newUser.getApikey()));
		assertNull("Ungültigen APIKey übergeben und dennoch einen Nutzer erhalten!!", userView.getUserByApikey(UUID.randomUUID().toString()));
	}

	@Test
	public void testEditUser() {
		// ähnlich addUser nur das wir uns einen Nutzerauswählen
		// und diesen dann einfach editiren --> speichern --> prüfen
		UserView userView = TestExecuter.getView().getUserView();
		User testUser = userView.getUserByUserName(this.testUserName);
		assertNotNull("User mit Namen "+this.testUserName+" nicht gefunden",testUser);
		//testUser.setUsername("TestUser");
		//User editedUser = userView.edit(testUser);
		//assertNotNull("User konnte nicht geändert werden",editedUser);
		//assertEquals("Username nicht identisch",testUser.getUsername(),editedUser.getUsername());

	}

	/*
	@Test
	public void testLogin() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Login als Admin funktioniert nicht!", userView.login("admin", "admin"));
		assertNotNull("Login als User funktioniert nicht!", userView.login("user", "user"));
	}*/
}
