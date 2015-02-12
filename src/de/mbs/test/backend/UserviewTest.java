package de.mbs.test.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;
import java.util.Vector;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.Pair;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.test.TestExecuter;

/**
 * 
 * @author MKuerbis
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserviewTest {
	private String testUserNameAlt;
	private String testUserNameNeu;
	private String testUserId;

	public UserviewTest() {
		testUserNameAlt = "alterName";
		testUserNameNeu = "neuerName";

	}

	@Test
	public void test1IsUserViewImplemented() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Userview nicht implementiert", userView);
	}

	@Test
	public void test2AddUser() {
		UserView userView = TestExecuter.getView().getUserView();
		// Daten des neuen Nutzers anlegen
		User testUser = new User(null);
		testUser.setFirstname("De");
		testUser.setLastname("Nizzle");
		testUser.setUsername(this.testUserNameAlt);
		testUser.setPw("passwort");
		testUser.setActive(true);

		// ..
		// die anderen Felder füllen
		// ..
		// Nutzer in Datenbank schreiben lassen
		testUserId = userView.add(testUser);
		System.out.println("Debug testUserId " + testUserId);
		// wenn die ID nicht null ist
		assertNotNull("Nutzer wurde nicht angelegt", testUserId);

		// okey scheinbar wurde der Nutzer angelegt
		// prüfen ob die Datengleich sind
		User newUser = userView.get(testUserId);

		assertNotNull("Neuer Nutzer wurde nicht gefunden!", newUser);

		assertEquals("Vorname sind nicht gleich", testUser.getFirstname(),
				newUser.getFirstname());
		assertEquals("Nachname sind nicht gleich", testUser.getLastname(),
				newUser.getLastname());
		assertEquals("Username nicht gleich", testUser.getUsername(),
				newUser.getUsername());
		// newUser.getPw();
		assertFalse("Passwort wurde nicht verschlüsselt", testUser.getPw()
				.equals(newUser.getPw()));
		assertNotNull("Nutzer konnte nicht am ApiKey abgerufen werden!",
				userView.getUserByApikey(newUser.getApikey()));
		assertNull(
				"Ungültigen APIKey übergeben und dennoch einen Nutzer erhalten!!",
				userView.getUserByApikey(UUID.randomUUID().toString()));
		assertTrue("testUser nicht aktiv", testUser.isActive());

	}

	@Test
	public void test2get() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Kein User mit der ID " + testUserId + "wurde gefunden",
				userView.get(this.testUserId));
	}

	@Test
	public void test3EditUserName() {
		// ähnlich addUser nur das wir uns einen Nutzerauswählen
		// und diesen dann einfach editiren --> speichern --> prüfen
		UserView userView = TestExecuter.getView().getUserView();
		User testUser = userView.get(this.testUserId);
		testUser.setUsername(testUserNameNeu);
		testUser.setFirstname("anderer");
		testUser.setLastname("Name");
		User editedUser = userView.edit(testUser);
		assertNotNull("User konnte nicht geändert werden", editedUser);
		assertEquals("Username nicht identisch", testUser.getUsername(),
				editedUser.getUsername());
		// Ausgabe aller Benutzernamen
		// for(User u:userView.getAll()){System.out.println(u.getUsername());}
	}

	@Test
	public void test4Login() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Login als Admin funktioniert nicht!",
				userView.login("admin", "admin"));
		assertNotNull("Login als User funktioniert nicht!",
				userView.login("user", "user"));
		User testUser = userView.get(this.testUserId);
		assertTrue("TestUser ist nicht aktiviert", testUser.isActive());
		if (testUser.isActive()) {
			assertNotNull("Login als TestUser funktioniert nicht!",
					userView.login(testUserNameNeu, "passwort"));
		}
	}

	@Test
	public void test4Search() {
		String suchString = "mini";
		UserView userView = TestExecuter.getView().getUserView();
		Vector<Pair<SearchResult, String>> vec = userView.search(suchString,
				userView.get(testUserId));
		assertNotNull("Vektor ist Null", vec);
		assertTrue("Vektor ist leer", vec.size() > 0);
	}

	@Test
	public void test9Remove() {
		UserView userView = TestExecuter.getView().getUserView();
		assertTrue("User loeschen lief schief", userView.remove(testUserId));

	}
}
