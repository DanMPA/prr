package prr.core.notification;

import prr.core.client.Client;

public class GenericNotificationDistributor implements NotificationDistributor {
	private static final long serialVersionUID = 202208091753L;

	@Override
	public void sendNotification(Client client, Notification notification) {
		client.addNotifications(notification);
		
	}

	@Override
	public boolean canReciveNotification(Client client) {
		return client.isReceiveNotification();
	}

	
}
