package prr.core.communication;

import java.io.Serializable;

import prr.core.terminal.Terminal;

public abstract class Communication implements Serializable {
	private static final long serialVersionUID = 202208091753L;

	private static int _communicationNumber;

	private int _id;
	private boolean _isPaid;
	protected double _cost;
	private Terminal _origen;
	private Terminal _destination;

	private CommunicationStatus _communicationStatus;

	protected Communication(Terminal origen, Terminal destination) {
		this._id = ++_communicationNumber;
		this._origen = origen;
		this._destination = destination;
	}

	public abstract String getType();

	public abstract int getUnits();

	public abstract double getPrice(boolean isFriends);

	public abstract CommunicationStatus getSatus();

	public CommunicationStatus getCommunicationStatus() {
		return _communicationStatus;
	}

	public boolean isCommunicationOngoing() {
		return _communicationStatus == CommunicationStatus.ONGOING;
	}

	public int getId() {
		return _id;
	}

	public Terminal getOrigen() {
		return this._origen;
	}

	public boolean isPaid() {
		return _isPaid;
	}

	public Terminal getDestination() {
		return this._destination;
	}

	public void setPaid() {
		_isPaid = true;
	}

	public double getCost() {
		return _cost;
	}

	public void setCost(double cost) {
		_cost = cost;
	}

	public double paidCommunication() {
		setPaid();
		return getCost();
	}

	@Override
	public String toString() {
		return String.join("|", getType(), String.valueOf(_id), _origen.getId(),
				_destination.getId(), String.valueOf(getUnits()),
				String.valueOf(Math.round(getCost())),
				String.valueOf(getSatus()));
	}
}
