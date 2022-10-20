package prr.core.terminal;

import prr.core.client.Client;

public class BasicTerminal extends Terminal {

	public BasicTerminal(String _id, Client _owner) {
		super(_id, _owner);
	}

	public void makeVideoCall() {
	}

	/**
	 * @return boolean
	 */
	boolean acceptVideoCall() {
		return false;
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
