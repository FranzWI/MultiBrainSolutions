package de.mbs.abstracts.mail.definition;

import java.util.Vector;

public class Mail {

	private Vector<String> to;
	private String topic, from, text;

	
	public Mail(String to, String topic, String from, String text) {
		this.to = new Vector<String>();
		this.to.add(to);
		this.topic = topic;
		this.from = from;
		this.text = text;
	}
	
	public Mail(Vector<String> to, String topic, String from, String text) {
		this.to = to;
		this.topic = topic;
		this.from = from;
		this.text = text;
	}

	public Vector<String> getTo() {
		return to;
	}

	public String getTopic() {
		return topic;
	}

	public String getFrom() {
		return from;
	}

	public String getText() {
		return text;
	}

}
