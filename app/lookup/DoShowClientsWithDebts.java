package prr.app.lookup;

import java.util.Comparator;

import prr.core.Network;
import prr.core.client.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {

  DoShowClientsWithDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
	_display.popup(_receiver.showClients(Comparator.comparingDouble(Client::getDebts).thenComparing(Client::getKey),Client::hasDebt));
  }
}
