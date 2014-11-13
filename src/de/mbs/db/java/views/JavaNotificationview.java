package de.mbs.db.java.views;

import java.util.Date;
import java.util.UUID;
import java.util.Vector;

import de.mbs.abstracts.db.objects.Notification;
import de.mbs.abstracts.db.objects.User;
import de.mbs.abstracts.db.views.NotificationView;
import de.mbs.db.java.JavaView;
import de.mbs.db.java.utils.JavaHelper;

public class JavaNotificationview extends NotificationView {

	private JavaView view;
	private Vector<Notification> notifications = new Vector<Notification>();

	public JavaNotificationview(JavaView view) {
		this.view = view;
	}

	@Override
	public String add(Notification data) {
		data.setId(UUID.randomUUID().toString());
		this.notifications.add(data);
		return data.getId();
	}

	@Override
	public Notification edit(Notification data) {
		return JavaHelper.edit(data, this.notifications);
	}

	@Override
	public Notification get(String id) {
		return JavaHelper.get(id, this.notifications);
	}

	@Override
	public Vector<Notification> getAll() {
		return this.notifications;
	}

	@Override
	public boolean remove(String id) {
		return JavaHelper.remove(id, this.notifications);
	}

	@Override
	public Vector<Notification> getNotificationsForUser(String userId) {
		Date currentTime = new Date();
		Vector<Notification> notification = new Vector<Notification>();
		User u = this.view.getUserView().get(userId);
		if (u != null) {
			for (Notification n : this.notifications) {
				if (n.getToUser().contains(u.getId())) {
					if (n.getReleaseTimestamp().getTime() <= currentTime
							.getTime())
						notification.add(n);
				} else {
					for (String groupId : u.getMembership()) {
						if (n.getToGroup().contains(groupId)) {
							if (n.getReleaseTimestamp().getTime() <= currentTime
									.getTime()) {
								notification.add(n);
								break;
							}
						}
					}
				}
			}
			return notification;
		}
		return null;
	}

}
