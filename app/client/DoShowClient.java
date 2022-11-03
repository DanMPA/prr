package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.core.notification.Notification;

import java.util.Collection;

import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  private final String _clientId = "ClientID";

  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    addStringField(_clientId, Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try{
      String clientId = stringField(_clientId);
      String client = _receiver.showClient(clientId);
      _display.add(client);
      _display.addAll(_receiver.showNotifications(clientId));
      _display.display();
      
    } catch(UnknownKeyException ex){
     throw new UnknownClientKeyException(stringField(_clientId)); 
    }
  }
}
