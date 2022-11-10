package prr.core.terminal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Stream;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.CommunicationStatus;
import prr.core.communication.InteractiveCommunication;
import prr.core.communication.TextCommunication;
import prr.core.communication.VoiceCommunication;
import prr.core.exception.InvalidCommunicationExpextion;
import prr.core.exception.UnsupportedCommunicationExceptionDestination;
import prr.core.exception.UnsupportedCommunicationExceptionOrigin;
import prr.core.notification.Notification;
import prr.core.notification.NotificationType;

/**
 * Abstract terminal.
 */
public abstract class Terminal implements Serializable {
	private static final long serialVersionUID = 202208091753L;

	private String _id;
	private double _debt;
	private double _payments;
	protected Client _owner;
	protected TerminalMode _mode;
	protected TerminalMode _previousMode;
	private Collection<String> _terminalFriends;
	private List<Communication> _communicationsMade;
	protected InteractiveCommunication _currentInteractiveCommunication;
	private List<Communication> _communicationsRecived;
	private Notification _notification;
	protected List<Client> _clientsToBeNotified;

	protected Terminal(String id, Client owner) {
		this._id = id;
		this._owner = owner;
		this._mode = new TerminalModeIdle();
		this._terminalFriends = new TreeSet<>();
		this._communicationsMade = new ArrayList<>();
		this._communicationsRecived = new ArrayList<>();
		this._previousMode = new TerminalModeIdle();
		this._clientsToBeNotified = new ArrayList<>();
	}

	/**
	 * Return true if the communication with the given id is in the list of
	 * communications made by this teminal.
	 * 
	 * @param id the id of the communication to be validated
	 * @return A boolean value.
	 */
	public boolean validCommunication(int id) throws InvalidCommunicationExpextion{
		for (Communication aCommunication : _communicationsMade) {
			if (aCommunication.getId() == id) {
				// if(!aCommunication.isPaid()){
					return true;
				// }
			}
		}
		throw new InvalidCommunicationExpextion(String.valueOf(id));
	}

	/**
	 * Returns the number of communications made by this agent.
	 * 
	 * @return The number of communications made.
	 */
	public int getNumberCommunications() {
		return _communicationsMade.size();
	}

	/**
	 * If the current mode is a mode that can start communication, then return
	 * true, otherwise return false.
	 * 
	 * @return The return value is a boolean.
	 */
	public boolean canStartCommunication() {
		return _mode.canStartCommunication();
	}

	/**
	 * Adds a communication to the list of communications made by this termianl.
	 * 
	 * @param communication The communication that was made.
	 */
	public void addCommunicationMade(Communication communication) {
		_communicationsMade.add(communication);
	}

	/**
	 * Adds a communication to the list of communications recived by this
	 * termianl.
	 * 
	 * @param communication The communication that was recived.
	 */
	public void addCommunicationRecived(Communication communication) {
		_communicationsRecived.add(communication);
	}

	/**
	 * If the current mode allows ending communication, and the current
	 * interactive communication is not null, and the current interactive
	 * communication was started by this terminal, then return true.
	 * 
	 * @return The return value is a boolean.
	 */
	public boolean canEndCommunication() {
		return _mode.canEndCommunication()
				&& _currentInteractiveCommunication != null
				&& _currentInteractiveCommunication.getOrigen() == this;
	}

	/**
	 * If the mode is not OFF, then the user can receive text communication.
	 * 
	 * @return A boolean value.
	 */
	public boolean canReciveTextCommunication(Terminal origin) {
		boolean canReceive = !this._mode.toString().equals("OFF");
		if (!canReceive) {
			this._clientsToBeNotified.add(origin.getOwner());
		}
		return canReceive;
	}

	/**
	 * This function creates a new text communication, adds it to the list of
	 * communications made by this terminal, adds it to the list of
	 * communications received by the destination terminal, and returns the text
	 * communication.
	 * 
	 * @param destination The terminal that the communication is being sent to.
	 * @param message     The message to be sent.
	 * @return A new Communication object.
	 */
	public Communication makeTextCommunication(Terminal destination,
			String message) {
		Communication newCommunicaiton = new TextCommunication(this,
				destination, message);
		this.addCommunicationMade(newCommunicaiton);
		destination.addCommunicationRecived(newCommunicaiton);
		newCommunicaiton.setCost(
				newCommunicaiton.getPrice(findFriend(destination.getId())));
		double price = newCommunicaiton.getCost();
		_debt += price;
		_owner.setDebts(price);
		return newCommunicaiton;
	}

