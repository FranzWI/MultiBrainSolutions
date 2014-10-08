package de.mbs.mail.sendgrid;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import de.mbs.interfaces.MailView;

public class SendGridView extends MailView {

	
	private SendGrid sendgrid;
	
	public SendGridView(){
		sendgrid = new SendGrid(SGProp.USER,SGProp.PASSWORD);
	}
	
	@Override
	public boolean sendMail(String to, String topic, String from, String text) {
		SendGrid.Email email = new SendGrid.Email();
	    email.addTo(to);
	    email.setFrom(from);
	    email.setSubject(topic);
	    email.setText(text);
	    try {
			SendGrid.Response response =  sendgrid.send(email);
			System.out.println("Email versandstatus: "+response.getMessage());
			return response.getStatus();
		} catch (SendGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String getServiceName(){
		return "SendGrid";
	}
	
	@Override
	public boolean isRunning() {
		return this.sendMail("derdudele@gmail.com", "testmail", "management-cockpit@ba-dresden.de", "testmail");
	}

}
