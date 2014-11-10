package de.mbs.db.java;

import groovy.xml.MarkupBuilder;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.abstracts.db.views.GroupView;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.db.java.views.JavaGroupview;
import de.mbs.db.java.views.JavaPortletview;
import de.mbs.db.java.views.JavaUserview;

public class JavaView extends DatabaseView {

	private JavaUserview userview;
	private JavaGroupview groupview;
	private JavaPortletview portletview;

	public JavaView() {
		this.groupview = new JavaGroupview();
		this.userview = new JavaUserview(this);
		this.portletview = new JavaPortletview(this);
	}

	@Override
	public Map<String,MarkupBuilder> search(String search) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServiceName() {
		return "Java DB";
	}

	@Override
	public Vector<String> getDatabases() {
		return new Vector<String>();
	}

	@Override
	public Map<String, Long> getDatabaseSize() {
		return new TreeMap<String, Long>();
	}

	@Override
	public long getEntryCount(String database) {
		return 0;
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public boolean exit() {
		return true;
	}

	@Override
	public UserView getUserView() {
		return this.userview;
	}

	@Override
	public PortletView getPortletView() {
		return this.portletview;
	}

	@Override
	public GroupView getGroupView() {
		return this.groupview;
	}

}