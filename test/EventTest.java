import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class EventTest {
	String name;
	String desc;
	List<String> options = new ArrayList<>();
	List<Integer> moodImpact = new ArrayList<>();
	List<Integer> socialImpact = new ArrayList<>();
	List<String> conse = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		name = "probanev";
		desc = "brobaleiras";
		options.add("probaopcio1");
		options.add("probaopcio2");
		moodImpact.add(0);
		moodImpact.add(1);
		socialImpact.add(2);
		socialImpact.add(3);
		conse.add("proba1");
		conse.add("proba2");
	}

	@Test
	void constructorTest() {
		Event emptyEvent = new Event();
		assertEquals(emptyEvent.getDescription(), null);
		assertEquals(emptyEvent.getName(), null);
		assertEquals(emptyEvent.getConse(), null);
		assertEquals(emptyEvent.getOptions(), null);
		Event event = new Event(name, desc, options, moodImpact, socialImpact, conse);
		assertEquals(event.getDescription(), desc);
		assertEquals(event.getName(), name);
		assertEquals(event.getConse(), conse);
		assertEquals(event.getCons(0), conse.get(0));
		assertEquals(event.getEnding(), false);
		assertEquals(event.getOptions(), options);
		Event ending = new Event(name, desc);
		assertEquals(ending.getDescription(), desc);
		assertEquals(ending.getName(), name);
		assertEquals(ending.getEnding(), true);
	}
	
	@Test
	void setterTest() {
		Event emptyEvent = new Event();
		emptyEvent.setName(name);
		assertEquals(emptyEvent.getName(), name);
		emptyEvent.setDescription(desc);
		assertEquals(emptyEvent.getDescription(), desc);
		emptyEvent.setConse(conse);
		assertEquals(emptyEvent.getConse(), conse);
		emptyEvent.setEnding(false);
		assertEquals(emptyEvent.getEnding(), false);
		emptyEvent.setMoodImpacts(moodImpact);
		assertEquals(emptyEvent.getMoodImpact(0), moodImpact.get(0));
		emptyEvent.setOptions(options);
		assertEquals(emptyEvent.getOptions(), options);
		emptyEvent.setSocialImpacts(socialImpact);
		assertEquals(emptyEvent.getSocialImpact(0), socialImpact.get(0));
	}
	
	@Test
	void toStringTest() {
		Event event = new Event(name, desc, options, moodImpact, socialImpact, conse);
		assertEquals(event.toString(),name);
		Event ending = new Event(name, desc);
		assertEquals(ending.toString(),name);
	}

}
