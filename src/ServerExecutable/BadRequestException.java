package ServerExecutable;

@SuppressWarnings("serial")
class BadRequestException extends RuntimeException
{
	public BadRequestException()
	{
		super("Bad request error. Check you request body and query parameters.");
	}
}
