package de.mbs.test.backend;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.test.TestExecuter;

public class PortletviewTest {
//for(User u:userView.getAll()){System.out.println(u.getUsername());}
	

	@Test
	public final void testGetAll() {
		PortletView portletView = TestExecuter.getView().getPortletView();
		Vector<Portlet> vec = portletView.getAll();
		for (Portlet p:vec){System.out.println(p.getName());}
		assertNotNull("",vec);
	}
/*	
@Test
	public final void testGetPossiblePortletsForUser() {
	PortletView portletView = TestExecuter.getView().getPortletView();
	portletView.getPossiblePortletsForUser(UserviewTest.testUserId);
	}
	*/
/*
	@Test
	public final void testJavaPortletview() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAdd() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testEdit() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGet() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRemove() {
		fail("Not yet implemented"); // TODO
	}
*/

}
