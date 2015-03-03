package de.mbs.test.backend;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.mbs.abstracts.db.objects.Portlet;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.test.TestExecuter;

//Tests werden in alphabetischer Reihenfolge ausgefuehrt
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PortletviewTest {
	// for(User u:userView.getAll()){System.out.println(u.getUsername());}

	private static Portlet portlet = new Portlet(null);
	private static String portletID = null;

	@Test
	public final void test1JavaPortletview() {
		PortletView portletView = TestExecuter.getView().getPortletView();
		assertNotNull("Portletview nicht implementiert", portletView);
	}

	@Test
	public final void test2GetAllPortlets() {
		PortletView portletView = TestExecuter.getView().getPortletView();
		List<Portlet> vec = portletView.getAll();
		for (Portlet p : vec) {
			System.out.println("Portlets: " + p.getName());
		}
		assertNotNull("PortletVector ist null", vec);
	}

	@Test
	public final void test3AddPortlet() {
		System.out.println("------ testAdd ------");
		PortletView portletView = TestExecuter.getView().getPortletView();
		Vector<String> groups = new Vector<String>();
		groups.add(TestExecuter.getView().getGroupView().getAdminGroupId());
		portlet.setName("TestPortlet");
		portlet.setPath("TestPfad");
		portlet.setDescription("Das ist eine testweise angelegtes Portlet");

		portlet.setSizeXS(50);
		portlet.setSizeSM(100);
		portlet.setSizeMD(150);
		portlet.setSizeLG(200);
		portlet.setUsedByGroups(groups);
		portletID = portletView.add(portlet);
		System.out.println("PortletID: " + portletID);
		assertNotNull("PortletID ist null", portletID);
	}

	@Test
	public final void test4GetPortlet() {
		try {
			PortletView portletView = TestExecuter.getView().getPortletView();
			portlet = portletView.get(portletID);

			System.out.println("------ testGet ------");
			System.out.println("Name: " + portlet.getName());
			System.out.println("Description: " + portlet.getDescription());
			System.out.println("Path: " + portlet.getPath());
			System.out.println("SizeXS: " + portlet.getSizeXS());
			System.out.println("SizeSM: " + portlet.getSizeSM());
			System.out.println("SizeMD: " + portlet.getSizeMD());
			System.out.println("SizeLG: " + portlet.getSizeLG());
			System.out.println("UsedByGroups: " + portlet.getUsedByGroups());

			assertNotNull("Portlet ist null", portlet);
		} catch (Exception e) {
			class Local {};
		    String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName +"---"); 
			e.printStackTrace();
		}
	}

	@Test
	public final void test5EditPortlet() {
		try {
			PortletView portletView = TestExecuter.getView().getPortletView();
			portlet = portletView.get(portletID);
			portlet.setName("Edited Name");
			portlet = portletView.edit(portlet);

			System.out.println("----- testEdit -----");
			test4GetPortlet();
			assertNotNull("Editierung fehlgeschlagen", portlet);
		} catch (Exception e) {
			class Local {};
		    String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName +"---"); 
			e.printStackTrace();
		}

	}

	@Test
	public final void test6RemovePortlet() {
		try {
		PortletView portletView = TestExecuter.getView().getPortletView();
		boolean deleted = false;
		deleted = portletView.remove(portletID);
		System.out.println("------ testRemove ------");
		System.out.println("Portlet gelöscht: "+deleted);
		System.out.println("PortledID nach löschen: "+portletView.get(portletID));
		assertTrue("Remove konnte nicht durchgeführt werden", deleted == true);
		}catch (Exception e){
			class Local {};
		    String methodName = Local.class.getEnclosingMethod().getName();
			System.out.println("---stacktrace " + methodName +"---"); 
			e.printStackTrace();
		}
	}

	@Test
	public final void test7GetPossiblePortletsForUser() {
		PortletView portletView = TestExecuter.getView().getPortletView();
		Vector<Portlet> vec = portletView
				.getPossiblePortletsForUser(TestExecuter.getView()
						.getUserView().getUserByUserName("admin").getId());
		System.out
				.println("----- Possible portlets for user \" admin \" ------");
		for (Portlet p : vec) {
			System.out.println("Portlet: " + p.getName());
		}
		assertNotNull("PortletVector ist null", vec);
	}
}
