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
import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.views.GroupView;
import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.test.TestExecuter;

/**
 * @author master
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotificationviewTest {
	private static Notification noti = new Notification(null);
	private static String notID = null;
	private static GregorianCalendar now = new GregorianCalendar();

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
	public final void test1AddNotification() {
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
	 * {@link de.mbs.db.java.views.JavaNotificationview#get(java.lang.String)}.
	 */
	@Test
	public final void test2GetNotification() {
		try{
			NotificationView notificationView = TestExecuter.getView()
					.getNotificationView();
			noti = notificationView.get(notID);
			
			System.out.println("------ testGet ------");
			System.out.println("ID: "+noti.getId());
			System.out.println("Subject: "+noti.getSubject());
			System.out.println("Creation: "+noti.getCreateTimestamp());
			System.out.println("Release: "+noti.getReleaseTimestamp());
			System.out.println("toUser: "+noti.getToUser());
			System.out.println("toGroup: "+noti.getToGroup());
			System.out.println("Icon: "+noti.getIcon());
			System.out.println("Link: "+noti.getLink());
			
			assertNotNull("Notificatin failed to get", noti);			
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
	@Test
	public final void test3GetNotificationsForUser() {
		try{
			NotificationView notificationView = TestExecuter.getView()
					.getNotificationView();
			Vector<Notification> vec = notificationView
					.getNotificationsForUser(TestExecuter.getView()
							.getUserView().getUserByUserName("admin").getId());
			System.out
					.println("----- Possible notifications for user \"admin\" ------");
			for (Notification n : vec) {
				System.out.println("Notification: " + n.getSubject());
			}
			assertNotNull("NotificationVector ist null", vec);
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
	public final void test4EditNotification() {
		try{
			NotificationView notificationView = TestExecuter.getView()
					.getNotificationView();
			String userID = TestExecuter.getView().getUserView()
					.getUserByUserName("user").getId();
			String userGroup = TestExecuter.getView().getGroupView()
					.getUserGroupId();
			
			Vector<String> userVec = new Vector<String>();
			userVec.add(userID);
			Vector<String> groupVec = new Vector<String>();
			userVec.add(userGroup);
			
			noti = notificationView.get(notID);
			noti.setSubject("Updated Notification");
			noti.setToUser(userVec);
			noti.setToGroup(groupVec);
			noti = notificationView.edit(noti);
			
			System.out.println("------ testEdit ------");
			test2GetNotification();
			assertNotNull("Editierung fehlgeschlagen", noti);
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
	public final void test5GetAllNotifications() {
		try{
			NotificationView notificationView = TestExecuter.getView()
					.getNotificationView();
			Vector<Notification> vec = notificationView.getAll();
			
			System.out.println("------ testGetAllNotifications ------");
			for (Notification n : vec) {
				System.out.println("Notifications: " + n.getSubject());
			}
			assertNotNull("NotificationVector ist null", vec);
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
	public final void test6RemoveNotification() {
		try{
			NotificationView notificationView = TestExecuter.getView()
					.getNotificationView();
			boolean deleted = false;
			deleted = notificationView.remove(notID);
			System.out.println("------ testRemove ------");
			System.out.println("Notification gelöscht: "+deleted);
			System.out.println("NotificationID nach löschen: "+notificationView.get(notID));
			assertTrue("Remove konnte nicht durchgeführt werden", deleted == true);
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}
}
