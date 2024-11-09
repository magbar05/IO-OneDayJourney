package main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;



import java.util.ArrayList;
import java.util.List;
import file.FileManager;
import model.GameState;
import model.Event;
import ui.GameUI;

public class Main {
	static FileManager file = new FileManager();
	static List<Event> events = new ArrayList<>();
	static List<Event> endings = new ArrayList<>();
	static List<GameState> results = new ArrayList<>();
	
	
	protected static void fileman() {
		if(file.loadEventList("events.json")!=null) {
			events = file.loadEventList("events.json");
		}
		if(file.loadEventList("endings.json")!=null) {
			endings = file.loadEventList("endings.json");
		}
		if(file.loadGameState("results.json")!=null) {
			results = file.loadGameState("results.json");
		}
	}
	
	public static void main(String[] args) throws IOException {
	    fileman();
	    GameUI main = new GameUI(events, endings, results);
	    main.setVisible(true);

	    main.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            // Frissítsük a változókat a GameUI legfrissebb adataival
				List<Event> updatedEvents = main.getEvents();
				List<Event> updatedEndings = main.getEndings();
				List<GameState> updatedResults = main.getResults();

				// Ezután mentjük a fájlokba a frissített adatokat
				file.saveEvents(updatedEvents, "events.json");
				file.saveEvents(updatedEndings, "endings.json");
				file.saveGame(updatedResults, "results.json");

				System.out.println("Adatok sikeresen elmentve az ablak bezárása után.");
	        }
	    });
	}

}
