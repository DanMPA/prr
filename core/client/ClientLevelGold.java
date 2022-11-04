package prr.core.client;

public class ClientLevelGold extends ClientLevel {
	@Override
	public double getTextCost(int textLength) {
		textStreaks += 1;
		if (textLength < 50) {
			return 10;
		} else if (50 <= textLength && textLength < 100) {
			return 10;
		} else {
			return 2D * textLength;
		}
	}

	@Override
	public double getVoiceCost(double duration) {
		return 10 * duration;
	}

	@Override
	public double getVideoCost(double duration) {
		videoStreaks ++;
		return 20 * duration;
	}

	@Override
	public void changeLevel(Client client) {
		if(client.getBalance() < 0){
			client.setClientLevel( new ClientLevelNormal());
		} else if(videoStreaks >= 5){
			client.setClientLevel(new ClientLevelPlatinum());
		}
	}

}
