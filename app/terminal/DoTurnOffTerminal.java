package prr.app.terminal;

import prr.core.Network;
import prr.core.terminal.Terminal;
import prr.core.terminal.TerminalModeOff;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

	DoTurnOffTerminal(Network context, Terminal terminal) {
		super(Label.POWER_OFF, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		if (!_receiver.changeTerminalMode(new TerminalModeOff())) {
			_display.popup(Message.alreadyOff());
		}
	}
}
