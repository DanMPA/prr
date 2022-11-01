package prr.app.lookup;

import java.util.Comparator;

import prr.core.Network;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

	DoShowUnusedTerminals(Network receiver) {
		super(Label.SHOW_UNUSED_TERMINALS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.popup(
				_receiver.showTerminal(Comparator.comparing(Terminal::getId),
						e -> e.numberCommunications() == 0));
	}
}
