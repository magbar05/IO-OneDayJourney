import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Event;
import model.GameState;

import java.util.Arrays;
import java.util.List;

public class GameStateTest {
    private GameState gameState;
    private List<Event> endings;

    @BeforeEach
    public void setUp() {
        endings = Arrays.asList(
            new Event("Low Mood & Low Social", "Ending 0"),
            new Event("Low Mood & Mid Social", "Ending 1"),
            new Event("Low Mood & High Social", "Ending 2"),
            new Event("Mid Mood & Low Social", "Ending 3"),
            new Event("Mid Mood & Mid Social", "Ending 4"),
            new Event("Mid Mood & High Social", "Ending 5"),
            new Event("High Mood & Low Social", "Ending 6"),
            new Event("High Mood & Mid Social", "Ending 7"),
            new Event("High Mood & High Social", "Ending 8")
        );
        gameState = new GameState(endings, "TestPlayer");
    }

    @Test
    public void testInitialValues() {
        assertEquals("TestPlayer", gameState.getName());
        assertEquals(50, gameState.getMood());
        assertEquals(50, gameState.getSocial());
    }

    @Test
    public void testChangeMood() {
        gameState.changeMood(-10);
        assertEquals(40, gameState.getMood());
    }

    @Test
    public void testChangeSocial() {
        gameState.changeSocial(-10);
        assertEquals(40, gameState.getSocial());
    }

    @Test
    public void testGetEndingLowMoodLowSocial() {
        gameState.changeMood(-30);
        gameState.changeSocial(-30);
        assertEquals(endings.get(0), gameState.getEnding());
    }

    @Test
    public void testGetEndingLowMoodMidSocial() {
        gameState.changeMood(-30);
        gameState.changeSocial(-10);
        assertEquals(endings.get(1), gameState.getEnding());
    }

    @Test
    public void testGetEndingLowMoodHighSocial() {
        gameState.changeMood(-30);
        gameState.changeSocial(20);
        assertEquals(endings.get(2), gameState.getEnding());
    }

    @Test
    public void testGetEndingHighMoodHighSocial() {
        gameState.changeMood(50);
        gameState.changeSocial(50);
        assertEquals(endings.get(8), gameState.getEnding());
    }

}
