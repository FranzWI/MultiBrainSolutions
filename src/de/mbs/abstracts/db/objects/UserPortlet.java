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
	
	private int xs, sm, md, lg; 

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

	public int getXs() {
		return xs;
	}

	public int getSm() {
		return sm;
	}

	public int getMd() {
		return md;
	}

	public int getLg() {
		return lg;
	}

	public void setXs(int xs) {
		this.xs = xs;
	}

	public void setSm(int sm) {
		this.sm = sm;
	}

	public void setMd(int md) {
		this.md = md;
	}

	public void setLg(int lg) {
		this.lg = lg;
	}

}
