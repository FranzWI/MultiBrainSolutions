package de.mbs.abstracts.db.objects;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

/**
 * Zwischen relation zwischen Nutzer und Portlet
 * 
 * @author master
 *
 */
public class UserPortlet extends DatabaseObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -554901431481773702L;

	private String settings;

	private String ownerId;
	private String portletId;

	private int order;

	public UserPortlet(String id) {
		super(id);
	}

	public UserPortlet(String id, long version) {
		super(id, version);
	}

	public String getSettings() {
		return settings;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getPortletId() {
		return portletId;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int i) {
		this.order = i;
	}

}
