package prr.core.terminal;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.InteractiveCommunication;
import prr.core.communication.VideoCommunication;

public class FancyTerminal extends Terminal {

	public FancyTerminal(String id, Client owner) {
		super(id, owner);
	}

	/**
	 * If the mode is not null and the mode can receive video communication,
	 * then return true.
	 * 
	 * @return boolean
	 */
	public boolean canReciveVideoCommunication(Terminal origin) {
		boolean canReceive = this._mode.canReciveCommunication();
		if(!canReceive){
			this._clientsToBeNotified.add(origin.getOwner());
		}
		return canReceive;
	}

	/**
	 * This function creates a new Video Communication , sets the current mode
	 * of the terminal to busy, and returns the a new VideoCommunication object.
	 * 
	 * @param destination The terminal that the communication is being made to.
	 * @return A new VideoCommunication object.
	 */
	public Communication makeVideoCommunication(Terminal destination) {
		InteractiveCommunication newCommunicaiton = new VideoCommunication(this,
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
	 * Converts Terminal Object to a String representation
	 * 
	 * @return String in the format
	 *         terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
	 *         terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts
	 */
	@Override
	public String toString() {
		String temp = super.toString();
		return String.join("|", "FANCY", temp);
	}
}
