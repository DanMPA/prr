package prr.core.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import prr.core.notification.Notification;
import prr.core.terminal.Terminal;

public class Client implements Serializable {
	private static final long serialVersionUID = 202208091753L;

	private String _key;
	private String _name;
	private int _taxNumber;
	private ClientLevel _level;
	private boolean _receiveNotification;
	private Collection<Notification> _terminalNotifications;
	private Collection<Terminal> _associatedTerminals;
	private double _payments;
	private double _debts;

	public Client(String _key, String _name, int _taxNumber, ClientLevel _level, boolean _receiveNotification) {
		this._key = _key;
		this._name = _name;
		this._taxNumber = _taxNumber;
		this._level = _level;
		this._receiveNotification = _receiveNotification;
		this._associatedTerminals = new ArrayList<>();
		this._terminalNotifications = new ArrayList<>();
	}

	@Override
	public String toString() {
		return String.join("|", "CLIENT", _key, _name, String.valueOf(_taxNumber), _level.toString(),
				(_receiveNotification ? "YES" : "NO"), String.valueOf(_associatedTerminals.size()),
				String.format("%.0f", _payments), String.format("%.0f", _debts));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_key == null) ? 0 : _key.hashCode());
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

		Client other = (Client) obj;
		if (_key == null) {
			if (other._key != null)
				return false;
		} else if (!_key.equals(other._key))
			return false;
		return true;
	}

	public void addTermina(Terminal terminal){
		_associatedTerminals.add(terminal);
	}

	public String get_key() {
		return _key;
	}

	public boolean is_receiveNotification() {
		return _receiveNotification;
	}

	public void set_receiveNotification(boolean _receiveNotification) {
		this._receiveNotification = _receiveNotification;
	}

	public double get_payments() {
		return _payments;
	}

	public double get_debts() {
		return _debts;
	}

	public Collection<String> getNotifcations() {
		List<String> tempNotifications = new ArrayList<>();
		if (_receiveNotification) {
			for (Notification notification : _terminalNotifications) {
				tempNotifications.add(notification.toString());
			}
		}
		return tempNotifications;
	}
}