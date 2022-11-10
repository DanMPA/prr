package prr.core.notification;

import java.io.Serializable;

import prr.core.client.Client;

public interface NotificationDistributor extends Serializable{
	public void sendNotification(Client client,Notification  notification);
	public boolean canReciveNotification(Client client);

}	
