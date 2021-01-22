package ServerExecutable;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.net.http.HttpRequest.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.util.*;

public class LobbyServiceRequests {
	
	private HttpClient client;
	
	public LobbyServiceRequests()
	{
		client = HttpClient.newBuilder()
			      .version(HttpClient.Version.HTTP_1_1)
			      .followRedirects(Redirect.NORMAL)
			      .build();
	}
	
	public String[] getOAuthToken(String pUser, String pPass) throws IOException, InterruptedException
	{
		String params = "grant_type=password&username=" + pUser + "&password=" + pPass;
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/oauth/token?" + params))
			      .timeout(Duration.ofMinutes(1))
			      .header("Authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=")
			      .POST(BodyPublishers.noBody())
			      .build();


		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		if (response.statusCode() == 200)
		{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(response.body());
			String[] result = new String[2];
			result[0] = node.get("access_token").asText();
			result[1] = node.get("refresh_token").asText();
			return result;
		}
			
			else
				throw new InvalidLoginException();
	}
	
	public String[] getOAuthToken(String pRefreshToken) throws IOException, InterruptedException
	{
		String params = "grant_type=refresh_token&refresh_token=" + pRefreshToken;
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/oauth/token?" + params))
			      .timeout(Duration.ofMinutes(1))
			      .header("Authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=")
			      .POST(BodyPublishers.noBody())
			      .build();


		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		if (response.statusCode() == 200)
		{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(response.body());
			String[] result = new String[2];
			result[0] = node.get("access_token").asText();
			result[1] = node.get("refresh_token").asText();
			return result;
		}
			
			else
				throw new InvalidLoginException();
	}
	
	public String getUserFromToken(String token) throws InterruptedException, IOException
	{
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/oauth/username?access_token=" + token))
			      .timeout(Duration.ofMinutes(1))
			      .GET()
			      .build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		if (response.statusCode() == 200)
			return response.body();
		else
			throw new InvalidTokenException();
	}
	
	@SuppressWarnings("unused")
	public void registerGameService(int pMaxSessionPlayers, int pMinSessionPlayers, String token) throws JsonProcessingException, InterruptedException, IOException
	{
		Object bodyJson = new Object()
		{
			String location = "http://127.0.0.1:8080/ColtExpress";
			String maxSessionPlayers = "" + pMaxSessionPlayers;
			String minSessionPlayers = "" + pMinSessionPlayers;
			String name = "Colt Express";
			String webSupport = "true";
			public String getLocation()
			{
				return location;
			}
			public String getMaxSessionPlayers()
			{
				return maxSessionPlayers;
			}
			public String getMinSessionPlayers()
			{
				return minSessionPlayers;
			}
			public String getName()
			{
				return name;
			}
			public String getWebSupport()
			{
				return webSupport;
			}
		};
		ObjectMapper jsonSerializer = new ObjectMapper();
		String requestBodyString = jsonSerializer.writeValueAsString(bodyJson);
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/api/gameservices/" + "Colt%20Express" + "/?access_token=" + token))
			      .timeout(Duration.ofMinutes(1))
			      .header("Content-Type", "application/json")
			      .PUT(BodyPublishers.ofString(requestBodyString))
			      .build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		if (response.statusCode() == 200)
			return;
		
		else if (response.statusCode() == 401)
			throw new InvalidTokenException();
		
		else
		{
			char responseBodyPenultimateChar = response.body().toCharArray()[response.body().length() - 3];
			if (responseBodyPenultimateChar == 'm')
				throw new GameServiceNameNotMatchException();
			else
				throw new GameServiceAlreadyRegisteredException();
		}
	}
	
