/**
 * 
 */
package de.mbs.test.backend;

import static org.junit.Assert.*;

import org.junit.Test;

import de.mbs.abstracts.db.views.MessageView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.test.TestExecuter;

/**
 * @author master
 *
 */
public class MessageviewTest {

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#getMessagesForUser(java.lang.String)}.
	 */
	@Test
	public final void testGetMessagesForUser() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#getUnreadMessagesForUser(java.lang.String)}.
	 */
	@Test
	public final void testGetUnreadMessagesForUser() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#JavaMessageview(de.mbs.db.java.JavaView)}.
	 */
	@Test
	public final void testJavaMessageview() {
		MessageView messageView = TestExecuter.getView().getMessageView();
		assertNotNull("Userview nicht implementiert", messageView);
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#search(java.lang.String, de.mbs.abstracts.db.objects.User)}.
	 */
	@Test
	public final void testSearch() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#add(de.mbs.abstracts.db.objects.Message)}.
	 */
	@Test
	public final void testAdd() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#edit(de.mbs.abstracts.db.objects.Message)}.
	 */
	@Test
	public final void testEdit() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#get(java.lang.String)}.
	 */
	@Test
	public final void testGet() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#getAll()}.
	 */
	@Test
	public final void testGetAll() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaMessageview#remove(java.lang.String)}.
	 */
	@Test
	public final void testRemove() {
		fail("Not yet implemented"); // TODO
	}

}
