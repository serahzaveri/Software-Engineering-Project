package ServerExecutable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ExceptionAdvice
{
	@ResponseBody
	@ExceptionHandler(InvalidTokenException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	
	String InvalidTokenHandler(InvalidTokenException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(InvalidLoginException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	
	String InvalidLoginHandler(InvalidLoginException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(GameSessionNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	
	String GameSessionNotFoundHandler(GameSessionNotFoundException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(UserDoesNotMatchTokenException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	String UserDoesNotMatchTokenHandler(UserDoesNotMatchTokenException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(SessionAlreadyJoinedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	String SessionAlreadyJoinedHandler(SessionAlreadyJoinedException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(SessionAlreadyLeftException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	String SessionAlreadyLeftHandler(SessionAlreadyLeftException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(SessionAlreadyLaunchedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	String SessionAlreadyLaunchedHandler(SessionAlreadyLaunchedException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(GameServiceAlreadyRegisteredException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	String GameServiceAlreadyRegisteredHandler(GameServiceAlreadyRegisteredException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(GameServiceNameNotMatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	String GameServiceNameNotMatchHandler(GameServiceNameNotMatchException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(NotEnoughPlayersException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	String NotEnoughPlayersHandler(NotEnoughPlayersException ex)
	{
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	
	String BadRequestHandler(BadRequestException ex)
	{
		return ex.getMessage();
	}
}
