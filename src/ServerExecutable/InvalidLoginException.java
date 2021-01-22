package ServerExecutable;

@SuppressWarnings("serial")
class InvalidLoginException extends RuntimeException
{
	public InvalidLoginException()
	{
		super("Invalid username and/or password.");
	}
}
