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
	private Collection<String> _terminalFrinds;
	private Client _owner;
	private int _numberCommunications;

	public Terminal(String _id, Client _owner) {
		this._id = _id;
		this._owner = _owner;
		this._mode = TerminalMode.ON;
		this._terminalFrinds = new TreeSet<String>();
	}

	/**
	 * Checks if this terminal can start a new communication.
	 *
	 * @return true if this terminal is neither off neither busy, false otherwise.
	 **/
	public boolean canStartCommunication() {
		return true;
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

	public int numberCommunications() {
		return _numberCommunications;
	}

	public boolean canEndCommunication() {
		return true;
	}

	public void makeSMS(Terminal to, String message) {

	}

	public void acceptSMS(Terminal from) {

	}

	public void makeVoiceCall(Terminal to) {

	}

	public void acceptVoiceCall(Terminal from) {

	}

	public void makeVideoCall(Terminal to) {

	}

	public void acceptVideoCall(Terminal from) {

	}

	public void endOngoingCommunication(int size) {

	}

	public void addFriend(String frientTerminalKey) {
		_terminalFrinds.add(frientTerminalKey);
	}

	public void removeFriend(String frientTerminalKey) {
		_terminalFrinds.remove(frientTerminalKey);
	}

	public boolean changeTerminalMode(TerminalMode newMode) {
		if (_mode.equals(newMode)) {
			return false;
		} else {
			_mode = newMode;
			return true;
		}
	}

	public String get_id() {
		return _id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
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
		Terminal other = (Terminal) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.join("|", _id, _owner.get_key(), _mode.toString(), String.format("%.0f", _payments),
				String.format("%.0f", _debt), String.join(",", _terminalFrinds));

	}

	// @Override
	// public int compareTo(Object o){
	// if(o instanceof Terminal){
	// Terminal terminl2 = (Terminal)o;
	// try{
	// return Integer.valueOf(this._id) - Integer.valueOf(terminl2.get_id());
	// } catch (NumberFormatException ex){
	// return 0;
	// }
	// }
	// return 0;
	// }

}
