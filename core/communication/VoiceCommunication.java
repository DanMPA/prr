package prr.core.communication;

import prr.core.terminal.Terminal;

public class VoiceCommunication extends InteractiveCommunication{

	protected VoiceCommunication(Terminal origen,Terminal destination) {
		super(origen, destination);
	}

	@Override
	public String getType() {
		return "VOICE";
	}

	@Override
	public int getUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
