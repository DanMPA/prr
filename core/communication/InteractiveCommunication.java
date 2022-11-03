package prr.core.communication;

import prr.core.terminal.Terminal;

public abstract class InteractiveCommunication extends Communication {

	private CommunicationStatus _communicationStatus;
	protected int _duration;

	protected InteractiveCommunication(Terminal origen, Terminal destination) {
		super(origen, destination);
		_communicationStatus = CommunicationStatus.ONGOING;
	}

	@Override
	public CommunicationStatus getSatus() {
		return _communicationStatus;
	}

	public void endInteractiveCommunication() {

	}

	public void setCommunicationStatus(
			CommunicationStatus _communicationStatus) {
		this._communicationStatus = _communicationStatus;
	}

	public void setDuration(int _duration) {
		this._duration = _duration;
	}
}
