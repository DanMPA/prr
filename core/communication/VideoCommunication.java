package prr.core.communication;

import prr.core.terminal.Terminal;

public class VideoCommunication extends InteractiveCommunication {
	protected VideoCommunication(Terminal origen,Terminal destination) {
		super(origen, destination);
	}

	@Override
	public String getType() {
		return "VIDEO";
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
