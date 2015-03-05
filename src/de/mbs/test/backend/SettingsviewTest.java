/**
 * 
 */
package de.mbs.test.backend;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.Settings;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.mail.sendgrid.SGProp;
import de.mbs.test.TestExecuter;

/**
 * @author master
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SettingsviewTest {
	private static String settingsId = null;

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaSettingsview#JavaSettingsview()}.
	 */
	@Test
	public final void testJavaSettingsview() {
		SettingsView settingsView = TestExecuter.getView().getSettingsView();
		assertNotNull("Settingsview nicht implementiert", settingsView);
	}

	/**
	 * Test method for
	 * {@link de.mbs.db.java.views.JavaSettingsview#edit(de.mbs.abstracts.db.objects.Settings)}
	 * .
	 */
	@Test
	public final void testEditSettings() {
		try {
			SettingsView settingsView = TestExecuter.getView().getSettingsView();
			Settings s = settingsView.getAll().get(0);
			assertNotNull("keine Default Settings angelegt", s);
			
			Properties mailProp = s.getMailProperties();
			mailProp.put("SendGrid_Nutzername", SGProp.USER);
			mailProp.put("PW_SendGrid_Passwort", SGProp.PASSWORD);
			s.setMailProperties(mailProp);
			
			Properties proxyProp = s.getProxyProperties();
			proxyProp.put("HTTP_Proxy_Server", "");
			proxyProp.put("NUMBER_HTTP_Proxy_Port", "");
			s.setProxyProperties(proxyProp);
			
			assertNotNull("edit settings returned NULL",settingsView.edit(s));
			assertNotNull("mailProperties sind nicht richtig",settingsView.get(settingsId).getMailProperties());
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
	 * {@link de.mbs.db.java.views.JavaSettingsview#get(java.lang.String)}.
	 */
	@Test
	public final void test2GetSettings() {
		try{
			SettingsView settingsView = TestExecuter.getView().getSettingsView();
			assertNotNull("settingsId ist NULL",settingsId);
			assertNotNull("Get mit hilfe der SettingId schlug fehl",settingsView.get(settingsId));
		}catch (Exception e) {
			class Local {};
			String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName + "---");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaSettingsview#getAll()}.
	 */
	@Test
	public final void test1GetAllSettings() {
		try {
			SettingsView settingsView = TestExecuter.getView()
					.getSettingsView();
			List<Settings> vec = settingsView.getAll();
			assertNotNull("Vector ist null", vec);
			assertFalse("Vector größe ist 0", vec.size()<1 );
			settingsId = vec.get(0).getId();
			/*
			for (Settings s : vec) {
				System.out.println("Settings: " + s.getId());
			}*/
			assertNotNull("SettingsVektor ist null", vec);
			assertTrue("Groesse des SettingsVektor ist 0", vec.size() > 0);
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
