package prr.app.lookup;

import java.util.Comparator;

import prr.core.Network;
import prr.core.communication.Communication;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing all communications.
 */
class DoShowAllCommunications extends Command<Network> {

	DoShowAllCommunications(Network receiver) {
		super(Label.SHOW_ALL_COMMUNICATIONS, receiver);
	}

	/**
	 * @throws CommandException
	 */
	@Override
	protected final void execute() throws CommandException {
		_display.popup(_receiver.showCommunications(Comparator.comparing(Communication::getId)));
	}
}
