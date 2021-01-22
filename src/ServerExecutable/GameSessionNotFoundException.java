package ServerExecutable;

@SuppressWarnings("serial")
class GameSessionNotFoundException extends RuntimeException
{
	public GameSessionNotFoundException()
	{
		super("Game session with specified ID does not exist.");
	}
}