	/**
	 * If the mode is not null and the mode can receive communication, return
	 * true.
	 * 
	 * @return A boolean value.
	 */
	public boolean canReciveVoiceCommunication(Terminal origin) {
		boolean canReceive = this._mode.canReciveCommunication();
		if (!canReceive) {
			this._clientsToBeNotified.add(origin.getOwner());
		}
		return canReceive;
	}

	/**
	 * This function creates a new voice communication between this terminal and
	 * the given terminal, and sets the mode of both terminals to busy.
	 * 
	 * @param destination The terminal that you want to make a voice
	 *                    communication with.
	 * @return A new VoiceCommunication object.
	 */
	public Communication makeVoiceCommunication(Terminal destination) {
		InteractiveCommunication newCommunicaiton = new VoiceCommunication(this,
				destination);
		this._currentInteractiveCommunication = newCommunicaiton;
		this.setPreviousMode();
		this.setMode(new TerminalModeBusy());
		this.addCommunicationMade(newCommunicaiton);
		destination.setPreviousMode();
		destination.setMode(new TerminalModeBusy());
		destination.addCommunicationRecived(newCommunicaiton);
		return newCommunicaiton;
	}

	/**
	 * Returns true if the destination can receive video communication
	 * 
	 * @throws UnsupportedCommunicationExceptionDestination If not supported.
	 * @return A boolean value.
	 */
	public abstract boolean canReciveVideoCommunication(Terminal origin)
			throws UnsupportedCommunicationExceptionDestination;

	/**
	 * This function returns a Communication object that can be used to make a
	 * video call to the given terminal.
	 * 
	 * @param destination The destination terminal.
	 * @throws UnsupportedCommunicationExceptionDestination If not supported.
	 * @return A Communication object.
	 */
	public abstract Communication makeVideoCommunication(Terminal destination)
			throws UnsupportedCommunicationExceptionOrigin;

	/**
	 * Ends the ongoing communication, sets the duration, sets the communication
	 * status to finished, sets the mode to the previous mode, sets the
	 * destination's mode to the previous mode, adds the price to the debt, and
	 * returns the price.
	 * 
	 * @param duration the duration of the communication in seconds
	 * @return The price of the communication.
	 */
	public double endOngoingCommunication(int duration) {
		_currentInteractiveCommunication.setDuration(duration);
		_currentInteractiveCommunication
				.setCommunicationStatus(CommunicationStatus.FINISHED);
		this.changeTerminalMode(this.getPreviousMode());
		Terminal destination = _currentInteractiveCommunication
				.getDestination();
		double price = _currentInteractiveCommunication
				.getPrice(findFriend(destination.getId()));
		_currentInteractiveCommunication.setCost(price);
		destination.changeTerminalMode(destination.getPreviousMode());
		_debt += price;
		_owner.setDebts(price);
		_owner.getClientLevel().changeLevel(_owner);
		_currentInteractiveCommunication = null;
		return price;
	}

	/**
	 * If the terminal key is not the same as the terminal's own key, then add
	 * the terminal key to the terminal's list of friends
	 * 
	 * @param friendTerminalKey The terminal key of the friend to add.
	 */
	public void addFriend(String friendTerminalKey) {
		if (!Objects.equals(_id, friendTerminalKey)) {
			_terminalFriends.add(friendTerminalKey);
		}
	}

