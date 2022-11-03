package prr.core.terminal;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.InteractiveCommunication;
import prr.core.communication.VideoCommunication;

public class FancyTerminal extends Terminal {

	public FancyTerminal(String id, Client owner) {
		super(id, owner);
	}

	public Communication makeVideoCommunication(Terminal destination) {
		InteractiveCommunication newCommunicaiton = new VideoCommunication(this,
				destination);
		this.addCommunicationMade(newCommunicaiton);
		this._currentInteractiveCommunication = newCommunicaiton;
		this.setPreviousMode();
		this.setMode(new TerminalModeBusy());
		destination.setPreviousMode();
		destination.setMode(new TerminalModeBusy());
		destination.addCommunicationMade(newCommunicaiton);
		return newCommunicaiton;
	}

	public boolean canReciveVideoCommunication() {
		return this._mode.canReciveCommunication();
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
