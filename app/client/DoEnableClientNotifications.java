package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknowKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {
  final private String CLIENT_ID = "ClientID";
  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("ClientID", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try{
      if(!_receiver.toggleNotificationStatus(stringField(CLIENT_ID),true)){
        _display.add(Message.clientNotificationsAlreadyEnabled());
        _display.display();
      };
    } catch(UnknowKeyException ex){
		throw new UnknownClientKeyException(stringField(CLIENT_ID));
	}
  }
}
