package prr.core.terminal;

import prr.core.client.Client;

public class FancyTerminal extends Terminal {

	public FancyTerminal(String _id, Client _owner) {
		super(_id, _owner);
	}

	@Override
	public String toString() {
		String temp = super.toString();
		return String.join("|", "FANCY", temp);
	}
}
