package prr.core.communication;

import prr.core.terminal.Terminal;

public class VideoCommunication extends InteractiveCommunication {
	public VideoCommunication(Terminal origen,Terminal destination) {
		super(origen, destination);
	}

	@Override
	public String getType() {
		return "VIDEO";
	}

	@Override
	public int getUnits() {
		return _duration;
	}

	@Override
	public double getPrice() {
		return getOrigen().getOwner().getClientLevel().getVideoCost(_duration);
	}

}
