package de.mbs.db.java;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.abstracts.db.views.GroupView;
import de.mbs.abstracts.db.views.MessageView;
import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.abstracts.db.views.PortletView;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.abstracts.db.views.UserPortletView;
import de.mbs.abstracts.db.views.UserView;
import de.mbs.db.java.views.JavaGroupview;
import de.mbs.db.java.views.JavaMessageview;
import de.mbs.db.java.views.JavaNotificationview;
import de.mbs.db.java.views.JavaPortletview;
import de.mbs.db.java.views.JavaSettingsview;
import de.mbs.db.java.views.JavaUserPortletview;
import de.mbs.db.java.views.JavaUserview;

public class JavaView extends DatabaseView {

	private JavaUserview userview;
	private JavaGroupview groupview;
	private JavaPortletview portletview;
	private JavaSettingsview settingview;
	private JavaMessageview messageview;
	private JavaNotificationview notificationview;
	private JavaUserPortletview userportletview;

	public JavaView() {
		this.groupview = new JavaGroupview();
		this.userview = new JavaUserview(this);
		this.portletview = new JavaPortletview(this);
		this.settingview = new JavaSettingsview();
		this.messageview = new JavaMessageview(this);
		this.notificationview = new JavaNotificationview(this);
		this.userportletview = new JavaUserPortletview();

		this.addSearchableView(this.getUserView());
		this.addSearchableView(this.getMessageView());
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

	@Override
	public MessageView getMessageView() {
		return this.messageview;
	}

	@Override
	public SettingsView getSettingsView() {
		return this.settingview;
	}

	@Override
	public NotificationView getNotificationView() {
		return this.notificationview;
	}

	@Override
	public UserPortletView getUserPortletView() {
		return this.userportletview;
	}

}
