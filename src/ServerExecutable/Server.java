package ServerExecutable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

@SpringBootApplication
public class Server
{
	public static void main(String[] args) throws IOException, InterruptedException
	{	
		SpringApplication.run(Server.class, args);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://127.0.0.1:8080/ColtExpress/live")).timeout(Duration.ofSeconds(3)).build();
		LobbyServiceRequests req =  new LobbyServiceRequests();
		String token = req.getOAuthToken("admin", "admin")[0];
		token = convertPluses(token);
		
		try
		{
			req.registerGameService(6, 1, token);
		}
		catch (GameServiceAlreadyRegisteredException e)
		{
			
		}
		
		HttpResponse<String> response;
		do 
		{
			try
			{
				response = client.send(request, BodyHandlers.ofString());
				
				if (response.statusCode() != 200)
					break;
			} 
			catch (Exception e)
			{
				break;
			}
		
		}while(true);
		
		
		token = req.getOAuthToken("admin", "admin")[0];
		token = convertPluses(token);
		req.deleteGameService(token);
	}
	
	public static String convertPluses(String token)
	{
		StringBuilder builder = new StringBuilder();
		char[] tmp = token.toCharArray();
		
		for (char c: tmp)
		{
			if (c != '+')
				builder.append(c);
			else
				builder.append("%2B");
		}
		return builder.toString();
	}
}
