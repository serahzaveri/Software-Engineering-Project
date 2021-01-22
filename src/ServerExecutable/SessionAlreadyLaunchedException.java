package ServerExecutable;

@SuppressWarnings("serial")
class SessionAlreadyLaunchedException extends RuntimeException
{
	public SessionAlreadyLaunchedException()
	{
		super("This session has already been launched");
	}
}
