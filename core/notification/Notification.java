package prr.core.notification;

public class Notification {
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
