package prr.core.notification;

import java.io.Serializable;

public class Notification implements Serializable{
	private static final long serialVersionUID = 202208091753L;

	private NotificationType _type;
	private int _terminalId;

	public Notification(NotificationType type) {
		this._type = type;
	}

	@Override
	public String toString() {
		return _type + "|" + _terminalId;
	}
}
