package prr.core.terminal;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

import prr.core.client.Client;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {
	private static final long serialVersionUID = 202208091753L;

	private String _id;
	private double _debt;
	private double _payments;
	private TerminalMode _mode;

	private Collection<String> _terminalFriends;
	private Client _owner;
	private int _numberCommunications;

	public Terminal(String _id, Client _owner) {
		this._id = _id;
		this._owner = _owner;
		this._mode = TerminalMode.IDLE;
		this._terminalFriends = new TreeSet<String>();
	}

	/**
	 * Checks if this terminal can start a new communication.
	 *
	 * @return true if this terminal is neither off neither busy, false otherwise.
	 **/
	public boolean canStartCommunication() {
		return _mode == TerminalMode.BUSY && _mode == TerminalMode.OFF ? false : true;
	}

	/**
	 * Checks if this terminal can end the current interactive communication.
	 *
	 * @return true if this terminal is busy (i.e., it has an active interactive
	 *         communication) and
	 *         it was the originator of this communication.
	 **/
	public void addCommunication() {
		_numberCommunications += 1;
	}

	/**
	 * Gets number of Communications.
	 * @return int
	 */
	public int numberCommunications() {
		return _numberCommunications;
	}

	/**
	 * Checks if this terminal can end a communication.
	 * @return boolean
	 */
	public boolean canEndCommunication() {
		return !this.canStartCommunication();
	}

	/**
	 * @param to
	 * @param message
	 */
	public void makeSMS(Terminal to, String message) {

	}

	/**
	 * @param from
	 */
	public void acceptSMS(Terminal from) {

	}

	/**
	 * @param to
	 */
	public void makeVoiceCall(Terminal to) {

	}

	/**
	 * @param from
	 */
	public void acceptVoiceCall(Terminal from) {

	}

	/**
	 * @param to
	 */
	public void makeVideoCall(Terminal to) {

	}

	/**
	 * @param from
	 */
	public void acceptVideoCall(Terminal from) {

	}

	/**
	 * @param size
	 */
	public void endOngoingCommunication(int size) {

	}

	/**
	 * Adds a friend to a terminal.
	 * @param friendTerminalKey
	 */
	public void addFriend(String friendTerminalKey) {
		_terminalFriends.add(friendTerminalKey);
	}

	/**
	 * Removes a friend from a terminal.
	 * @param friendTerminalKey
	 */
	public void removeFriend(String friendTerminalKey) {
		_terminalFriends.remove(friendTerminalKey);
	}

	/**
	 * Changes terminal's mode
	 * @param newMode
	 * @return boolean true when the newMode is different than the actual mode
	 */
	public boolean changeTerminalMode(TerminalMode newMode) {
		if (_mode.equals(newMode)) {
			return false;
		} else {
			_mode = newMode;
			return true;
		}
	}

	/**
	 * Gets terminal ID.
	 * @return String
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * Compute hashCode with reference to terminal's ID
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		return result;
	}

	/**
	 * Verifies if two terminal objects are the same
	 * If their IDs are the same
	 * 
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Terminal other = (Terminal) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}

	/**
	 * Gets Terminal's mode
	 * @return TerminalMode
	 */
	public TerminalMode get_mode() {
		return _mode;
	}

	/**
	 * Converts Terminal Object to a String representation
	 * @return String in the format
	 * 			terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
	 *			terminalId|clientId|terminalStatus|balance-paid|balance-debts
	 */
	@Override
	public String toString() {
		if (_terminalFriends.size() != 0) {
			return String.join("|", _id, _owner.get_key(), _mode.toString(), String.format("%.0f", _payments),
					String.format("%.0f", _debt), String.join(",", _terminalFriends));
		} else {
			return String.join("|", _id, _owner.get_key(), _mode.toString(), String.format("%.0f", _payments),
					String.format("%.0f", _debt));
		}

	}
}
