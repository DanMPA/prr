package prr.app.terminal;

import java.util.Comparator;

import prr.core.Network;
import prr.core.communication.Communication;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

	DoShowOngoingCommunication(Network context, Terminal terminal) {
		super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.popup(_network.showCommunications(Communication::isCommunicationOngoing, Comparator.comparing(Communication::getId)));
	}
}