	public boolean deleteGameService(String token) throws InterruptedException, IOException
	{
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/api/gameservices/Colt%20Express/?access_token=" + token))
			      .timeout(Duration.ofMinutes(1))
			      .header("Content-Type", "application/json")
			      .DELETE()
			      .build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() == 200)
			return true;
		else
			return false;
		
	}
	
	@SuppressWarnings("unused")
	public String createSession(String pCreator, String token) throws JsonProcessingException, InterruptedException, IOException
	{
		Object bodyParams = new Object()
		{
			String creator = pCreator;
			String game = "Colt Express";
			String savegame = "";
			public String getCreator()
			{
				return creator;
			}
			public String getGame()
			{
				return game;
			}
			public String getSavegame()
			{
				return savegame;
			}
		};
		ObjectMapper jsonSerializer = new ObjectMapper();
		String body = jsonSerializer.writeValueAsString(bodyParams);
		
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/api/sessions?access_token=" + token))
			      .timeout(Duration.ofMinutes(1))
			      .header("Content-Type", "application/json")
			      .POST(BodyPublishers.ofString(body))
			      .build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() == 200)
			return response.body();
		
		else if (response.statusCode() == 401)
			throw new InvalidTokenException();
		else
			throw new BadRequestException();
		
	}
	
	public void launchSession(String sessionID, String token) throws IOException, InterruptedException
	{
		token= Server.convertPluses(token);
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/api/sessions/" + sessionID + "?access_token=" + token))
			      .timeout(Duration.ofMinutes(1))
			      .POST(BodyPublishers.noBody())
			      .build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() == 200)
			return;
		
		else if (response.statusCode() == 401)
			throw new InvalidTokenException();
		
		else
		{
			char responseBodyPenultimateChar = response.body().toCharArray()[response.body().length() - 4];
			
			if (responseBodyPenultimateChar == 'h')
				throw new SessionAlreadyLaunchedException();
				
			else if (responseBodyPenultimateChar == '-')
				throw new GameSessionNotFoundException();
			else
				throw new NotEnoughPlayersException();
		}
	}
	
	public void addPlayerToSession(String sessionID, String pUser, String token) throws InterruptedException, IOException
	{
		String params = sessionID + "/players/" + pUser + "?access_token=" + token;
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/api/sessions/" + params))
			      .timeout(Duration.ofMinutes(1))
			      .PUT(BodyPublishers.noBody())
			      .build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() == 200)
			return;
		
		else if (response.statusCode() == 401)
			throw new InvalidTokenException();
		
		else
		{
			char responseBodyLastChar = response.body().toCharArray()[response.body().length() - 2];
			if (responseBodyLastChar == 'e')
				throw new UserDoesNotMatchTokenException();
			else if (responseBodyLastChar == 'd')
				throw new GameSessionNotFoundException();
			else
				throw new SessionAlreadyJoinedException();
		}
	}
	
	public void removePlayerFromSession(String sessionID, String pUser, String token) throws IOException, InterruptedException
	{
		String params = sessionID + "/players/" + pUser + "?access_token=" + token;
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/api/sessions/" + params))
			      .timeout(Duration.ofMinutes(1))
			      .DELETE()
			      .build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() == 200)
			return;
		
		else if (response.statusCode() == 401)
			throw new InvalidTokenException();
		
		else
		{
			char responseBodyLastChar = response.body().toCharArray()[response.body().length() - 2];
			if (responseBodyLastChar == 'e')
				throw new UserDoesNotMatchTokenException();
			else if (responseBodyLastChar == 'd')
				throw new GameSessionNotFoundException();
			else
				throw new SessionAlreadyJoinedException();
		}
	}
	
	public GameSession getSessionInfo(String sessionID, String token) throws InterruptedException, IOException
	{
		HttpRequest request = HttpRequest.newBuilder()
							  .uri(URI.create("http://127.0.0.1:4242/api/sessions/" + sessionID + "?access_token=" + token))
							  .build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		if (response.statusCode() == 200)
		{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(response.body());
			return sessionJsonToObject(node, sessionID);
		}
		
		else if (response.statusCode() == 401)
			throw new InvalidTokenException();
		
		else
			throw new GameSessionNotFoundException();
	}
	
	public ArrayList<GameSession> getActiveSessions(String token) throws InterruptedException, IOException
	{
		HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create("http://127.0.0.1:4242/api/sessions?access_token=" + token))
			      .timeout(Duration.ofMinutes(1))
			      .GET()
			      .build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		if (response.statusCode() == 200)
		{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(response.body()).get("sessions");
			Iterator<String> gameSessionIterator = node.fieldNames();
			
			ArrayList<GameSession> gameSessions = new ArrayList<GameSession>();
			while (gameSessionIterator.hasNext())
			{
				String sessionID = gameSessionIterator.next();
				JsonNode sessionNode = node.get(sessionID);
				
				gameSessions.add(sessionJsonToObject(sessionNode, sessionID));
			}
			
			return gameSessions;
		}
		
		else if (response.statusCode() == 401)
			throw new InvalidTokenException();
		else
			return null;
	}
	
	GameSession sessionJsonToObject(JsonNode sessionNode, String sessionID) throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode gameParameters = sessionNode.get("gameParameters");
		String creator = sessionNode.get("creator").asText();
		int minSessionPlayers = gameParameters.get("minSessionPlayers").asInt();
		int maxSessionPlayers = gameParameters.get("maxSessionPlayers").asInt();
		boolean launched = sessionNode.get("launched").asBoolean();
		
		ArrayList<String> players = mapper.readValue(sessionNode.get("players").toString(), 
													 new TypeReference<ArrayList<String>>(){});
		
		String savegameid = sessionNode.get("savegameid").asText();
		
		return new GameSession(sessionID, creator, minSessionPlayers, maxSessionPlayers, launched, players, savegameid);
	}
}
