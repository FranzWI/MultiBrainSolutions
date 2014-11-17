package de.mbs.abstracts.db.objects;

import java.util.Properties;

import de.mbs.abstracts.db.objects.definition.DatabaseObject;

public class Settings extends DatabaseObject {

	private Properties mailProperties, dbProperties, proxyProperties;
	
	public Settings(String id) {
		super(id);
		this.mailProperties = new Properties();
		this.dbProperties = new Properties();
		this.proxyProperties = new Properties();
	}

	public Properties getMailProperties() {
		return mailProperties;
	}

	public void setMailProperties(Properties mailProperties) {
		this.mailProperties = mailProperties;
	}

	public Properties getDbProperties() {
		return dbProperties;
	}

	public void setDbProperties(Properties dbProperties) {
		this.dbProperties = dbProperties;
	}

	public Properties getProxyProperties() {
		return proxyProperties;
	}

	public void setProxyProperties(Properties proxyProperties) {
		this.proxyProperties = proxyProperties;
	}

}