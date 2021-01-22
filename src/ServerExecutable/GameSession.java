package ServerExecutable;
import java.util.ArrayList;
public class GameSession
{	
	private String id;
	private String creator;
	private int minSessionPlayers;
	private int maxSessionPlayers;
	private boolean launched;
	private ArrayList<String> players;
	private String savegameID;
	
	@SuppressWarnings("unchecked")
	public GameSession(String ID, String creator, int minSessionPlayers, int maxSessionPlayers,
						boolean launched, ArrayList<String> players, String savegameid)
	{
		this.id = ID;
		this.creator = creator;
		this.minSessionPlayers = minSessionPlayers;
		this.maxSessionPlayers = maxSessionPlayers;
		this.launched = launched;
		this.players = (ArrayList<String>)players.clone();
		this.savegameID = savegameid;
	}
	
	
	public String getid()
	{
		return id;
	}



	public String getCreator()
	{
		return creator;
	}



	public int getMinSessionPlayers()
	{
		return minSessionPlayers;
	}



	public int getMaxSessionPlayers()
	{
		return maxSessionPlayers;
	}



	public boolean isLaunched()
	{
		return launched;
	}



	public ArrayList<String> getPlayers()
	{
		return players;
	}



	public String getSavegameID()
	{
		return savegameID;
	}



	@Override
	public String toString()
	{
		return creator + "'s game " + players.size() + "/" + maxSessionPlayers + (launched ? " IN PROGRESS" : " waiting to start");
	}
}
