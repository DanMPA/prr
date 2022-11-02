package prr.core.terminal;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.exception.UnsupportedCommunicationExceptionDestination;
import prr.core.exception.UnsupportedCommunicationExceptionOrigin;


public class BasicTerminal extends Terminal{

	public BasicTerminal(String id, Client owner) {
		super(id, owner);
	}

	@Override
	public Communication makeVideoCommunication(Terminal destination) throws UnsupportedCommunicationExceptionOrigin {
		throw new UnsupportedCommunicationExceptionOrigin("VIDEO");
	}
	
	@Override
	public boolean canReciveVideoCommunication()throws UnsupportedCommunicationExceptionDestination {
		throw new UnsupportedCommunicationExceptionDestination("VIDEO"); 
	}

	/**
	 * Converts Terminal Object to a String representation
	 * @return String in the format
	 * 			terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
	 *			terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts
	 */
	@Override
	public String toString() {
		return String.join("|", "BASIC", super.toString());
	}

}
