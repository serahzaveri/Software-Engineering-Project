package Client;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import ServerExecutable.GameSession;

public class ServerRequests {
	
	private static final String address = "http://192.168.2.47:8080/ColtExpress";
	private HttpClient client;
	
	public ServerRequests()
	{
		client = HttpClient.newHttpClient();
	}
	
	public String[] login(String pRefreshToken)
	{
		HttpRequest request = HttpRequest.newBuilder()
							  .uri(URI.create(address + "/refresh?refresh_token=" + pRefreshToken))
							  .POST(BodyPublishers.noBody())
							  .build();
		
		try
		{
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() == 200)
			{
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(response.body());
				String[] tokens = new String[2];
				tokens[0] = node.get("access_token").asText();
				tokens[1] = node.get("refresh_token").asText();
				return tokens;
			}
			
			else
				return null;			
		} 
		
		catch (Exception e)
		{
			return null;
		}
	}
	
	public String[] login(String pUser, String pPass)
	{
		HttpRequest request = HttpRequest.newBuilder()
							  .uri(URI.create(address + "/login?username=" + pUser + "&password=" + pPass))
							  .POST(BodyPublishers.noBody())
							  .build();
		
		try
		{
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() == 200)
			{
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(response.body());
				String[] tokens = new String[2];
				tokens[0] = node.get("access_token").asText();
				tokens[1] = node.get("refresh_token").asText();
				return tokens;
			}
			
			else
				return null;			
		} 
		
		catch (Exception e)
		{
			return null;
		}
	}
	
	public ArrayList<GameSession> getActiveSessions(String pToken, Pregame pPregame)
	{
		pToken = convertPluses(pToken);
		HttpRequest request = HttpRequest.newBuilder()
							  .uri(URI.create(address + "/sessions?access_token=" + pToken))
							  .build();
		
		try
		{
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == 200)
			{
				ObjectMapper mapper = new ObjectMapper();
				ArrayList<GameSession> sessions = new ArrayList<GameSession>();
				JsonNode node = mapper.readTree(response.body());
				Iterator<JsonNode> jsonIterator = node.elements();
				while (jsonIterator.hasNext())
				{
					JsonNode tmpNode = jsonIterator.next();
					sessions.add(sessionJsonToObject(tmpNode));
				}
				return sessions;
			}
			
			else if(response.statusCode() == 401)
			{
				String newToken = pPregame.refreshToken();
				
				if (newToken == null || newToken == pToken)
					return null;
				else
					return getActiveSessions(newToken, pPregame);
			}
			
			else
				return null;			
		}
		
		catch (Exception e)
		{
			return null;
		}
	}
		
	public String createSession(String pToken, Pregame pPregame)
	{
		pToken = convertPluses(pToken);
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(URI.create(address + "/sessions?access_token=" + pToken))
				  .PUT(BodyPublishers.noBody())
				  .build();
		try
		{
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == 200)
				return response.body();
			
			else if(response.statusCode() == 401)
			{
				String newToken = pPregame.refreshToken();
				
				if (newToken == null || newToken == pToken)
					return null;
				else
					return createSession(newToken, pPregame);
			}
			
			else
				return null;
		} 
		catch (Exception e)
		{
			return null;
		}
	}
	
	public boolean joinSession(String pSessionID, String pToken, Pregame pPregame)
	{
		pToken = convertPluses(pToken);
		HttpRequest request = HttpRequest.newBuilder()
							  .uri(URI.create(address + "/sessions/join?sessionid=" + pSessionID + "&access_token=" + pToken))
							  .PUT(BodyPublishers.noBody())
							  .build();
		try
		{
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == 200)
				return true;
			
			else if(response.statusCode() == 401)
			{
				String newToken = pPregame.refreshToken();
				
				if (newToken == null || newToken == pToken)
					return false;
				else
					return joinSession(pSessionID, newToken, pPregame);
			}
			
			else
				return false;
		} 
		
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean launchSession(String pSessionID, String pToken, Pregame pPregame)
	{
		pToken = convertPluses(pToken);
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(URI.create(address + "/sessions/launch?sessionid=" + pSessionID + "&access_token=" + pToken))
				  .POST(BodyPublishers.noBody())
				  .build();

		try
		{
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == 200)
				return true;
			
			else if(response.statusCode() == 401)
			{
				String newToken = pPregame.refreshToken();
				
				if (newToken == null || newToken == pToken)
					return false;
				else
					return launchSession(pSessionID, newToken, pPregame);
			}
			
			else
				return false;
		} 
		
		catch (Exception e)
		{
			return false;
		}		
	}
	
	public boolean leaveSession(String pSessionID, String pToken, Pregame pPregame)
	{
		pToken = convertPluses(pToken);
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(URI.create(address + "/sessions/leave?sessionid=" + pSessionID + "&access_token=" + pToken))
				  .DELETE()
				  .build();
		
		try
		{
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == 200)
				return true;
			
			else if(response.statusCode() == 401)
			{
				String newToken = pPregame.refreshToken();
				
				if (newToken == null || newToken == pToken)
					return false;
				else
					return leaveSession(pSessionID, newToken, pPregame);
			}
			
			else
				return false;
		} 
		
		catch (Exception e)
		{
			return false;
		}
	}
	
	public GameSession getSessionInfo(String pSessionID, String pToken, Pregame pPregame)
	{
		pToken = convertPluses(pToken);
		HttpRequest request = HttpRequest.newBuilder()
							  .uri(URI.create(address + "/sessions/info?sessionid=" + pSessionID + "&access_token=" + pToken))
							  .build();
		
		HttpResponse<String> response;
		
		try
		{
			response = client.send(request, BodyHandlers.ofString());
			if (response.statusCode() == 200)
			{
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(response.body());
				return sessionJsonToObject(node);
			}
			
			else if(response.statusCode() == 401)
			{
				String newToken = pPregame.refreshToken();
				
				if (newToken == null || newToken == pToken)
					return null;
				else
					return getSessionInfo(pSessionID, newToken, pPregame);
			}
			
			else
				return null;
			
		} 
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static String convertPluses(String pToken)
	{
		StringBuilder builder = new StringBuilder();
		char[] tmp = pToken.toCharArray();
		
		for (char c: tmp)
		{
			if (c != '+')
				builder.append(c);
			else
				builder.append("%2B");
		}
		return builder.toString();
	}
	
	GameSession sessionJsonToObject(JsonNode sessionNode) throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		String creator = sessionNode.get("creator").asText();
		String sessionID = sessionNode.get("id").asText();
		int minSessionPlayers = sessionNode.get("minSessionPlayers").asInt();
		int maxSessionPlayers = sessionNode.get("maxSessionPlayers").asInt();
		boolean launched = sessionNode.get("launched").asBoolean();
		
		ArrayList<String> players = mapper.readValue(sessionNode.get("players").toString(), 
													 new TypeReference<ArrayList<String>>(){});
		
		String savegameid = sessionNode.get("savegameID").asText();
		
		return new GameSession(sessionID, creator, minSessionPlayers, maxSessionPlayers, launched, players, savegameid);
	}
}
