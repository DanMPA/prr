package prr.core.communication;

import java.io.Serializable;

import prr.core.terminal.Terminal;

public abstract class Communication implements Serializable {
	private static final long serialVersionUID = 202208091753L;

	private static int _communicationNumber;

	private int _id;
	private boolean _isPaid;
	private double _cost;
	private Terminal _origen;
	private Terminal _destination;
	private CommunicationStatus _communicationStatus;

	protected Communication(Terminal origen, Terminal destination) {
		this._id = _communicationNumber++;
		this._origen = origen;
		this._destination = destination;
	}

	public abstract String getType();

	public abstract int getUnits();

	public abstract double getPrice();

	public abstract CommunicationStatus getSatus();

	public int getId() {
		return _id;
	}

	@Override
	public String toString() {
		return String.join("|", getType(), String.valueOf(_id), _origen.getId(),
				_destination.getId(), String.valueOf(getUnits()),
				String.valueOf(getPrice()), String.valueOf(getSatus()));
	}

	public CommunicationStatus getCommunicationStatus() {
		return _communicationStatus;
	}

	public boolean isCommunicationOngoing() {
		return _communicationStatus == CommunicationStatus.ONGOING;
	}
}
