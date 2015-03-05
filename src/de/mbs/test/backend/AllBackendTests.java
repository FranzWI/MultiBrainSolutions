package de.mbs.test.backend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.mbs.abstracts.db.views.NotificationView;

@RunWith(Suite.class)
@SuiteClasses({ UserviewTest.class,MessageviewTest.class, NotificationviewTest.class, PortletviewTest.class, GroupviewTest.class, SettingsviewTest.class}) //hier alle zu testenden views einfügen
public class AllBackendTests {
}