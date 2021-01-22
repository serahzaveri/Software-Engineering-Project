package ServerExecutable;

@SuppressWarnings("serial")
class NotEnoughPlayersException extends RuntimeException
{
	public NotEnoughPlayersException()
	{
		super("Not enough players have joined this game to launch.");
	}
}
