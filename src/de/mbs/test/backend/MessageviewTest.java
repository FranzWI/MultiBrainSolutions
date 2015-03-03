/**
 * 
 */
package de.mbs.test.backend;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.mbs.abstracts.db.objects.Message;
import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.utils.Pair;
import de.mbs.abstracts.db.utils.SearchResult;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.test.TestExecuter;

/**
 * @author master
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageviewTest {

	private static String messageId = null;

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaMessageview#JavaMessageview(de.mbs.db.java.JavaView)}
	 * .
	 */
	@Test
	public final void test0JavaMessageview() {
		MessageView messageView = TestExecuter.getView().getMessageView();
		assertNotNull("Messageview nicht implementiert", messageView);
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaMessageview#add(de.mbs.abstracts.db.objects.Message)}
	 * .
	 */
	@Test
	public final void test1AddMessage() {
		try {
			MessageView messageView = TestExecuter.getView().getMessageView();
			Message testMessage = new Message(null);

			testMessage.setTopic("TestMessage");
			testMessage.setContent("MessageContent");
			testMessage.setFromUser(TestExecuter.getView().getUserView()
					.getUserByUserName("admin").getId());
			testMessage.addToUser(TestExecuter.getView().getUserView()
					.getUserByUserName("user").getId());

			messageId = messageView.add(testMessage);

			System.out.println("_____test1AddMessage");
			System.out.println("testMessage ID = " + messageId);

			assertNotNull("testMessage wurde nicht hinzugefuegt", messageId);

			assertEquals("Betreff nicht richtig hinzugefuegt",
					testMessage.getTopic(), "TestMessage");
			assertEquals("Inhalt nicht richtig hinzugefuegt",
					testMessage.getContent(), "MessageContent");
			assertEquals("Absender nicht richtig hinzugefuegt",
					testMessage.getFromUser(), TestExecuter.getView()
							.getUserView().getUserByUserName("admin").getId());

		} catch (Exception e) {
			class Local {
			}
			;
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaMessageview#get(java.lang.String)}.
	 */
	@Test
	public final void test1GetMessage() {
		try {
			MessageView messageView = TestExecuter.getView().getMessageView();
			Message testMessage = messageView.get(messageId);
			assertNotNull("testMessage kann nicht gefunden werden", testMessage);
			assertEquals("Betreff der gefundenen Nachricht ist nicht richtig",
					testMessage.getTopic(), "TestMessage");
		} catch (Exception e) {
			class Local {
			}
			;
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaMessageview#edit(de.mbs.abstracts.db.objects.Message)}
	 * .
	 */
	@Test
	public final void test2EditMessage() {
		try {
			MessageView messageView = TestExecuter.getView().getMessageView();
			Message testMessage = messageView.get(messageId);
			testMessage.setTopic("edited subject");
			assertNotNull("testMessage wurde nicht geaendert",
					messageView.edit(testMessage));
			assertEquals("geaenderter Betreff ist falsch",
					testMessage.getTopic(), "edited subject");
		} catch (Exception e) {
			class Local {
			}
			;
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaMessageview#getMessagesForUser(java.lang.String)}
	 * .
	 */
	@Test
	public final void test2GetMessagesForUser() {
		MessageView messageView = TestExecuter.getView().getMessageView();
		String userId = TestExecuter.getView().getUserView()
				.getUserByUserName("user").getId();
		assertNotNull("ungueltige NutzerId",
				messageView.getMessagesForUser(userId));
		assertTrue("es liegen keine Nachrichten fuer user vor", messageView
				.getMessagesForUser(userId).size() > 0);
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaMessageview#getUnreadMessagesForUser(java.lang.String)}
	 * .
	 */
	@Test
	public final void test3GetUnreadMessagesForUser() {

		MessageView messageView = TestExecuter.getView().getMessageView();
		String userId = TestExecuter.getView().getUserView()
				.getUserByUserName("user").getId();
		assertNotNull("ungueltige NutzerId",
				messageView.getUnreadMessagesForUser(userId));
		assertTrue("es liegen keine ungelesenen Nachrichten fuer user vor",
				messageView.getUnreadMessagesForUser(userId).size() > 0);
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaMessageview#search(java.lang.String, de.mbs.abstracts.db.objects.User)}
	 * .
	 */
	@Test
	public final void test4SearchMessage() {
		try {
			String suchString = "Message";
			MessageView messageView = TestExecuter.getView().getMessageView();
			Vector<Pair<SearchResult, String>> vec = messageView.search(
					suchString, TestExecuter.getView().getUserView()
							.getUserByUserName("user"));
			assertNotNull("Suchvektor ist NULL", vec);
			assertTrue("Groesse des Suchvektors ist 0", vec.size() > 0);
		} catch (Exception e) {
			class Local {}			;
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#getAll()}.
	 */
	@Test
	public final void test2GetAllMessages() {
		try {
			MessageView messageView = TestExecuter.getView().getMessageView();
			Vector<Message> vec = messageView.getAll();
			System.out.println("_______________testGetAllMessages");
			for (Message m : vec) {
				System.out.println("Message " + m.getTopic());
			}
			assertNotNull("Message Vektor ist Null", vec);
			assertTrue("Message Vektor hat eie Laenge von 0", vec.size() > 0);
		} catch (Exception e) {
			class Local {
			}
			;
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaMessageview#remove(java.lang.String)}.
	 */
	@Test
	public final void test5RemoveMessage() {
		try {
			MessageView messageView = TestExecuter.getView().getMessageView();
			assertTrue("Message wurde nicht geloescht",
					messageView.remove(messageId));
			assertNull(
					"geloeschte Message konnte immer noch aufgerufen werden",
					messageView.get(messageId));

		} catch (Exception e) {
			class Local {
			}
			;
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}

}
