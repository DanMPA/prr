package prr.app.lookup;

import java.util.Comparator;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.core.communication.Communication;
import prr.core.exception.UnknownKeyException;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

	DoShowCommunicationsFromClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
		addStringField("ClientId", Message.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.popup(
					_receiver.showCommunications(stringField("ClientId"),
							Comparator.comparing(Communication::getId),
							Terminal::getCommunicationsMadeStream));
		} catch (UnknownKeyException e) {
			throw new UnknownClientKeyException(stringField("ClientId"));
		}
	}
}
