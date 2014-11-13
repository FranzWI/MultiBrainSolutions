package de.mbs.mail.sendgrid;

import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import de.mbs.abstracts.db.DatabaseView;
import de.mbs.abstracts.mail.MailView;
import de.mbs.handler.ServiceHandler;

public class SendGridView extends MailView {

	private SendGrid sendgrid;

	private final static int OKEY = 0, ERROR = 1, NO_SEND_YET = -1;
	
	private int lastSendStatus = SendGridView.NO_SEND_YET;

	public SendGridView() {
		this.init();
	}

	private void init() {
		DatabaseView dbView = ServiceHandler.getDatabaseView();
		sendgrid = null;
		if (dbView != null) {

			Properties mail = dbView.getSettingsView().getAll().get(0)
					.getMailProperties();

			String sendgridUser = mail.getProperty("SendGrid_Nutzername");
			String sendgridPW = mail.getProperty("PW_SendGrid_Passwort");
			if (sendgridUser != null && !sendgridUser.isEmpty()
					&& sendgridPW != null && !sendgridPW.isEmpty())
				sendgrid = new SendGrid(sendgridUser, sendgridPW);
			else
				System.err
						.println("SendGridView: keine Login informatione hinterlegt");
			// Proxy einstellungen
			Properties proxyProp = dbView.getSettingsView().getAll().get(0)
					.getProxyProperties();
			if (proxyProp != null) {

				String server = proxyProp.getProperty("HTTP_Proxy_Server");
				String port = proxyProp.getProperty("NUMBER_HTTP_Proxy_Port");
				if (server != null && !server.isEmpty() && port != null
						&& !port.isEmpty() && port.matches("[0-9]?")) {
					HttpHost proxy = new HttpHost(server,
							Integer.parseInt(port));
					CloseableHttpClient http = HttpClientBuilder
							.create()
							.setProxy(proxy)
							.setUserAgent(
									"sendgrid/" + sendgrid.getVersion()
											+ ";java").build();
					sendgrid = sendgrid.setClient(http);
				} else {
					if (server != null && server.isEmpty() && port != null
							&& port.isEmpty()) {
					} else
						System.err
								.println("SendGridView: Proxy Einstellungen fehlerhaft");
				}
			}
			this.sendMail("derdudele@gmail.com", "Probemail",
					"multibraincockpit@ba-dresden.de", "Dies ist eine Testmail");
		} else {
			System.err
					.println("SendGridView: keine Login informatione hinterlegt");
			this.lastSendStatus = SendGridView.ERROR;
		}
	}

	@Override
	protected boolean sendMail(String to, String topic, String from, String text) {
		if (sendgrid == null) {
			this.lastSendStatus = SendGridView.ERROR;
			return false;
		}
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(to);
		email.setFrom(from);
		email.setSubject(topic);
		email.setText(text);
		try {
			SendGrid.Response response = sendgrid.send(email);
			this.lastSendStatus = SendGridView.OKEY;
			return response.getStatus();
		} catch (SendGridException e) {
			// TODO loggen
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
		if (this.lastSendStatus == SendGridView.OKEY)
			return true;
		if (this.lastSendStatus == SendGridView.NO_SEND_YET)
			return this
					.sendMail("derdudele@gmail.com", "Probemail",
							MailView.SENDER,
							"Dies ist eine Testmail");
		return false;
	}

	@Override
	protected boolean sendHtmlMail(String to, String topic, String from,
			String html) {
		if (sendgrid == null) {
			this.lastSendStatus = SendGridView.ERROR;
			return false;
		}
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(to);
		email.setFrom(from);
		email.setSubject(topic);
		email.setHtml(html);
		try {
			SendGrid.Response response = sendgrid.send(email);
			this.lastSendStatus = SendGridView.OKEY;
			return response.getStatus();
		} catch (SendGridException e) {
			// TODO loggen
			e.printStackTrace();
		}
		this.lastSendStatus = SendGridView.ERROR;
		return false;
	}

	@Override
	public void retry() {
		this.init();
	}

}
