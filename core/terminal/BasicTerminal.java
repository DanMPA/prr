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
	 * @return String
	 */
	@Override
	public String toString() {
		return String.join("|", "BASIC", super.toString());
	}
}
