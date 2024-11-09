package ui;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import file.FileManager;
import model.Event;
import model.GameState;

@SuppressWarnings("serial")
public class GameUI extends JFrame{
	FileManager file = new FileManager();
	private List<Event> events;
	private List<Event> endings;
	private List<GameState> results;
	public List<Event> getEvents() {
		return events;
	}
	public List<Event> getEndings() {
		return endings;
	}
	public List<GameState> getResults(){
		return results;
	}
	public GameUI(List<Event> event, List<Event> ending, List<GameState> result) {
		events = event;
		endings = ending;
		results = result;
		setTitle("Agymanók");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setResizable(true);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                file.saveEvents(getEvents(), "events.json");
				file.saveEvents(getEndings(), "endings.json");
				file.saveGame(getResults(), "results.json");
            }
        });
		
		JPanel game = new JPanel();
		game.setBackground(Color.LIGHT_GRAY);
		JButton switchtoGame = new JButton("Új játék");
		switchtoGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Game(events, endings, results);
			}
		});
		game.add(switchtoGame);
		
		JPanel editor = new JPanel();
		editor.setBackground(Color.LIGHT_GRAY);
		JButton switchtoEditor = new JButton("Események szerkesztése");
		switchtoEditor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Editor(events, endings, results);
			}
		});
		editor.add(switchtoEditor);
		
		JPanel results = new JPanel();
		results.setBackground(Color.LIGHT_GRAY);
		JButton switchtoResults = new JButton("Eddigi eredményeim");
		switchtoResults.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Results();
			}
		});
		results.add(switchtoResults);
		
		JLabel label = new JLabel("Üdvözlünk az Inside Out világában játszódó játékban! Kérlek válassz az alábbi lehetőségek közül:");
		JPanel cim = new JPanel();
		cim.add(label);
		
		add(cim, BorderLayout.NORTH);
		add(game, BorderLayout.WEST);
		add(editor, BorderLayout.CENTER);
		add(results, BorderLayout.EAST);
		pack();
	}
	

	public class Game extends JFrame {
	    private JFrame nevle = new JFrame();
	    private JFrame eventFrame = new JFrame();
	    private String playerName;
	    private int currentEventIndex = 0;
	    private GameState gameState;
	    private List<Event> events;
	    private List<Event> endings;
	    private List<GameState> results;
	    
	    public Game(List<Event> events, List<Event> endings, List<GameState> result) {
	        this.events = events;
	        this.endings = endings;
	        this.results = result;
	        
	        if (events.size() < 5 || endings.size() != 9) {
	            JOptionPane.showMessageDialog(null, "Nincs elég befejezés, vagy esemény.", "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
	            initPlayerNameInput();
	        }
	    }

	    private void initPlayerNameInput() {
	        nevle.setTitle("Új játék");
	        nevle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        nevle.setSize(400, 300);
	        
	        JPanel namePanel = new JPanel();
	        JLabel nameLabel = new JLabel("Add meg a neved, majd nyomj a kezdés gombra!");
	        namePanel.add(nameLabel);
	        
	        JTextField nameField = new JTextField(20);
	        JButton startButton = new JButton("Kezdés");
	        
	        startButton.addActionListener(e -> {
	            playerName = nameField.getText();
	            gameState = new GameState(events, endings, playerName);
	            nevle.dispose();
	            loadNextEvent();
	        });
	        
	        namePanel.add(nameField);
	        namePanel.add(startButton);
	        
	        nevle.add(namePanel, BorderLayout.CENTER);
	        nevle.setVisible(true);
	    }

	    private void loadNextEvent() {
	    	if (events == null || events.isEmpty() || currentEventIndex >= events.size()) {
	            JOptionPane.showMessageDialog(null, "Nincs több esemény.", "Error", JOptionPane.ERROR_MESSAGE);
	            showGameEnding();
	            return;
	        }
	        if (currentEventIndex < events.size()) {
	            Event currentEvent = events.get(currentEventIndex);
	            eventFrame.getContentPane().removeAll();
	            eventFrame.setTitle(currentEvent.getName());
	            eventFrame.setSize(900, 400);
	            eventFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
	            JMenuBar menu = new JMenuBar();
	            JMenu file = new JMenu("File");
	            JMenuItem nGame = new JMenuItem("New Game");
	            JMenuItem exit = new JMenuItem("Exit");
	            nGame.addActionListener(e->{
	            	eventFrame.dispose();
	            	new Game(events, endings, results);
	            });
	            exit.addActionListener(e->{
	            	eventFrame.dispose();
	            	new GameUI(events, endings, results);
	            });
	            file.add(nGame);
	            file.add(exit);
	            menu.add(file);
	            

	            JPanel eventPanel = new JPanel(new BorderLayout());
	            JTextArea eventDescription = new JTextArea(currentEvent.getDescription());
	            eventDescription.setEditable(false);
	            eventPanel.add(eventDescription, BorderLayout.NORTH);

	            JPanel felsobuttonPanel = new JPanel();
	            JPanel alsobuttonPanel = new JPanel();
	            for (int i = 0; i < currentEvent.getOptions().size(); i++) {
	                int optionIndex = i;
	                JButton optionButton = new JButton(currentEvent.getOption(i));
	                optionButton.addActionListener(e -> {
	                	handleEventChoice(currentEvent, optionIndex);
	                });
	                if(i<=2) {
	                felsobuttonPanel.add(optionButton);
	                }
	                else {
	                	alsobuttonPanel.add(optionButton);
	                }
	            }

	            eventPanel.add(alsobuttonPanel, BorderLayout.SOUTH);
	            eventPanel.add(felsobuttonPanel, BorderLayout.CENTER);
	            eventFrame.setJMenuBar(menu);
	            eventFrame.add(eventPanel);
	            eventFrame.revalidate();
	            eventFrame.repaint();
	            eventFrame.setVisible(true);
	        } else {
	            showGameEnding();
	        }
	    }

	    private void showGameEnding() {
	        Event endingEvent = gameState.getEnding();
	        JOptionPane.showMessageDialog(eventFrame, endingEvent.getDescription(), "Játék vége", JOptionPane.INFORMATION_MESSAGE);
	        results.add(gameState);
	        eventFrame.dispose();
	        new GameUI(events, endings, results);
	    }


	    private void handleEventChoice(Event event, int choiceIndex) {
	        int moodImpact = event.getMoodImpact(choiceIndex);
	        int socialImpact = event.getSocialImpact(choiceIndex);
	        gameState.changeMood(moodImpact);
	        gameState.changeSocial(socialImpact);
	        JOptionPane.showMessageDialog(eventFrame, event.getCons(choiceIndex), "Eredmény", JOptionPane.INFORMATION_MESSAGE);
	        currentEventIndex++;
	        loadNextEvent();
	    }

	}

	class Editor extends JFrame {
	    private List<Event> events = new ArrayList<>();
	    private List<Event> endings = new ArrayList<>();
	    private List<GameState> results = new ArrayList<>();
	    private JFrame valaszt = new JFrame();
	    private JFrame listaEvent = new JFrame();
	    private JFrame listaEnding = new JFrame();
	    private JFrame sheetEvent = new JFrame();

	    public Editor(List<Event> event, List<Event> ending, List<GameState> result) {
	        events = event;
	        endings = ending;
	        results = result;

	        valaszt.setTitle("Új játék");
	        valaszt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        valaszt.setSize(400, 300);
	        JPanel panel = new JPanel();
	        JButton eventbutton = new JButton("Egy eseményt szeretnék szerkeszteni");
	        eventbutton.addActionListener(e -> {
	            valaszt.dispose();
	            editEvent();
	        });

	        JButton endingbutton = new JButton("Egy befejezést szeretnék szerkeszteni");
	        endingbutton.addActionListener(e -> {
	            valaszt.dispose();
	            editEnding();
	        });

	        panel.add(eventbutton);
	        panel.add(endingbutton);
	        valaszt.add(panel);
	        valaszt.setVisible(true);
	    }

	    @SuppressWarnings({ "rawtypes", "unchecked" })
		private void editEvent() {
	        listaEvent.setTitle("Esemény kiválasztása");
	        listaEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        listaEvent.setSize(400, 300);

	        Object[] eventslist = events.toArray();
	        JPanel panel = new JPanel();
	        JComboBox box = new JComboBox(eventslist);

	        JButton szerkeszt = new JButton("Kiválasztott szerkesztése");
	        szerkeszt.addActionListener(e -> {
	            listaEvent.dispose();
	            sheetEvent((Event) box.getSelectedItem());
	        });

	        JButton uj = new JButton("Új esemény");
	        uj.addActionListener(e -> {
	            listaEvent.dispose();
	            sheetEvent(new Event());
	        });

	        panel.add(box);
	        panel.add(szerkeszt);
	        panel.add(uj);
	        listaEvent.add(panel);
	        listaEvent.setVisible(true);
	    }

	    @SuppressWarnings("unchecked")
		private void editEnding() {
	        listaEnding.setTitle("Befejezés kiválasztása");
	        listaEnding.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        listaEnding.setSize(400, 300);

	        Object[] endingsList = endings.toArray();
	        JPanel panel = new JPanel();
	        @SuppressWarnings("rawtypes")
			JComboBox box = new JComboBox(endingsList);

	        JButton szerkeszt = new JButton("Kiválasztott szerkesztése");
	        szerkeszt.addActionListener(e -> {
	            listaEnding.dispose();
	            sheetEnding((Event) box.getSelectedItem());
	        });

	        JButton uj = new JButton("Új befejezés");
	        uj.addActionListener(e -> {
	            listaEnding.dispose();
	            sheetEnding(new Event());
	        });

	        panel.add(box);
	        panel.add(szerkeszt);
	        panel.add(uj);
	        listaEnding.add(panel);
	        listaEnding.setVisible(true);
	    }
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private void sheetEvent(Event event) {
			Object[] szamok = new Integer[200];
			for (int i = -100; i < 100; i++) {
			    szamok[i + 100] = i;
			}

			sheetEvent.setTitle("Befejezés kiválasztása");
	        sheetEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        sheetEvent.setSize(400, 300);
	       
            
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 40, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Cím:"), gbc);
            
            gbc.gridx = 1;
            JTextField newName = new JTextField(20);
            newName.setEditable(true);
            panel.add(newName, gbc);
            
            
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(new JLabel("Leírás:"), gbc);
            
            gbc.gridx = 1;
            JTextField newDesc = new JTextField(20);
            newName.setEditable(true);
            panel.add(newDesc, gbc);
            
            List<Integer> mood = new ArrayList<>();
            List<Integer> relation = new ArrayList<>();
            List<String> reactions = new ArrayList<>();
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(new JLabel("Undor új reakciója:"), gbc);
            
            
            gbc.gridx = 1;
            JTextField newUnd = new JTextField(20);
            newName.setEditable(true);
            panel.add(newUnd, gbc);
            reactions.add(newUnd.getText());
            
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(new JLabel("Majré új reakciója:"), gbc);
            
            gbc.gridx = 1;
            JTextField newMaj = new JTextField(20);
            newName.setEditable(true);
            panel.add(newMaj, gbc);
            reactions.add(newMaj.getText());
            
            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(new JLabel("Derű új reakciója:"), gbc);
            
            gbc.gridx = 1;
            JTextField newDer = new JTextField(20);
            newName.setEditable(true);
            panel.add(newDer, gbc);
            reactions.add(newDer.getText());
            
            gbc.gridx = 0;
            gbc.gridy = 5;
            panel.add(new JLabel("Bánat új reakciója:"), gbc);
            
            gbc.gridx = 1;
            JTextField newBanat = new JTextField(20);
            newName.setEditable(true);
            panel.add(newBanat, gbc);
            reactions.add(newBanat.getText());
            
            gbc.gridx = 0;
            gbc.gridy = 6;
            panel.add(new JLabel("Harag új reakciója:"), gbc);
            
            gbc.gridx = 1;
            JTextField newHarag = new JTextField(20);
            newName.setEditable(true);
            panel.add(newHarag, gbc);
            reactions.add(newHarag.getText());
            
            gbc.gridx = 0;
            gbc.gridy = 7;
            panel.add(new JLabel("Undor érzelmi hatása:"), gbc);
            
            gbc.gridx = 1;
            JComboBox newUndmood = new JComboBox(szamok);
            panel.add(newUndmood, gbc);
            mood.add((int) newUndmood.getSelectedItem());
            
            gbc.gridx = 0;
            gbc.gridy = 8;
            panel.add(new JLabel("Undor kapcsolati hatása:"), gbc);
            
            
            gbc.gridx = 1;
            JComboBox newUndRelation = new JComboBox(szamok);
            panel.add(newUndRelation, gbc);
            relation.add((int) newUndRelation.getSelectedItem());
            
            gbc.gridx = 0;
            gbc.gridy = 9;
            panel.add(new JLabel("Majré érzelmi hatása:"), gbc);

            gbc.gridx = 1;
            JComboBox newMajMood = new JComboBox(szamok);
            panel.add(newMajMood, gbc);
            mood.add((int) newMajMood.getSelectedItem());

            gbc.gridx = 0;
            gbc.gridy = 10;
            panel.add(new JLabel("Majré kapcsolati hatása:"), gbc);

            gbc.gridx = 1;
            JComboBox newMajRelation = new JComboBox(szamok);
            panel.add(newMajRelation, gbc);
            relation.add((int) newMajRelation.getSelectedItem());

            gbc.gridx = 0;
            gbc.gridy = 11;
            panel.add(new JLabel("Derű reakciójának érzelmi hatása:"), gbc);

            gbc.gridx = 1;
            JComboBox newDerMood = new JComboBox(szamok);
            panel.add(newDerMood, gbc);
            mood.add((int) newDerMood.getSelectedItem());

            gbc.gridx = 0;
            gbc.gridy = 12;
            panel.add(new JLabel("Derű reakciójának kapcsolati hatása:"), gbc);

            gbc.gridx = 1;
            JComboBox newDerRelation = new JComboBox(szamok);
            panel.add(newDerRelation, gbc);
            relation.add((int) newDerRelation.getSelectedItem());

            gbc.gridx = 0;
            gbc.gridy = 13;
            panel.add(new JLabel("Bánat reakciójának érzelmi hatása:"), gbc);

            gbc.gridx = 1;
            JComboBox newBanatMood = new JComboBox(szamok);
            panel.add(newBanatMood, gbc);
            mood.add((int) newBanatMood.getSelectedItem());

            gbc.gridx = 0;
            gbc.gridy = 14;
            panel.add(new JLabel("Bánat reakciójának kapcsolati hatása:"), gbc);
            

            gbc.gridx = 1;
            JComboBox newBanatRelation = new JComboBox(szamok);
            panel.add(newBanatRelation, gbc);
            relation.add((int) newBanatRelation.getSelectedItem());

            gbc.gridx = 0;
            gbc.gridy = 15;
            panel.add(new JLabel("Harag reakciójának érzelmi hatása:"), gbc);

            gbc.gridx = 1;
            JComboBox newHaragMood = new JComboBox(szamok);
            panel.add(newHaragMood, gbc);
            mood.add((int) newHaragMood.getSelectedItem());

            gbc.gridx = 0;
            gbc.gridy = 16;
            panel.add(new JLabel("Harag reakciójának kapcsolati hatása:"), gbc);

            gbc.gridx = 1;
            JComboBox newHaragRelation = new JComboBox(szamok);
            panel.add(newHaragRelation, gbc);
            relation.add((int) newHaragRelation.getSelectedItem());
            
            List<String> leir = new ArrayList<>();
            gbc.gridx = 0;
            gbc.gridy = 17;
            panel.add(new JLabel("Undor leírása:"), gbc);

            gbc.gridx = 1;
            JTextField descUndor = new JTextField(20);
            panel.add(descUndor, gbc);
            leir.add(descUndor.getText());

            gbc.gridx = 0;
            gbc.gridy = 18;
            panel.add(new JLabel("Majré leírása:"), gbc);

            gbc.gridx = 1;
            JTextField descMajre = new JTextField(20);
            panel.add(descMajre, gbc);
            leir.add(descMajre.getText());

            gbc.gridx = 0;
            gbc.gridy = 19;
            panel.add(new JLabel("Derű leírása:"), gbc);

            gbc.gridx = 1;
            JTextField descDery = new JTextField(20);
            panel.add(descDery, gbc);
            leir.add(descDery.getText());

            gbc.gridx = 0;
            gbc.gridy = 20;
            panel.add(new JLabel("Bánat leírása:"), gbc);

            gbc.gridx = 1;
            JTextField descBanat = new JTextField(20);
            panel.add(descBanat, gbc);
            leir.add(descBanat.getText());

            gbc.gridx = 0;
            gbc.gridy = 21;
            panel.add(new JLabel("Harag leírása:"), gbc);

            gbc.gridx = 1;
            JTextField descHarag = new JTextField(20);
            panel.add(descHarag, gbc);
            leir.add(descHarag.getText());
            newDesc.setEditable(true);
            newUnd.setEditable(true);
            newMaj.setEditable(true);
            newDer.setEditable(true);
            newBanat.setEditable(true);
            newHarag.setEditable(true);
            descUndor.setEditable(true);
            descMajre.setEditable(true);
            descDery.setEditable(true);
            descBanat.setEditable(true);
            descHarag.setEditable(true);

            
            JMenuBar menu = new JMenuBar();
            JMenu file = new JMenu("File");
            JMenuItem save = new JMenuItem("Save");
            JMenuItem exit = new JMenuItem("Exit");
            save.addActionListener(e -> {
        	    reactions.clear();
        	    reactions.add(newUnd.getText());
        	    reactions.add(newMaj.getText());
        	    reactions.add(newDer.getText());
        	    reactions.add(newBanat.getText());
        	    reactions.add(newHarag.getText());

        	    mood.clear();
        	    mood.add((int) newUndmood.getSelectedItem());
        	    mood.add((int) newMajMood.getSelectedItem());
        	    mood.add((int) newDerMood.getSelectedItem());
        	    mood.add((int) newBanatMood.getSelectedItem());
        	    mood.add((int) newHaragMood.getSelectedItem());

        	    relation.clear();
        	    relation.add((int) newUndRelation.getSelectedItem());
        	    relation.add((int) newMajRelation.getSelectedItem());
        	    relation.add((int) newDerRelation.getSelectedItem());
        	    relation.add((int) newBanatRelation.getSelectedItem());
        	    relation.add((int) newHaragRelation.getSelectedItem());

        	    leir.clear();
        	    leir.add(descUndor.getText());
        	    leir.add(descMajre.getText());
        	    leir.add(descDery.getText());
        	    leir.add(descBanat.getText());
        	    leir.add(descHarag.getText());

                int index = events.indexOf(event);
                if (index != -1) {
                	event.setName(newName.getText());
	                event.setDescription(newDesc.getText());
	                event.setOptions(new ArrayList<>(reactions));
	                event.setMoodImpacts(new ArrayList<>(mood));
	                event.setSocialImpacts(new ArrayList<>(relation));
	                event.setConse(new ArrayList<>(leir));
                    events.set(index, event);
                } else {
                	event.setName(newName.getText());
	                event.setDescription(newDesc.getText());
	                event.setOptions(new ArrayList<>(reactions));
	                event.setMoodImpacts(new ArrayList<>(mood));
	                event.setSocialImpacts(new ArrayList<>(relation));
	                event.setConse(new ArrayList<>(leir));
                    events.add(event);
                }
                System.out.println(event.toString());
                sheetEvent.dispose();
                new GameUI(events, endings, results);
            });
			
            
            exit.addActionListener(e->{
            	sheetEvent.dispose();
            	new GameUI(events, endings, results);
            });
            file.add(save);
            file.add(exit);
            menu.add(file);
            
            sheetEvent.setJMenuBar(menu);
            
            
            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            sheetEvent.add(scrollPane);
            sheetEvent.setVisible(true);

		}
		private void sheetEnding(Event ending) {
			ending.setEnding(true);
			sheetEvent.setTitle("Befejezés kiválasztása");
	        sheetEvent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        sheetEvent.setSize(400, 300);
            
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 40, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Cím:"), gbc);
            
            gbc.gridx = 1;
            JTextField newName = new JTextField(20);
            newName.setEditable(true);
            panel.add(newName, gbc);
            
            
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(new JLabel("Leírás:"), gbc);
            
            gbc.gridx = 1;
            JTextField newDesc = new JTextField(20);
            newName.setEditable(true);
            panel.add(newDesc, gbc);
            
            JMenuBar menu = new JMenuBar();
            JMenu file = new JMenu("File");
            JMenuItem save = new JMenuItem("Save");
            JMenuItem exit = new JMenuItem("Exit");
            save.addActionListener(e->{

                int index = endings.indexOf(ending);
                if (index != -1) {
                	ending.setName(newName.getText());
	                ending.setDescription(newDesc.getText());
                    endings.set(index, ending);
                } else {
                	ending.setName(newName.getText());
	                ending.setDescription(newDesc.getText());
                    endings.add(ending);
                }
                sheetEvent.dispose();
                new GameUI(events, endings, results);
            });
            exit.addActionListener(e->{
            	sheetEvent.dispose();
            	new GameUI(events, endings, results);
            });
            file.add(save);
            file.add(exit);
            menu.add(file);
            
            sheetEvent.setJMenuBar(menu);
            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            sheetEvent.add(scrollPane);
            sheetEvent.setVisible(true);
		}
	}
	
	class Results extends JFrame{
		JFrame f = new JFrame("Eredmények");
		DefaultListModel<GameState> listModel = new DefaultListModel<>();{
		for (GameState result : results) {
		    listModel.addElement(result);
		}
		JList<GameState> list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(10);
		JButton back = new JButton("Vissza");
		back.addActionListener(e ->{
			f.dispose();
			new GameUI(events, endings, results);
		});
		JScrollPane scrollPane = new JScrollPane(list);
		f.add(scrollPane);
		f.add(back, BorderLayout.SOUTH);
		f.setSize(400, 300);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		}
	}
}
