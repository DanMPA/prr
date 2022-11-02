package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.UnavailableEntity;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnsupportedCommunicationExceptionDestination;
import prr.core.exception.UnsupportedCommunicationExceptionOrigin;
import prr.core.terminal.Terminal;

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
			if(e.getMode().toString() == "SILENT"){
				Message.destinationIsSilent(destinationId);
			} else if (e.getMode().toString() == "BUSY"){
				Message.destinationIsBusy(destinationId);
			}
		} catch (UnsupportedCommunicationExceptionOrigin e) {
			_display.popup(Message.unsupportedAtOrigin(_receiver.getId(), e.getMessage()));
		} catch (UnsupportedCommunicationExceptionDestination e){
			_display.popup(Message.unsupportedAtDestination(destinationId, e.getMessage()));
		}

	}
}
