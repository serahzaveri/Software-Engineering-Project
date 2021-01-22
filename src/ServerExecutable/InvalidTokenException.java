package ServerExecutable;

@SuppressWarnings("serial")
class InvalidTokenException extends RuntimeException
{
	public InvalidTokenException()
	{
		super("Invalid authorization token.");
	}
}