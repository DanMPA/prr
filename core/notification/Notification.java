package prr.core.notification;

public class Notification {
	private NotificationType _type;
	private int _terminalID;

	public Notification(NotificationType _type) {
		this._type = _type;
	}

	@Override
	public String toString() {
		return _type + "|" + _terminalID;
	}
}
