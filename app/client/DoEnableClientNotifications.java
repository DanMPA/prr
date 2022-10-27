package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {
  final private String _clientId = "ClientID";
  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField(_clientId, Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try{
      if(!_receiver.toggleNotificationStatus(stringField(_clientId),true)){
        _display.add(Message.clientNotificationsAlreadyEnabled());
        _display.display();
      };
    } catch(UnknownKeyException ex){
		throw new UnknownClientKeyException(stringField(_clientId));
	}
  }
}
