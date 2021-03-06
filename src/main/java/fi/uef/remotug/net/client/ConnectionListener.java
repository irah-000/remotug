package fi.uef.remotug.net.client;

import java.util.HashMap;

public interface ConnectionListener {
	public void gameValuesChanged(float balance, HashMap<Integer, Float> balances);
	public void readyAnnounced(int playerID, String playerName);
	public void winnerAnnounced(int winnerID);
	public void startAnnounced(long serverTime, int duration, int delay);
}
