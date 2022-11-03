package prr.core.communication;

import prr.core.terminal.Terminal;

public class VoiceCommunication extends InteractiveCommunication{

	public VoiceCommunication(Terminal origen,Terminal destination) {
		super(origen, destination);
		
	}

	@Override
	public String getType() {
		return "VOICE";
	}

	@Override
	public int getUnits() {
		return _duration;
	}

	@Override
	public double getPrice(boolean isFriends) {
		if (isFriends) {
			return (getOrigen().getOwner().getClientLevel()
					.getVideoCost(_duration)) / 2;
		}
		return getOrigen().getOwner().getClientLevel().getVoiceCost(_duration);

	}
	
}
