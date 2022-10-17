package prr.app.client;

import prr.core.Network;
import prr.core.exception.DuplicateEntityKeyException;
import prr.app.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("ClientKey", Message.key());
    addStringField("ClientName", Message.name());
    addIntegerField("TaxID", Message.taxId());
  }

  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.registerClient(stringField("ClientKey"), stringField("ClientName"), integerField("TaxID"));
    } catch (DuplicateEntityKeyException ex) {
		throw new DuplicateClientKeyException(stringField("ClientKey")); 
    }
  }

}
