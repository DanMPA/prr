package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.UnknowKeyException;
import prr.core.terminal.Terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.app.terminal.Menu;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
		addStringField("terminalKey", Message.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			Terminal terminal = _receiver.findTerminal(stringField("terminalKey"));
			new Menu(_receiver, terminal);
		} catch (UnknowKeyException ex) {
			throw new UnknownTerminalKeyException(stringField("terminalKey"));
		}

		// FIXME implement command
		// create an instance of prr.app.terminal.Menu with the
		// selected Terminal and open it

	}
}
