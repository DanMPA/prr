package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.UnavailableEntity;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnsupportedCommunicationExceptionDestination;
import prr.core.exception.UnsupportedCommunicationExceptionOrigin;
import prr.core.terminal.Terminal;


import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal,
				Terminal::canStartCommunication);
		addStringField("destinationId", Message.terminalKey());
		addOptionField("type", Message.commType(), "VOICE","VIDEO");
	}

	@Override
	protected final void execute() throws CommandException {
		String destinationId = stringField("destinationId");
		try {
			_network.generateInteractiveCommunication(_receiver, destinationId,
					optionField("type"));
		} catch (UnknownKeyException e) {
			throw new UnknownTerminalKeyException(destinationId);
		} catch (UnavailableEntity e) {
			switch (e.getMode()){
				case "SILENCE" -> _display.popup(Message.destinationIsSilent(destinationId));
				case "BUSY" ->_display.popup(Message.destinationIsBusy(destinationId));
				case "OFF" -> _display.popup(Message.destinationIsOff(destinationId));
			}
		} catch (UnsupportedCommunicationExceptionOrigin e) {
			_display.popup(Message.unsupportedAtOrigin(_receiver.getId(), e.getMessage()));
		} catch (UnsupportedCommunicationExceptionDestination e){
			_display.popup(Message.unsupportedAtDestination(destinationId, e.getMessage()));
		}

	}
}
