package file;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import model.Event;
import model.GameState;
import java.lang.reflect.Type;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager {
	public void saveGame(List<GameState> game, String filepath) {
		Gson gson = new GsonBuilder()
				 .setPrettyPrinting()
				 .create();
		try (FileWriter writer = new FileWriter(filepath)) {
	        gson.toJson(game, writer);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}


	public void saveEvents(List<Event> events, String filepath) {
	    Gson gson = new GsonBuilder()
	                 .setPrettyPrinting()
	                 .create();
	    try (FileWriter writer = new FileWriter(filepath)) {
	        gson.toJson(events, writer);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	

	public List<Event> loadEventList(String filepath) {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    Type eventListType = new TypeToken<List<Event>>() {}.getType();
	    List<Event> events = new ArrayList<>();
	    try (FileReader reader = new FileReader(filepath)) {
	        events = gson.fromJson(reader, eventListType);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return events;
	    
	}
	
	public List<GameState> loadGameState(String filepath) {
		Gson gson = new GsonBuilder().create();
		Type eventListType = new TypeToken<List<GameState>>() {}.getType();
	    List<GameState> events = new ArrayList<>();
	    try (FileReader reader = new FileReader(filepath)) {
	        events = gson.fromJson(reader, eventListType);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return events;
	}
}
