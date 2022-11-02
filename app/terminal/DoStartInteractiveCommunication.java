package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.UnavailableEntity;
import prr.core.exception.UnknownKeyException;
import prr.core.terminal.Terminal;
import prr.core.terminal.TerminalMode;
import java.util.Objects;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal,
				Terminal::canStartCommunication);
		addStringField("destinationId", Message.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		String destinationId = stringField("destinationId");
		try {
			_network.generateInteractiveCommunication(_receiver, destinationId,
					Form.requestString(Message.commType()));
		} catch (UnknownKeyException e) {
			throw new UnknownTerminalKeyException(destinationId);
		} catch (UnavailableEntity e) {
			if(e.getMode() == TerminalMode.SILENT){
				Message.destinationIsSilent(destinationId);
			} else if (e.getMode() == TerminalMode.BUSY){
				Message.destinationIsBusy(destinationId);
			}
		}

	}
}
