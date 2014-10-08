package de.mbs.interfaces;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public abstract class MailView {

	public abstract boolean sendMail(String to, String topic, String from,
			String text);

	public abstract String getServiceName();
	
	public Map<String, Boolean> sendMail(Vector<String> to, String topic,
			String from, String text) {
		Map<String, Boolean> map = new TreeMap<String, Boolean>();
		for (String res : to) {
			map.put(res, this.sendMail(res, topic, from, text));
		}
		return map;
	}

	public abstract boolean isRunning();

}
