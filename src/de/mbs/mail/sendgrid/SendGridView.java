package de.mbs.mail.sendgrid;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import de.mbs.abstracts.mail.MailView;

public class SendGridView extends MailView {

	private SendGrid sendgrid;

	private final static int OKEY = 0, ERROR = 1, NO_SEND_YET = -1;

	private int lastSendStatus = SendGridView.NO_SEND_YET;

	public SendGridView() {
		sendgrid = new SendGrid(SGProp.USER, SGProp.PASSWORD);

		// TODO aus der Datenbank lesen
		HttpHost proxy = new HttpHost("192.168.2.4", 3128);
		CloseableHttpClient http = HttpClientBuilder.create().setProxy(proxy)
				.setUserAgent("sendgrid/" + sendgrid.getVersion() + ";java")
				.build();
		sendgrid = sendgrid.setClient(http);

	}

	@Override
	protected boolean sendMail(String to, String topic, String from, String text) {
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(to);
		email.setFrom(from);
		email.setSubject(topic);
		email.setText(text);
		try {
			SendGrid.Response response = sendgrid.send(email);
			this.lastSendStatus = SendGridView.OKEY;
			System.out.println("Email versandstatus: " + response.getMessage());
			return response.getStatus();
		} catch (SendGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.lastSendStatus = SendGridView.ERROR;
		return false;
	}

	public String getServiceName() {
		return "SendGrid";
	}

	@Override
	public boolean isRunning() {
		if (this.lastSendStatus == SendGridView.ERROR)
			return false;
		if (this.lastSendStatus == SendGridView.OKEY)
			return true;
		return this.sendMail("derdudele@gmail.com", "testmail",
				"management-cockpit@ba-dresden.de", "testmail");
	}

	@Override
	protected boolean sendHtmlMail(String to, String topic, String from,
			String html) {
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(to);
		email.setFrom(from);
		email.setSubject(topic);
		email.setHtml(html);
		try {
			SendGrid.Response response = sendgrid.send(email);
			this.lastSendStatus = SendGridView.OKEY;
			System.out.println("Email versandstatus: " + response.getMessage());
			return response.getStatus();
		} catch (SendGridException e) {
			// TODO loggen
			e.printStackTrace();
		}
		this.lastSendStatus = SendGridView.ERROR;
		return false;
	}

}
