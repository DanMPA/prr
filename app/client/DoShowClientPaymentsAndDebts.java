package prr.app.client;

import prr.core.Network;
import prr.core.client.Client;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  final private String CLIENT_ID = "ClientID";

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    addStringField(CLIENT_ID, Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
    Client tempClient = _receiver.findClient(CLIENT_ID);
    Message.clientPaymentsAndDebts(CLIENT_ID, (long)tempClient.get_payments(), (long)tempClient.get_debts());
    } catch(UnknownClientKeyException ex){
      _display.popup(ex);
    }
  }
}
