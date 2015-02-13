/**
 * 
 */
package de.mbs.test.backend;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.mbs.abstracts.db.views.GroupView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.test.TestExecuter;

/**
 * @author master
 *
 */
// Tests werden in alphabetischer Reihenfolge ausgefuehrt
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class GroupviewTest {

	
	/**
	 * Test method for {@link de.mbs.abstracts.db.DatabaseView#getGroupView()}.
	 */
	@Test
	public void test0IsGroupViewImplemented() {
		GroupView groupView = TestExecuter.getView().getGroupView();
		assertNotNull("Groupview nicht implementiert", groupView);
	}
	
	/**
	 * Test method for {@link de.mbs.db.java.views.JavaGroupview#JavaGroupview()}.
	 */
	@Test
	public final void testJavaGroupview() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaGroupview#add(de.mbs.abstracts.db.objects.Group)}.
	 */
	@Test
	public final void testAdd() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaGroupview#edit(de.mbs.abstracts.db.objects.Group)}.
	 */
	@Test
	public final void testEdit() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaGroupview#get(java.lang.String)}.
	 */
	@Test
	public final void testGet() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaGroupview#remove(java.lang.String)}.
	 */
	@Test
	public final void testRemove() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.db.java.views.JavaGroupview#getAll()}.
	 */
	@Test
	public final void testGetAll() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.abstracts.db.views.GroupView#getAdminGroupId()}.
	 */
	@Test
	public final void testGetAdminGroupId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.abstracts.db.views.GroupView#getUserGroupId()}.
	 */
	@Test
	public final void testGetUserGroupId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link de.mbs.abstracts.db.views.GroupView#update(java.util.Observable, java.lang.Object)}.
	 */
	@Test
	public final void testUpdate() {
		fail("Not yet implemented"); // TODO
	}

}
