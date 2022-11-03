package prr.core.notification;

import prr.core.client.Client;

public interface NotificationDistributor {
	public void sendNotification(Client client,Notification  notification);
	public boolean canReciveNotification(Client client);

}	
