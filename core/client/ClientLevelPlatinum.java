package prr.core.client;

public class ClientLevelPlatinum extends ClientLevel {
	@Override
	public double getTextCost(int textLength) {
		if (textLength < 50) {
			return 0;
		} else {
			return 4;
		}
	}

	@Override
	public double getVoiceCost(double duration) {
		return 10 * duration;
	}

	@Override
	public double getVideoCost(double duration) {
		return 10 * duration;
	}

}
