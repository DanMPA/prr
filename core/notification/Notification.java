package prr.core.notification;

import java.io.Serializable;

public class Notification implements Serializable {
	private static final long serialVersionUID = 202208091753L;

	private NotificationType _type;
	private String _terminalId;

	public Notification(NotificationType type, String terminalId) {
		this._type = type;
		this._terminalId = terminalId;
	}

	@Override
	public String toString() {
		return _type + "|" + _terminalId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_type == null) ? 0 : _type.hashCode());
		result = prime * result
				+ ((_terminalId == null) ? 0 : _terminalId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (_type != other._type)
			return false;
		if (_terminalId == null) {
			if (other._terminalId != null)
				return false;
		} else if (!_terminalId.equals(other._terminalId))
			return false;
		return true;
	}
}
