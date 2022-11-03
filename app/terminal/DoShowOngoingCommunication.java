package prr.app.terminal;

import java.util.Collection;
import java.util.Comparator;

import prr.core.Network;
import prr.core.communication.Communication;
import prr.core.communication.CommunicationStatus;
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
		Collection<Communication> tempCommunications = _network.showCommunications(
			Communication -> Communication
					.getSatus() == CommunicationStatus.ONGOING && Communication.getOrigen() == _receiver,
			Comparator.comparing(Communication::getId));
			if(tempCommunications.isEmpty()){
				_display.popup(Message.noOngoingCommunication());
			} else {
				_display.popup(tempCommunications);
			}
	}
}
