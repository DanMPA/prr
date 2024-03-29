package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.InvalidCommunicationExpextion;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
	addIntegerField("communicationId", Message.commKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
		_network.payCommunication(_receiver,integerField("communicationId"));
	} catch (InvalidCommunicationExpextion e) {
		_display.popup(Message.invalidCommunication());
	}
  }
}
