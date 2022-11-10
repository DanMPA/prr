package prr.core.client;

public class ClientLevelNormal extends ClientLevel {

	private static final long serialVersionUID = 202208091753L;

	@Override
	public double getTextCost(int textLength) {
		if (textLength < 50) {
			return 10;
		} else if (50 <= textLength && textLength < 100) {
			return 16;
		} else {
			return 2 * textLength;
		}
	}

	@Override
	public double getVoiceCost(double duration) {
		return 20 * duration;
	}

	@Override
	public double getVideoCost(double duration) {
		return 30 * duration;
	}

	@Override
	public String toString() {
		return "NORMAL";
	}

	@Override
	public void changeLevel(Client client) {
		if(client.getBalance() > 500){
			client.setClientLevel(new ClientLevelGold());
		}
	}
}
