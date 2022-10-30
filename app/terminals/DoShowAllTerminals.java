package prr.app.terminals;

import java.util.Comparator;

import prr.core.Network;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

	DoShowAllTerminals(Network receiver) {
		super(Label.SHOW_ALL_TERMINALS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.addAll(_receiver.showAllTerminals(Comparator.comparing(Terminal::getId)));
		_display.display();
	}
}
