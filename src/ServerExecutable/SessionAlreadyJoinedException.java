package ServerExecutable;

@SuppressWarnings("serial")
class SessionAlreadyJoinedException extends RuntimeException
{
	public SessionAlreadyJoinedException()
	{
		super("You have already joined this session.");
	}
}
