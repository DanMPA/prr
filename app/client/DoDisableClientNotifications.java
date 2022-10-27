package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {
  final private String _clientId = "ClientID";

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField(_clientId, Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try{
      if(!_receiver.toggleNotificationStatus(stringField(_clientId),false)){
        _display.add(Message.clientNotificationsAlreadyDisabled());
        _display.display();
      };
    } catch(UnknownKeyException ex){
      throw new UnknownClientKeyException(stringField(_clientId));
    }
  }
}
