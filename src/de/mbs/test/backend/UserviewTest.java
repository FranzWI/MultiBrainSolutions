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
import de.mbs.db.elasticsearch.views.ElasticsearchUserview;
import de.mbs.test.TestExecuter;

/**
 * 
 * @author MKuerbis
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserviewTest 
{
	private String testUserNameAlt;
	private String testUserNameNeu;
	
	
	public UserviewTest() 
	{
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
		assertFalse("Passwort wurde nicht verschlüsselt",testUser.getPw().equals(newUser.getPw()));
		assertNotNull("Nutzer konnte nicht am ApiKey abgerufen werden!", userView.getUserByApikey(newUser.getApikey()));
		assertNull("Ungültigen APIKey übergeben und dennoch einen Nutzer erhalten!!", userView.getUserByApikey(UUID.randomUUID().toString()));
		assertTrue ("user nicht aktiviert", testUser.isActive());
	}

	@Test
	public void test3EditUserName() {
		// ähnlich addUser nur das wir uns einen Nutzerauswählen
		// und diesen dann einfach editiren --> speichern --> prüfen
		UserView userView = TestExecuter.getView().getUserView();
		User testUser = userView.getUserByUserName(this.testUserNameAlt);
		assertNotNull("User mit Namen "+this.testUserNameAlt+" nicht gefunden",testUser);
			testUser.setUsername(testUserNameNeu);
			testUser.setFirstname("anderer");
			testUser.setLastname("Name");
			User editedUser = userView.edit(testUser);
		
		assertNotNull("User konnte nicht geändert werden",editedUser);
		assertEquals("Username nicht identisch",testUser.getUsername(),editedUser.getUsername());
		//Ausgabe aller Benutzernamen 
		//for(User u:userView.getAll()){System.out.println(u.getUsername());}
		
	}
		
	@Test
	public void test4Login() {
		UserView userView = TestExecuter.getView().getUserView();
		assertNotNull("Login als Admin funktioniert nicht!", userView.login("admin", "admin"));
		assertNotNull("Login als User funktioniert nicht!", userView.login("user", "user"));
		User testUser = userView.getUserByUserName(testUserNameNeu);
		if (!testUser.isActive()){
		assertNull("Login funktioniert, obwohl User nicht aktiviert ist!", userView.login(testUserNameNeu, "passwort"));
		}
	}
	@Test
	public void test5Search(){
			UserView userView = TestExecuter.getView().getUserView();
		User testUser = userView.getUserByUserName("admin");
		String testUserId = testUser.getId();
		System.out.println("testUser ID:" + testUserId);
		String testSearch = "mini";
		assertNotNull("string " + testSearch + "nicht gefunden", userView.search(testSearch, testUser));
	}
}
