import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import file.FileManager;
import model.Event;
import model.GameState;

public class FileManagerTest {
    private FileManager fileManager;
    private List<Event> sampleEvents;
    private List<GameState> sampleGameStates;
    private final String eventFilePath = "test_events.json";
    private final String gameStateFilePath = "test_game_states.json";

    @BeforeEach
    public void setUp() {
        fileManager = new FileManager();
        sampleEvents = Arrays.asList(
            new Event("Event 1", "Description 1", Arrays.asList("Option 1", "Option 2"), Arrays.asList(5, -3), Arrays.asList(2, -1), Arrays.asList("Consequence 1", "Consequence 2")),
            new Event("Event 2", "Description 2", Arrays.asList("Option 3", "Option 4"), Arrays.asList(-1, 4), Arrays.asList(3, -2), Arrays.asList("Consequence 3", "Consequence 4"))
        );

        sampleGameStates = Arrays.asList(
            new GameState(sampleEvents, "Player1"),
            new GameState(sampleEvents, "Player2")
        );
    }

    @Test
    public void testSaveAndLoadEvents() {
        fileManager.saveEvents(sampleEvents, eventFilePath);
        List<Event> loadedEvents = fileManager.loadEventList(eventFilePath);
        assertEquals(sampleEvents.size(), loadedEvents.size());
        for (int i = 0; i < sampleEvents.size(); i++) {
            assertEquals(sampleEvents.get(i).getName(), loadedEvents.get(i).getName());
            assertEquals(sampleEvents.get(i).getDescription(), loadedEvents.get(i).getDescription());
            assertEquals(sampleEvents.get(i).getOptions(), loadedEvents.get(i).getOptions());
            assertEquals(sampleEvents.get(i).getConse(), loadedEvents.get(i).getConse());
        }
    }

    @Test
    public void testSaveAndLoadGameStates() {
        fileManager.saveGame(sampleGameStates, gameStateFilePath);
        List<GameState> loadedGameStates = fileManager.loadGameState(gameStateFilePath);
        assertEquals(sampleGameStates.size(), loadedGameStates.size());
        for (int i = 0; i < sampleGameStates.size(); i++) {
            assertEquals(sampleGameStates.get(i).getName(), loadedGameStates.get(i).getName());
            assertEquals(sampleGameStates.get(i).getScore(), loadedGameStates.get(i).getScore());
            assertEquals(sampleGameStates.get(i).getEndings().size(), loadedGameStates.get(i).getEndings().size());
        }
    }



    @Test
    public void testSaveAndLoadEmptyList() {
        fileManager.saveEvents(Arrays.asList(), eventFilePath);
        List<Event> loadedEvents = fileManager.loadEventList(eventFilePath);
        assertTrue(loadedEvents.isEmpty());
    }

    @Test
    public void testOverwriteFile() throws IOException {
        File file = new File(eventFilePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("This will be overwritten");
        }

        fileManager.saveEvents(sampleEvents, eventFilePath);
        List<Event> loadedEvents = fileManager.loadEventList(eventFilePath);
        
        assertEquals(sampleEvents.size(), loadedEvents.size());
    }
}
