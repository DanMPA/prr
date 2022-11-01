package prr.core.communication;

import prr.core.terminal.Terminal;

public class TextCommunication extends Communication {
	private String _message;
	
	public TextCommunication(Terminal origen, Terminal destination,String message) {
		super(origen, destination);
		this._message = message;
	}

	@Override
	public String getType() {
		return "TEXT";
	}

	@Override
	public int getUnits() {
		return _message.length();
	}

	@Override
	public double getPrice() {
		return 0;
	}

	@Override
	public CommunicationStatus getSatus() {
		return CommunicationStatus.FINISHED;
	}
}