	/**
	 * This function returns true if the terminal has this friend
	 * 
	 * @param friendTerminalKey The terminal key of the friend you want to find.
	 * @return A boolean value.
	 */
	public boolean findFriend(String friendTerminalKey) {
		for (String friend : _terminalFriends) {
			if (friend.equals(friendTerminalKey)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove a friend from the list of friends.
	 * 
	 * @param friendTerminalKey The terminal key of the friend to remove.
	 */
	public void removeFriend(String friendTerminalKey) {
		_terminalFriends.remove(friendTerminalKey);
	}

	/**
	 * If the new mode is the same as the current mode, return false; otherwise,
	 * set the current mode to the new mode and return true
	 * 
	 * @param newMode The new mode to change to.
	 * @return A boolean value.
	 */
	public boolean changeTerminalMode(TerminalMode newMode) {
		if (Objects.equals(_mode.toString(), newMode.toString())) {
			return false;
		} else {
			_previousMode = _mode;
			_mode = newMode;
			this.notificationCreation();
			this.notifyClient();
			return true;
		}
	}

	public void notifyClient() {
		for (Client aClient : _clientsToBeNotified) {
			aClient.addNotifications(_notification);
		}
	}

	public void notificationCreation() {
		if (_previousMode.toString().compareTo("OFF") == 0
				&& _mode.toString().compareTo("IDLE") == 0) {
			_notification = new Notification(NotificationType.O2I, _id);
		} else if (_previousMode.toString().compareTo("BUSY") == 0
				&& _mode.toString().compareTo("IDLE") == 0) {
			_notification = new Notification(NotificationType.B2I, _id);
		} else if (_previousMode.toString().compareTo("SILENCE") == 0
				&& _mode.toString().compareTo("IDLE") == 0) {
			_notification = new Notification(NotificationType.S2I, _id);
		} else if (_previousMode.toString().compareTo("OFF") == 0
				&& _mode.toString().compareTo("SILENCE") == 0) {
			_notification = new Notification(NotificationType.O2S, _id);
		}
	}

	/**
	 * Returns the id of the current teminal.
	 * 
	 * @return The id of the teminal.
	 */
	public String getId() {
		return _id;
	}

	/**
	 * Returns the owner of this teminal.
	 * 
	 * @return The owner of the teminal.
	 */
	public Client getOwner() {
		return this._owner;
	}

	/**
	 * Get the balance of the teminal.
	 * 
	 * @return The balance of the teminal.
	 */
	public double getBalance() {
		return _payments - _debt;
	}

	/**
	 * Returns the current mode of the terminal.
	 * 
	 * @return The mode of the terminal.
	 */
	public TerminalMode getMode() {
		return _mode;
	}

	/**
	 * Sets the mode of the terminal.
	 * 
	 * @param mode The mode of the terminal.
	 */
	public void setMode(TerminalMode mode) {
		this._mode = mode;
	}

	/**
	 * Return a Collection of all the communications made by this teminal.
	 * 
	 * @return A Collection of the communications made by the teminal.
	 */
	public Collection<Communication> getCommunicationsMade() {
		return _communicationsMade;
	}

	/**
	 * Returns a stream of all the communications received by this teminal
	 * 
	 * @return A stream of the communications recived by the teminal.
	 */
	public Collection<Communication> getCommunicationsRecived() {
		return _communicationsRecived;
	}

	/**
	 * Returns the debt rounded to the nearest integer.
	 * 
	 * @return The debt is being returned.
	 */
	public long getDebt() {
		return Math.round(_debt);
	}

	/**
	 * If the number of payments is less than or equal to zero, return zero.
	 * Otherwise, return the number of payments rounded to the nearest integer.
	 * 
	 * @return The value of the _payments variable is being returned.
	 */
	public long getPayments() {
		return Math.round(_payments);
	}

	/**
	 * This function adds the value to the payments and subtracts the value from
	 * the debt.
	 * 
	 * @param value The amount of money to pay off the debt.
	 */
	public void payDebt(double value) {
		_payments += value;
		_debt -= value;
		_owner.payDebts(value);
	}

	/**
	 * Returns the previous mode of the terminal.
	 * 
	 * @return The previous mode.
	 */
	public TerminalMode getPreviousMode() {
		return _previousMode;
	}

	/**
	 * Set the previous mode to the current mode, and return the previous mode.
	 * 
	 * @return The previous mode.
	 */
	public TerminalMode setPreviousMode() {
		return _previousMode = _mode;
	}

	protected Notification getNotification() {
		return _notification;
	}

	/**
	 * Converts Terminal Object to a String representation
	 * 
	 * @return String in the format
	 *         terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
	 *         terminalId|clientId|terminalStatus|balance-paid|balance-debts
	 */
	@Override
	public String toString() {
		if (!_terminalFriends.isEmpty()) {
			return String.join("|", _id, _owner.getKey(), _mode.toString(),
					String.valueOf(Math.round(_payments)),
					String.valueOf(Math.round(_debt)), String.join(",",
							_terminalFriends.stream().sorted().toList()));
		} else {
			return String.join("|", _id, _owner.getKey(), _mode.toString(),
					String.valueOf(Math.round(_payments)),
					String.valueOf(Math.round(_debt)));
		}

	}

	/**
	 * If the id is null, return 0. Otherwise, return the hashcode of the id
	 * 
	 * @return The hashcode of the object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		return result;
	}

	/**
	 * If the object is not null, and the object is of the same class, then
	 * compare the IDs
	 * 
	 * @param obj The object to compare to.
	 * @return The hashcode of the object.
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
}
