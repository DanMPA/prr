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
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

	DoShowCommunicationsToClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
		addStringField("ClientId", Message.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.popup(
					_receiver.showCommunications(stringField("ClientId"),
							Comparator.comparing(Communication::getId),
							Terminal::getCommunicationsRecived));
		} catch (UnknownKeyException e) {
			throw new UnknownClientKeyException(stringField("ClientId"));
		}
	}
}
