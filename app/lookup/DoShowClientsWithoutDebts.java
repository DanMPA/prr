package prr.app.lookup;

import java.util.Comparator;

import prr.core.Network;
import prr.core.client.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

	DoShowClientsWithoutDebts(Network receiver) {
		super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.popup(_receiver.showClients(Comparator.comparing(e -> e.getKey().toLowerCase()),Client::hasNoDebt));
	}
}
