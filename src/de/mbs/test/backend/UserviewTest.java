package de.mbs.test.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
	private static String testUserId;

	public UserviewTest() {
		testUserNameAlt = "alterName";
		testUserNameNeu = "neuerName";

	}

	/**
	 * Test method for {@link de.mbs.abstracts.db.DatabaseView#getUserView()}.
	 */
	@Test
	public void test0IsUserViewImplemented() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Userview nicht implementiert", userView);
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaUserview#getAll()}.
	 */
	@Test
	public final void test1GetAll() {
		UserView userView = TestExecuter.getView().getUserView();
		Vector<User> vec = userView.getAll();
		assertNotNull("", userView.getAll());
		assertTrue("UserVektor ist leer", vec.size() > 0);
	}
	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#add(de.mbs.abstracts.db.objects.User)}
	 * .
	 */
	@Test
	public final void test2Add() {
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
		this.testUserId = userView.add(testUser);
		// wenn die ID nicht null ist
		assertNotNull("Nutzer wurde nicht angelegt", this.testUserId);

		// okey scheinbar wurde der Nutzer angelegt
		// prüfen ob die Datengleich sind
		User newUser = userView.get(this.testUserId);

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
	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#get(java.lang.String)}.
	 */
	@Test
	public final void test2Get() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Kein User mit der ID " + this.testUserId
				+ " wurde gefunden", userView.get(this.testUserId));
	}
	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#getUserByUserName(java.lang.String)}
	 * .
	 */
	@Test
	public final void test2GetUserByUserName() {
		try{
		UserView userView = TestExecuter.getView().getUserView();
		User admin =userView.getUserByUserName("admin");
		assertNotNull("Admin ID ist Null",admin.getId());
		User testUser = userView.getUserByUserName(testUserNameAlt);
		assertNotNull("testUser ID ist Null",testUser.getId());
		assertEquals("ID's des TestUser stimmen nicht ueberein",testUser.getId(),testUserId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#edit(de.mbs.abstracts.db.objects.User)}
	 * .
	 */
	@Test
	public final void test3Edit() {
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

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#login(java.lang.String, java.lang.String)}
	 * .
	 */
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
	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#search(java.lang.String, de.mbs.abstracts.db.objects.User)}
	 * .
	 */
	@Test
	public final void test4Search() {
		String suchString = "mini";
		UserView userView = TestExecuter.getView().getUserView();
		Vector<Pair<SearchResult, String>> vec = userView.search(suchString,
				userView.getUserByUserName("Admin"));
		assertNotNull("Vektor ist Null", vec);
		assertTrue("Vektor ist leer", vec.size() > 0);
	}
	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#remove(java.lang.String)}.
	 */
	@Test
	public final void test9Remove() {
		UserView userView = TestExecuter.getView().getUserView();
		assertTrue("User loeschen lief schief", userView.remove(testUserId));

	}
/*

	*//**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#getUserByApikey(java.lang.String)}
	 * .
	 *//*
	@Test
	public final void testGetUserByApikey() {
		fail("Not yet implemented"); // TODO
	}

	*//**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaUserview#JavaUserview(de.mbs.db.java.JavaView)}
	 * .
	 *//*
	@Test
	public final void testJavaUserview() {
		fail("Not yet implemented"); // TODO
	}

	*//**
	 * Test method for
	 * {@link de.mbs.abstracts.db.views.UserView#update(java.util.Observable, java.lang.Object)}
	 * .
	 *//*
	@Test
	public final void testUpdate() {
		fail("Not yet implemented"); // TODO
	}

	*//**
	 * Test method for {@link de.mbs.abstracts.db.views.UserView#getTabName()}.
	 *//*
	@Test
	public final void testGetTabName() {
		fail("Not yet implemented"); // TODO
	}
*/
}
