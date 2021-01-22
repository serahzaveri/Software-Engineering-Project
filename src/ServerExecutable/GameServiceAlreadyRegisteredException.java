package ServerExecutable;

@SuppressWarnings("serial")
class GameServiceAlreadyRegisteredException extends RuntimeException
{
	public GameServiceAlreadyRegisteredException()
	{
		super("This game service has already been registered.");
	}
}
