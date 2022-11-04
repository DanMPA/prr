package prr.core.terminal;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.exception.UnsupportedCommunicationExceptionDestination;
import prr.core.exception.UnsupportedCommunicationExceptionOrigin;

public class BasicTerminal extends Terminal {

	public BasicTerminal(String id, Client owner) {
		super(id, owner);
	}

	/**
	 * Cant make video communication throw an exception.
	 * 
	 * @param destination The destination terminal.
	 * @throws UnsupportedCommunicationExceptionOrigin
	 */
	@Override
	public Communication makeVideoCommunication(Terminal destination)
			throws UnsupportedCommunicationExceptionOrigin {
		throw new UnsupportedCommunicationExceptionOrigin("VIDEO");
	}

	/**
	 * If the destination cant receive video communication throw an exception.
	 * 
	 * @throws UnsupportedCommunicationExceptionDestination
	 */
	@Override
	public boolean canReciveVideoCommunication(Terminal origin)
			throws UnsupportedCommunicationExceptionDestination {
		throw new UnsupportedCommunicationExceptionDestination("VIDEO");
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
		return String.join("|", "BASIC", super.toString());
	}

}
