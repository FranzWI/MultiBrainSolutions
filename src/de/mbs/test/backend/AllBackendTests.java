package de.mbs.test.backend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GroupviewTest.class,  UserviewTest.class, PortletviewTest.class, MessageviewTest.class}) //hier alle zu testenden views einfügen
//@SuiteClasses({ UserviewTest.class, PortletviewTest.class, GroupviewTest.class })
public class AllBackendTests {
}