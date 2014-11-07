package de.mbs.db.java;

import groovy.xml.MarkupBuilder;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.abstracts.db.views.GroupView;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.db.java.views.JavaUserview;

public class JavaView extends DatabaseView {

	
	private JavaUserview userview;
	
	public JavaView() {
		this.userview = new JavaUserview();
	}
	
	
	@Override
	public Vector<MarkupBuilder> search(String search) {
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
		return new TreeMap<String,Long>();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupView getGroupView() {
		// TODO Auto-generated method stub
		return null;
	}

}
