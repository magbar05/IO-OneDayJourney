package model;

import java.util.List;

public class GameState {
	private List<Event> events;
	private int mood;
	private int social;
	private String playerName;
	private List<Event> endings;
	private int score;
	
	public GameState(List<Event> game, List<Event> ends, String name) {
		playerName = name;
		events = game;
		mood = 50;
		social = 50;
		endings = ends;
	}
	
	public void changeMood(int i) {
		mood += i;
	}
	public void changeSocial(int i) {
		social += i;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getName() {
		return playerName;
	}
	
	public String toString() {
		return ("Játékos neve: "+playerName+"\nPontszám: "+score);
	}
	
	public Event getEnding() {
		score = (mood+social)/2;
		if(mood<33 && social<33) {
			return endings.get(0);
		}
		else if(mood<33 && 33<social && social<66) {
			return endings.get(1);
		}
		else if(mood<33 && 66<=social) {
			return endings.get(2);
		}
		else if(mood<66 && 33<mood && social<33) {
			return endings.get(3);
		}
		else if(mood<66 && 33<mood && social>33 && social<66) {
			return endings.get(4);
		}
		else if(mood<66 && 33<mood && social>=66) {
			return endings.get(5);
		}
		else if(mood>=66 && 33>social) {
			return endings.get(6);
		}
		else if(mood>=66 && 33<=social && social>66) {
			return endings.get(7);
		}
		else {
			return endings.get(8);
		}
		
	}
	
	
}
