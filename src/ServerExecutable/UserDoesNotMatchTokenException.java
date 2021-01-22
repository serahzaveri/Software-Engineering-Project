package ServerExecutable;

@SuppressWarnings("serial")
class UserDoesNotMatchTokenException extends RuntimeException
{
	public UserDoesNotMatchTokenException()
	{
		super("The provided username does not match the provided authorization token");
	}
}
