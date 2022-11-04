package prr.core.client;

import java.io.Serializable;

public abstract class ClientLevel implements Serializable {
	private static final long serialVersionUID = 202208091753L;
	protected double commutativeCredits;
	protected int textStreaks;
	protected int videoStreaks;



	public abstract double getTextCost(int textLength);

	public abstract double getVoiceCost(double duration);

	public abstract double getVideoCost(double duration);

	public abstract void changeLevel(Client client);
}
