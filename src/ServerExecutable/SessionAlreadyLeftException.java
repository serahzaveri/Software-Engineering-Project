package ServerExecutable;

@SuppressWarnings("serial")
class SessionAlreadyLeftException extends RuntimeException
{
	public SessionAlreadyLeftException()
	{
		super("This player is no longer in this session.");
	}
}
