package model;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Event {
	private String name;
	private String description;
	private List<String> options;
	private static final List<String> feelings = Arrays.asList("Undor", "Majré", "Derű", "Bánat", "Harag");
	private List<Integer> moodImpact;
	private List<Integer> socialImpact;
	private boolean ending;
	private List<String> conse;
	
	public Event(String title, String desc, List<String> option, List<Integer> moodimp, List<Integer> socialimp, List<String> conseq) {
		name = title;
		description = desc;
		options = option;
		moodImpact = moodimp;
		socialImpact = socialimp;
		conse = conseq;
		ending = false;
	}
	
	public Event() {
	}
	
	public Event(String title, String desc) {
		name = title;
		description = desc;
		options = null;
		moodImpact = null;
		socialImpact = null;
		ending = true;
	}
	
	public String getName() {
		return name;
	}
	public String getCons(int i) {
		return conse.get(i);
	}
	public String getDescription() {
		return description;
	}
	public String getOption(int i){
		return options.get(i);
	}
	public int getMoodImpact(int i) {
		return moodImpact.get(i);
	}
	public int getSocialImpact(int i) {
		return socialImpact.get(i);
	}
	public String getFeeling(int i) {
		return feelings.get(i);
	}
	public boolean getEnding() {
		return ending;
	}
	public void setName(String s) {
		name = s;
	}
	public void setDescription(String s) {
		description = s;
	}
	public void setOptions(List<String> in) {
		options = in;
	}
	public void setMoodImpacts(List<Integer> in) {
		moodImpact = in;
	}
	public void setSocialImpacts(List<Integer> in) {
		socialImpact = in;
	}
	public void setEnding(boolean end) {
		ending = end;
	}
	public void setConse(List<String> conseq) {
		conse = conseq;
	}
	
	public String toString() {
		if(!ending) {
			return (name);
		}
		else {
			return (name);
		}
	}
	
	@SuppressWarnings("resource")
	public List<Integer> project(){
		if(!ending) {
			int bemenet;
			List<Integer> impact = new ArrayList<>();
			System.out.println(toString());
			Scanner scanner = new Scanner(System.in);
			bemenet = Integer.valueOf(scanner.nextLine());
			System.out.println(conse.get(bemenet));
			impact.add(moodImpact.get(bemenet));
			impact.add(socialImpact.get(bemenet));
			return impact;
		}
		else {
			return null;
		}
	}

	public List<String> getOptions() {
		return options;
	}
	
	
	
}
