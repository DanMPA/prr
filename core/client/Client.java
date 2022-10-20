package prr.core.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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

	/***
	 * Constrictor of a client object.
	 * @param _key 
	 * @param _name
	 * @param _taxNumber
	 * @param _level
	 * @param _receiveNotification
	 */
	public Client(String _key, String _name, int _taxNumber, ClientLevel _level, boolean _receiveNotification) {
		this._key = _key;
		this._name = _name;
		this._taxNumber = _taxNumber;
		this._level = _level;
		this._receiveNotification = _receiveNotification;
		this._associatedTerminals = new ArrayList<>();
		this._terminalNotifications = new ArrayList<>();
	}

	/**
	 * Converts Client Object to a String representation.
	 * 
	 * @return String in the format
	 *         CLIENT|key|name|taxId|type|notifications|terminals|payments|debts
	 */
	@Override
	public String toString() {
		return String.join("|", "CLIENT", _key, _name, String.valueOf(_taxNumber), _level.toString(),
				(_receiveNotification ? "YES" : "NO"), String.valueOf(_associatedTerminals.size()),
				String.format("%.0f", _payments), String.format("%.0f", _debts));
	}

	/**
	 * Computes hasCode with respect to clients id.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_key == null) ? 0 : _key.hashCode());
		return result;
	}

	/**
	 * Verifies if two Client objects are the same.
	 * If there id are the same.
	 * 
	 * @param obj
	 * @return boolean true if the key of the clients is the same.
	 */
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

	/**
	 * Adds a terminal to a Client.
	 * 
	 * @param terminal
	 */
	public void addTerminal(Terminal terminal) {
		_associatedTerminals.add(terminal);
	}

	/**
	 * Gets the Clients ID.
	 * 
	 * @return String
	 */
	public String get_key() {
		return _key;
	}

	/**
	 * Is the current client receiving notification.
	 * 
	 * @return boolean true when client is receiving notification.
	 */
	public boolean is_receiveNotification() {
		return _receiveNotification;
	}

	/**
	 * Changes the state of Notifications.
	 * 
	 * @param _receiveNotification
	 */
	public void set_receiveNotification(boolean _receiveNotification) {
		this._receiveNotification = _receiveNotification;
	}

	/**
	 * Gets Payment.
	 * 
	 * @return double
	 */
	public double get_payments() {
		return _payments;
	}

	/**
	 * Get debts.
	 * 
	 * @return double
	 */
	public double get_debts() {
		return _debts;
	}

	/**
	 * Returns all the Notification associated to this client.
	 * 
	 * @return Collection<String> Collection of Notification in string format.
	 */
	public Collection<String> getNotifications() {
		return _receiveNotification ? _terminalNotifications.stream().map(e -> e.toString()).toList() : null;
	}
}