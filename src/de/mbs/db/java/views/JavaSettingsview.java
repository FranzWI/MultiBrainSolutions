package de.mbs.db.java.views;

import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Settings;
import de.mbs.abstracts.db.views.SettingsView;
import de.mbs.db.java.utils.JavaHelper;
import de.mbs.mail.sendgrid.SGProp;

public class JavaSettingsview extends SettingsView {

	private Vector<Settings> settings = new Vector<Settings>();
	//TODO mögliche Prefixe dokumentieren PW --> passwort, NUMBER--> Zahl, BOOL-->CheckBOX
	public JavaSettingsview() {
		Settings setting = new Settings(UUID.randomUUID().toString());
		Properties mailProp = setting.getMailProperties();
		mailProp.put("SendGrid_Nutzername", SGProp.USER);
		mailProp.put("PW_SendGrid_Passwort", SGProp.PASSWORD);
		setting.setMailProperties(mailProp);
		
		Properties proxyProp = setting.getProxyProperties();
		proxyProp.put("HTTP_Proxy_Server", "");
		proxyProp.put("NUMBER_HTTP_Proxy_Port", "");
		setting.setProxyProperties(proxyProp);
		this.settings.add(setting);
	}
	
	@Override
	public Settings edit(Settings data) {
		return JavaHelper.edit(data, this.settings);
	}

	@Override
	public Settings get(String id) {
		return settings.get(0);
	}

	@Override
	public Vector<Settings> getAll() {
		return settings;
	}

}
