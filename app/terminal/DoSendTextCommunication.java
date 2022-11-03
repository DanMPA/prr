package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.UnavailableEntity;
import prr.core.exception.UnknownKeyException;
import prr.core.terminal.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

	DoSendTextCommunication(Network context, Terminal terminal) {
		super(Label.SEND_TEXT_COMMUNICATION, context, terminal,
				Terminal::canStartCommunication);
		addStringField("destnationId", Message.terminalKey());
		addStringField("message", Message.textMessage());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_network.generateTextCommunication(_receiver,stringField("destnationId"),stringField("message"));
		} catch (UnknownKeyException e) {
			throw new UnknownTerminalKeyException(stringField("destnationId"));
		} catch (UnavailableEntity e){
			_display.popup(Message.destinationIsOff(stringField("destnationId")));
		}
		
	}
}
