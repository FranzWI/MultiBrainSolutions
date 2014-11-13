package de.mbs.abstracts.mail;

import java.util.Map;
import java.util.TreeMap;

import de.mbs.abstracts.mail.definition.Mail;

public abstract class MailView {

	public final static String SENDER = "multibraincockpit@ba-dresden.de";
	
	protected abstract boolean sendMail(String to, String topic, String from,
			String text);

	protected abstract boolean sendHtmlMail(String to, String topic,
			String from, String html);

	public abstract String getServiceName();

	public Map<String, Boolean> sendMail(Mail m) {
		Map<String, Boolean> map = new TreeMap<String, Boolean>();
		for (String res : m.getTo()) {
			map.put(res,
					this.sendMail(res, m.getTopic(), m.getFrom(), m.getText()));
		}
		return map;
	}

	public Map<String, Boolean> sendHtmlMail(Mail m) {
		Map<String, Boolean> map = new TreeMap<String, Boolean>();
		for (String res : m.getTo()) {
			map.put(res,
					this.sendHtmlMail(res, m.getTopic(), m.getFrom(),
							m.getText()));
		}
		return map;
	}

	public abstract boolean isRunning();
	
	/**
	 * verbindung erneut versuchen
	 */
	public abstract void retry();
}
