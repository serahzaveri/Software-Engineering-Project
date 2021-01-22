package ServerExecutable;

@SuppressWarnings("serial")
class GameServiceNameNotMatchException extends RuntimeException
{
	public GameServiceNameNotMatchException()
	{
		super("The name provided in the request body does not match the one specified in the API path.");
	}
}
