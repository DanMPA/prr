package prr.app.client;

import prr.core.Network;
import prr.core.client.Client;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	final private String clientId = "ClientID";

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
		addStringField(clientId, Message.key());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			Client tempClient = _receiver.findClient(clientId);
			Message.clientPaymentsAndDebts(clientId, Math.round(tempClient.getPayments()), Math.round(tempClient.getDebts()));
		} catch (UnknownKeyException ex) {
			throw new UnknownClientKeyException(stringField(clientId));
		}
	}
}
