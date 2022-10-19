package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("FriendID", Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
	String friendsTerminalId = stringField("FriendID");
	try{
		_network.findTerminal(friendsTerminalId);
		_receiver.addFriend(friendsTerminalId);
	} catch(UnknownKeyException ex){
		throw new UnknownTerminalKeyException(friendsTerminalId);
	}
  }
}
