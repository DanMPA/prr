package prr.app.terminal;

import prr.core.Network;
import prr.core.terminal.Terminal;
import prr.core.terminal.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

	DoSilenceTerminal(Network context, Terminal terminal) {
		super(Label.MUTE_TERMINAL, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		if (!_receiver.changeTerminalMode(TerminalMode.SILENT)) {
			_display.popup(Message.alreadySilent());
		}
	}
}
