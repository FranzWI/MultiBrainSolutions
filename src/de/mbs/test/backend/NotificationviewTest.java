/**
 * 
 */
package de.mbs.test.backend;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.mbs.abstracts.db.objects.Notification;
import de.mbs.abstracts.db.views.GroupView;
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
	private static Notification noti = new Notification(null);
	private static String notID = null;
	private static GregorianCalendar now = new GregorianCalendar();
	private static String userID = TestExecuter.getView().getUserView()
			.getUserByUserName("user").getId();
	private static String userGroup = TestExecuter.getView().getGroupView()
			.getUserGroupId();
	
	//private static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

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
			NotificationView notificationView = TestExecuter.getView()
					.getNotificationView();
			String adminID = TestExecuter.getView().getUserView()
					.getUserByUserName("admin").getId();
			String adminGroup = TestExecuter.getView().getGroupView()
					.getAdminGroupId();
			
			Vector<String> userVec = new Vector<String>();
			userVec.add(adminID);
			
			Vector<String> groupVec = new Vector<String>();
			groupVec.add(adminGroup);
			
			noti.setSubject("Test Benachrichtigung");
			noti.setCreateTimestamp(now.getTime());
			noti.setReleaseTimestamp(now.getTime());
			noti.setToUser(userVec);
			noti.setToGroup(groupVec);
			noti.setIcon("TestString zum Icon");
			noti.setLink("TestString für einen Link");
			
			notID = notificationView.add(noti);
			System.out.println("------ testAdd ------");
			System.out.println("ID der neuen Notification: "+notID);
			assertNotNull("Notification ID ist null", notID);
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaNotificationview#getNotificationsForUser(java.lang.String)}
	 * .
	 */
	@Ignore
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
	 * {@link de.mbs.db.java.views.JavaNotificationview#edit(de.mbs.abstracts.db.objects.Notification)}
	 * .
	 */
	@Ignore
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
	@Ignore
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
	@Ignore
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
	@Ignore
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
