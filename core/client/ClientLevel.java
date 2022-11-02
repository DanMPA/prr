package prr.core.client;

import java.io.Serializable;

public abstract class  ClientLevel implements Serializable {
	private static final long serialVersionUID = 202208091753L;
	public abstract double getTextCost(int textLength);
	public abstract double getVoiceCost(double duration);
	public abstract double getVideoCost(double duration);
}
