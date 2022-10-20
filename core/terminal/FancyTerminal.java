package prr.core.terminal;

import prr.core.client.Client;

public class FancyTerminal extends Terminal {
	
	public FancyTerminal(String _id, Client _owner) {
		super(_id, _owner);
	}

	/**
	 * Converts Terminal Object to a String representation
	 * @return String in the format
	 * 			terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
	 *			terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts
	 */
	@Override
	public String toString() {
		String temp = super.toString();
		return String.join("|", "FANCY", temp);
	}
}
