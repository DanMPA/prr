package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {
  final private String CLIENT_ID = "ClientID";

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("ClientID", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try{
      if(!_receiver.toggleNotificationStatus(stringField(CLIENT_ID),false)){
        _display.add(Message.clientNotificationsAlreadyDisabled());
        _display.display();
      };
    } catch(UnknownClientKeyException ex){
      _display.popup(ex);
    }
  }
}
