/**
 * 
 */
package de.mbs.test.backend;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.test.TestExecuter;

/**
 * @author master
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotificationviewTest {
	private static String notificationId = null;

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaNotificationview#getNotificationsForUser(java.lang.String)}
	 * .
	 */
	@Test
	public final void testGetNotificationsForUser() {
		try{
			
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}	
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaNotificationview#JavaNotificationview(de.mbs.db.java.JavaView)}
	 * .
	 */
	@Test
	public final void test0JavaNotificationview() {
		NotificationView notificationView = TestExecuter.getView().getNotificationView();
		assertNotNull("Notificationview nicht implementiert", notificationView);
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaNotificationview#add(de.mbs.abstracts.db.objects.Notification)}
	 * .
	 */
	@Test
	public final void testAddNotification() {
		try{
			//TODO
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}	
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaNotificationview#edit(de.mbs.abstracts.db.objects.Notification)}
	 * .
	 */
	@Test
	public final void testEditNotification() {
		try{
			//TODO
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}	
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaNotificationview#get(java.lang.String)}.
	 */
	@Test
	public final void testGetNotification() {
		try{
			//TODO
			
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}	
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaNotificationview#getAll()}.
	 */
	@Test
	public final void testGetAllNotifications() {
		try{
			//TODO
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}	
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaNotificationview#remove(java.lang.String)}
	 * .
	 */
	@Test
	public final void testRemoveNotification() {
		try{
			//TODO
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}

}
