package prr.app.terminals;

import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;

import prr.core.Network;
import prr.core.exception.DuplicateEntityKeyException;
import prr.core.exception.KeyFormattingExemption;
import prr.core.exception.UnknownKeyException;
import prr.core.terminal.TerminalType;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		addStringField("terminalKey", Message.terminalKey());
		addOptionField("terminalType", Message.terminalType(), "BASIC", "FANCY");
		addStringField("clientID", Message.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {

		String terminalKey = stringField("terminalKey");
		String clientId = stringField("clientID");
		String termianlType = stringField("terminalType");

		try {
			_receiver.registerTerminal(terminalKey, clientId, termianlType);
		} catch (UnknownKeyException ex) {
			throw new UnknownClientKeyException(clientId);
		} catch (KeyFormattingExemption ex) {
			throw new InvalidTerminalKeyException(terminalKey);
		} catch (DuplicateEntityKeyException ex) {
			throw new DuplicateTerminalKeyException(terminalKey);
		}
	}
}
