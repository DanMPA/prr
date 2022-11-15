package prr.core.client;

public class ClientLevelPlatinum extends ClientLevel {
	@Override
	public double getTextCost(int textLength) {
		textStreaks++;
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

	@Override
	public void changeLevel(Client client) {
		if(client.getBalance() < 0){
			client.setClientLevel(new ClientLevelNormal());
		} else if(textStreaks > 2){
			client.setClientLevel(new ClientLevelGold());
			textStreaks =0;
		}
	}

	@Override
	public String toString() {
		return "PLATINUM";
	}
}
