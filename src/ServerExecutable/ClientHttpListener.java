package ServerExecutable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.*;

@RestController
class ClientHttpListener
{
	private LobbyServiceRequests lobby;
	
	public ClientHttpListener()
	{
		lobby = new LobbyServiceRequests();
	}
	
	@GetMapping("/ColtExpress/sessions")
	ArrayList<GameSession> getActiveSessions(@RequestParam(value = "access_token") String token) throws IOException, InterruptedException
	{
		token = Server.convertPluses(token);
		return lobby.getActiveSessions(token);
		
	}
	
	@GetMapping("ColtExpress/sessions/info")
	GameSession getSessionInfo(@RequestParam(value = "sessionid") String sessionID, @RequestParam(value = "access_token") String token)
	{
		token = Server.convertPluses(token);
		GameSession session;
		try
		{
			session = lobby.getSessionInfo(sessionID, token);
		} 
		catch (IOException | InterruptedException e)
		{
			return null;
		}
		
		return session;
	}
	
	@PutMapping("/ColtExpress/sessions")
	String createSession(@RequestParam(value = "access_token") String token) throws IOException, InterruptedException
	{
		token= Server.convertPluses(token);
		String creator = lobby.getUserFromToken(token);
		return lobby.createSession(creator, token);
	}
	
	//Returns access_token in index 0 and refresh_token in index 1
	@SuppressWarnings("unused")
	@PostMapping("ColtExpress/login")
	Object login(@RequestParam(value = "username") String pUser, @RequestParam(value = "password") String pPass) throws IOException, InterruptedException
	{
		String[] tokens = lobby.getOAuthToken(pUser, pPass);
		Object result = new Object()
		{
			String access_token = tokens[0];
			String refresh_token = tokens[1];
			public String getAccess_token()
			{
				return access_token;
			}
			public String getRefresh_token()
			{
				return refresh_token;
			}
		};
		return result;
	}
	
	@SuppressWarnings("unused")
	@PostMapping("ColtExpress/refresh")
	Object login(@RequestParam(value = "refresh_token") String pRefreshToken) throws IOException, InterruptedException
	{
		String[] tokens = lobby.getOAuthToken(pRefreshToken);
		Object result = new Object()
		{
			String access_token = tokens[0];
			String refresh_token = tokens[1];
			public String getAccess_token()
			{
				return access_token;
			}
			public String getRefresh_token()
			{
				return refresh_token;
			}
		};
		return result;
	}
	
	@PutMapping("ColtExpress/sessions/join")
	void joinSession(@RequestParam(value = "sessionid") String sessionID, @RequestParam(value = "access_token") String token) throws IOException, InterruptedException
	{
		token = Server.convertPluses(token);
		String user = lobby.getUserFromToken(token);
		lobby.addPlayerToSession(sessionID, user, token);
	}
	
	@PostMapping("ColtExpress/sessions/launch")
	void launchSession(@RequestParam(value = "sessionid") String sessionID, @RequestParam(value = "access_token") String token) throws IOException, InterruptedException
	{
		token = Server.convertPluses(token);
		lobby.launchSession(sessionID, token);
	}
	
	@DeleteMapping("ColtExpress/sessions/leave")
	void leaveSession(@RequestParam(value = "sessionid") String sessionID, @RequestParam(value = "access_token") String token) throws IOException, InterruptedException
	{
		token = Server.convertPluses(token);
		String user = lobby.getUserFromToken(token);
		lobby.removePlayerFromSession(sessionID, user, token);
	}
	
	@GetMapping("ColtExpress/live")
	void isLive()
	{
		return;
	}
}
