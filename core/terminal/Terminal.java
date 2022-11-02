package prr.core.terminal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

import javax.management.Notification;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.InteractiveCommunication;
import prr.core.communication.TextCommunication;
import prr.core.communication.VoiceCommunication;
import prr.core.exception.UnsupportedCommunicationExceptionDestination;
import prr.core.exception.UnsupportedCommunicationExceptionOrigin;

/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable {
	private static final long serialVersionUID = 202208091753L;

	private String _id;
	private double _debt;
	private double _payments;
	protected TerminalMode _mode;

	private Collection<String> _terminalFriends;
	private Client _owner;
	private int _numberCommunications;
	private List<Communication> _communicationsMade;
	protected InteractiveCommunication _currentInteractiveCommunication;
	private List<Communication> _communicationsRecived;
	private List<Notification> _notifications;

	protected Terminal(String id, Client owner) {
		this._id = id;
		this._owner = owner;
		this._mode = new TerminalModeIdle();
		this._terminalFriends = new TreeSet<>();
		this._communicationsMade = new ArrayList<>();
		this._communicationsRecived = new ArrayList<>();
		this._notifications = new ArrayList<>();
	}

	/**
	 * Checks if this terminal can end the current interactive communication.
	 *
	 * @return true if this terminal is busy (i.e., it has an active interactive
	 *         communication) and
	 *         it was the originator of this communication.
	 **/
	public void addCommunicationMade(Communication communication) {
		_communicationsMade.add(communication);
		_numberCommunications += 1;
	}

	public void addCommunicationRecived(Communication communication) {
		_communicationsRecived.add(communication);
	}

	public void addNotification(Notification notification){
		_notifications.add(notification);
	}

	/**
	 * Gets number of Communications.
	 * @return int
	 */
	public int numberCommunications() {
		return _numberCommunications;
	}	
	
	public boolean canStartCommunication(){
		return _mode.canStartCommunication();
	}

	public boolean canEndCommunication(){
		return _mode.canEndCommunication();
	}
	public Communication makeTextCommunication(Terminal destination, String message) {
		Communication newCommunicaiton = new TextCommunication(this,destination,message);
		this.addCommunicationMade(newCommunicaiton);
		destination.addCommunicationRecived(newCommunicaiton);
		return newCommunicaiton;
	}

	public boolean canReciveTextCommunication() {
		return this._mode.canReciveCommunication();
	}

	public Communication makeVoiceCommunication(Terminal destination) {
		InteractiveCommunication newCommunicaiton = new VoiceCommunication(this, destination);
		this.addCommunicationMade(newCommunicaiton);
		this._currentInteractiveCommunication = newCommunicaiton;
		destination.addCommunicationMade(newCommunicaiton);
		return newCommunicaiton;
	}

	public boolean canReciveVoiceCommunication() {
		return this._mode.canStartCommunication();
	}

	public abstract Communication makeVideoCommunication(Terminal destination) throws UnsupportedCommunicationExceptionOrigin;
	public abstract boolean canReciveVideoCommunication() throws UnsupportedCommunicationExceptionDestination;


	public double endOngoingCommunication(int duration) {
		_currentInteractiveCommunication.setDuration(duration);
		double price = _currentInteractiveCommunication.getPrice();
		_mode = new TerminalModeIdle();
		_currentInteractiveCommunication = null;
		return price;
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
	public String getId() {
		return _id;
	}

	public Client getOwner(){
		return this._owner;
	}
	public double getBalance(){
		return _payments-_debt;
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
	public TerminalMode getMode() {
		return _mode;
	}

	public Stream<Communication> getCommunicationsMadeStream(){
		return _communicationsMade.stream();
	}
	public Stream<Communication> getCommunicationsRecivedStream(){
		return _communicationsRecived.stream();
	}
	
	/**
	 * Converts Terminal Object to a String representation
	 * @return String in the format
	 * 			terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
	 *			terminalId|clientId|terminalStatus|balance-paid|balance-debts
	 */
	@Override
	public String toString() {
		if (!_terminalFriends.isEmpty()) {
			return String.join("|", _id, _owner.getKey(), _mode.getName(), String.format("%.0f", _payments),
					String.format("%.0f", _debt), String.join(",", _terminalFriends));
		} else {
			return String.join("|", _id, _owner.getKey(), _mode.getName(), String.format("%.0f", _payments),
					String.format("%.0f", _debt));
		}

	}
}
