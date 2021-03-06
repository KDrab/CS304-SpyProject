package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.Object;
import java.lang.Math;
import java.awt.Color;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.Database;

public class Homepage extends JFrame {
	public Database database; 
	public static JFrame frame;
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public TableView playerListModel;
	public int appWidth = 640;
	public int appHeight = 480;
	public boolean isAdmin = false;

    public Homepage(String uname, Database db) {
        this.database = db;
        
        frame = new JFrame(uname + " - Homepage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(appWidth, appHeight);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        
        panel = new JPanel(new GridBagLayout());
        cs = new GridBagConstraints();
        frame.add(panel);
        
        this.displayLoginButton();
        this.displaySignupButton();
    }
    
    
    public void displayLoginButton(){
    	JButton loginButton = new JButton("login");
    	cs.gridx = 0;
    	cs.gridy = 0;
    	panel.add(loginButton);
    	loginButton.addActionListener(new ActionListener()
    	{
    	public void actionPerformed(ActionEvent e) {
    		LoginDialog ld = new LoginDialog(frame, database);
    		ld.setVisible(true);
    		if(ld.isSucceeded()){
    			if (ld.getIsAdmin()){
    				panel.removeAll();
        			displayLogoutButton();
        			displayWinnerButton();
        			displayLoserButton();
        			displayTeamList();
        			displayFindPlayerButton();
        			displayDeleteButton();
        			displaytopcharlist();
        			frame.repaint();
        			isAdmin = true;
        			return;
    			}
    			panel.removeAll();
    			displayLogoutButton();
    			displayWinnerButton();
    			displayLoserButton();
    			displayCharList(ld.getUsername(), 0 , 5);
    			frame.repaint();
    		}
    	}});
    	frame.setVisible(true);	
    	
    }
    
    public void displaySignupButton(){
    	JButton signupButton = new JButton("Sign Up");
    	cs.gridx = 1;
    	cs.gridy = 0;
    	panel.add(signupButton, cs);
    	signupButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		SignupDialog sd = new SignupDialog(frame, database);
    		sd.setVisible(true);
    	}});
    	frame.setVisible(true);	 	
    }
    
    public void displayLogoutButton(){
    	JButton logoutButton = new JButton("logout");
    	logoutButton.setLocation(200,150);
    	logoutButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		panel.removeAll();
    		isAdmin = false;
    		displayLoginButton();
    		displaySignupButton();
    	}});
    	cs.gridx = 0;
    	cs.gridy = 0;
    	panel.add(logoutButton, cs); 
    	frame.setVisible(true);
    }
    
    public void displayWinnerButton(){
    	JButton winnerButton = new JButton("Winning Team");
    	cs.gridx = 0;
    	cs.gridy = 1;
    	panel.add(winnerButton, cs);
    	winnerButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		JOptionPane.showMessageDialog(frame, "Winning Team: \nAverage Level & Team Name: \n" + database.getLeaderBoard(0), "Winning Team", JOptionPane.INFORMATION_MESSAGE);
    	}});
    	frame.setVisible(true);
    }
    
    public void displayLoserButton(){
    	JButton loserButton = new JButton("Losing Team");
    	cs.gridx = 0;
    	cs.gridy = 2;
    	panel.add(loserButton, cs);
    	loserButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
    		JOptionPane.showMessageDialog(frame, "Losing Team: \nAverage Level & Team Name: \n" + database.getLeaderBoard(1), "Losing Team", JOptionPane.INFORMATION_MESSAGE);
    	}});
    	frame.setVisible(true);
    }
    
    public void displayTeamList() {
    	ArrayList<String> teams = database.getTeams();
    	TableView teamListModel = new TableView(new String[]{"Teams"}, 0);
    	JTable teamList = new JTable(teamListModel);
    	for (String team : teams) {
    		teamListModel.addRow(new Object[] {team});
    	}
    	
        System.out.println("Top displayTeamList");
    	
    	cs.gridx = 0;
    	cs.gridy = 6;
    	JScrollPane pane = new JScrollPane(teamList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/3, Math.min(20 + teams.size() * 16, 132)));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
        System.out.println("Bottom displayTeamList");
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = teamList.getSelectedRow();
    			String selected = teams.get(row);
    			
                System.out.println("In MouseListener...");
    			
    			panel.removeAll();
    			displayLogoutButton();
    			displayWinnerButton();
    			displayLoserButton();
    			displayFindPlayerButton();
    			displayDeleteButton();
    			cs.gridx = 0;
    	    	cs.gridy = 10;
    			panel.add(pane, cs);
    			displaytopcharlist();
    			displayTeamStats(selected, 0, 11);
    			frame.pack();
    			frame.repaint();
    			
    		}
    	};
    	
    	teamList.addMouseListener(tableMouseListener);
    }
    
    public void displayTeamStats(String team, int x, int y) {
    	ArrayList<String> stats = database.getTeamStats(team);
    	TableView statListModel = new TableView(new String[]{"Name", "Size", "Avg. Level", "Total Cash"}, 0);
    	JTable statList = new JTable(statListModel);
    	
    	System.out.println("In displayTeamStats, querying done.");
    	
    	for (int i = 0; i < stats.size(); i = i + 4) {
    		System.out.println("In for...");
    		String[] toAdd = new String[4];
    		toAdd[0] = stats.get(i);
    		toAdd[1] = stats.get(i+1);
    		toAdd[2] = stats.get(i+2);
    		toAdd[3] = stats.get(i+3);
    		System.out.println("toAdd[] full.");
    		statListModel.addRow(toAdd);
    	}
    	
    	cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(statList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, 36));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    }
    
    public void displayPlayerList() {
    	ArrayList<String> players = database.getPlayerList();
    	playerListModel = new TableView(new String[]{"Players"}, 0);
    	playerList = new JTable(playerListModel);
    	for (String player : players) {
    		playerListModel.addRow(new Object[] {player});
    	}
    	cs.gridx = 0;
    	cs.gridy = 1;
    	JScrollPane pane = new JScrollPane(playerList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/3, Math.min(20 + players.size() * 16, 132)));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = playerList.getSelectedRow();
    			String selected = players.get(row);
    			
    			panel.removeAll();
    			displayLogoutButton();
    			cs.gridx = 0;
    	    	cs.gridy = 1;
    			panel.add(pane, cs);
    			displayCharList(selected, 0, 11);
    			frame.repaint();
    			
    		}
    	};
    	
    	playerList.addMouseListener(tableMouseListener);
    }
    
    public void displayCharList(String username, int x, int y) {
    	ArrayList<String> chars = database.getCharList(username);
    	TableView charListModel = new TableView(new String[]{"ID", "Name", "Level"}, 0);
    	
    	JTable charList = new JTable(charListModel);
    	
    	for (int i = 0; i < chars.size(); i = i + 3) {
    		String[] toAdd = new String[3];
    		toAdd[0] = chars.get(i);
    		toAdd[1] = chars.get(i+1);
    		toAdd[2] = chars.get(i+2);
    		charListModel.addRow(toAdd);
    	}
    	
    	cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(charList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, Math.min(20 + chars.size() * 16 / 3, 132)));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = charList.getSelectedRow();
    			int col = 0;
    			String selected = charList.getModel().getValueAt(row, col).toString().trim();
    			
    			// check type of player here
    			int type = database.checkPlayerType(selected);
    			System.out.println("Player type: " + type);
    			
    			panel.removeAll();
    			displayLogoutButton();
    			displayWinnerButton();
    			displayLoserButton();
    			if (isAdmin) {
    				displayPlayerList();
    			}
    			cs.gridx = 0;
    	    	cs.gridy = 10;
    			panel.add(pane, cs);
    			displayCharStats(selected, 0, 20);
    			
    			// display the correct buttons depending on type of character (changes the color also)
    			if (type == 1) {
    				// character is a spy
    				panel.setBackground(Color.red);
    				displayAssassinationButton(0, 12, selected);
    				displayInterceptButton(0, 13, selected);
    			} else if (type == 2) {
    				// character is a politician
    				panel.setBackground(Color.black);
    				displaySpeechButton(0, 12, selected);
    				displayCampaignButton(0, 13, selected);
    			} else if (type == 3) {
    				// character is a businessman
    				panel.setBackground(Color.blue);
    				displayFundPoliticianButton(0, 12);
    				displayPaySpyButton(0, 13, selected);
    			}
    			
    			frame.repaint();
    		}
    	};
    	
    	charList.addMouseListener(tableMouseListener);
    }
    
    public void displayCharStats(String id, int x, int y) {
    	ArrayList<String> values = database.getCharacterStats(id);
		System.out.println(values);
		
		TableView statListModel = new TableView(new String[]{"ID", "Name", "Level", "Cash", "Email", "Team"}, 0);
		JTable statList = new JTable(statListModel);
		    			
		String[] stats = new String[6];
		
		for (int i = 0; i < 6; i++) {
    		stats[i] = values.get(i).toString().trim();
    	}
		statListModel.addRow(stats);
		
		cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(statList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth, 36));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    }
    
    public void displayAssassinationButton(int x, int y, String charID){
    	JButton assassinateButton = new JButton("Assassinate");
//    	assassinateButton.setLocation(200,150);
    	assassinateButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
//    		panel.removeAll();
    		String name = "";
    		
//    		AssassinationDialog ad = new AssassinationDialog(frame, database, charID);
    		JOptionPane.showMessageDialog(frame, "Assassinate", "Assassinate", JOptionPane.INFORMATION_MESSAGE);
    		assassinateButton.setSelected(false);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(assassinateButton, cs); 
    	frame.setVisible(true);
    	
    }
    
    public void displayInterceptButton(int x, int y, String charID){
    	JButton interceptButton = new JButton("Intercept");
 //   	interceptButton.setLocation(200,150);
    	interceptButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
//    		panel.removeAll();
    		System.out.println("Intercept clicked!");
//    		InterceptDialog id = new InterceptDialog(frame, database, charID);
    		JOptionPane.showMessageDialog(frame, "Intercept Messages", "Intercept Transmissions", JOptionPane.INFORMATION_MESSAGE);
    		interceptButton.setSelected(false);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(interceptButton, cs); 
    	frame.setVisible(true);
    }
    
    public void displaySpeechButton(int x, int y, String charID) {
    	JButton speechButton = new JButton("Give Speech");
//    	speechButton.setLocation(200,150);
    	speechButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
//    		panel.removeAll();
    		System.out.println("Speech clicked!");
//    		SpeechDialog id = new SpeechDialog(frame, database, charID);
    		JOptionPane.showMessageDialog(frame, "Give A Speech", "Game Day", JOptionPane.INFORMATION_MESSAGE);
    		speechButton.setSelected(false);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(speechButton, cs); 
    	frame.setVisible(true);
    }
    
    public void displayCampaignButton(int x, int y, String charID) {
    	JButton campaignButton = new JButton("Campaign...");
//    	campaignButton.setLocation(200,150);
    	campaignButton.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
//    		panel.removeAll();
    		System.out.println("Campaign clicked!");
//    		CampaignDialog id = new CampaignDialog(frame, database, charID);
    		JOptionPane.showMessageDialog(frame, "Run A Campaign!", "Campaign Finance", JOptionPane.INFORMATION_MESSAGE);
    		campaignButton.setSelected(false);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(campaignButton, cs); 
    	frame.setVisible(true);
    }
    
    public void displayFundPoliticianButton(int x, int y) {
    	JButton fundPoliBtn = new JButton("Donate...");
    	fundPoliBtn.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
//    		panel.removeAll();
    		System.out.println("Donate clicked!");
    		FundPoliDialog id = new FundPoliDialog(frame, database);
//    		JOptionPane.showMessageDialog(frame, "Fund a Politician!", "Donate...", JOptionPane.INFORMATION_MESSAGE);
    		fundPoliBtn.setSelected(false);
    		id.setVisible(true);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(fundPoliBtn, cs); 
    	frame.setVisible(true);
    }

    public void displayPaySpyButton(int x, int y, String charID) {
    	JButton paySpyBtn = new JButton("Pay a Spy");
    	paySpyBtn.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e) {
//    		panel.removeAll();
    		System.out.println("Pay Spy clicked!");
//    		PaySpyDialog id = new PaySpyDialog(frame, database, charID);
    		JOptionPane.showConfirmDialog(frame, "Spy on someone...", "Spy Action", JOptionPane.INFORMATION_MESSAGE);
    		paySpyBtn.setSelected(false);
    	}});
    	cs.gridx = x;
    	cs.gridy = y;
    	panel.add(paySpyBtn, cs); 
    	frame.setVisible(true);
    }
    
    public void displayFindPlayerButton(){
    	JButton findPlayerButton = new JButton("Check Stats");
    	cs.gridx = 0;
    	cs.gridy = 4;
    	panel.add(findPlayerButton, cs);
    	
    	findPlayerButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			StatsDialog sd = new StatsDialog(frame, database);
    			sd.setVisible(true);
    			
    		}
    	});
    	frame.setVisible(true);
    	
    }
    public void displaytopcharlist() {
    	
    	ArrayList<String> chars = database.gettopchar();
    	TableView charListModel = new TableView(new String[]{"Top Players"}, 0);
    	
    	JTable charList = new JTable(charListModel);
    	
    	for (int i = 0; i < chars.size(); i++) {
    		String[] toAdd = new String[1];
    		toAdd[0] = chars.get(i);
    		charListModel.addRow(toAdd);
    	}
    	GridBagConstraints cs1 = new GridBagConstraints();
    	cs1.gridx = 0;
    	cs1.gridy = 30;
    	JScrollPane pane = new JScrollPane(charList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, Math.min(20 + chars.size() * 32, 132)));
    	panel.add(pane, cs1);
    	frame.setVisible(true);
    }
    
    public void displayDeleteButton(){
    	JButton deleteButton = new JButton("Delete Player");
    	cs.gridx = 0;
    	cs.gridy = 5;
    	panel.add(deleteButton, cs);
    	
    	deleteButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			DeleteDialog dd = new DeleteDialog(frame, database);
    			dd.setVisible(true);
    			
    		}
    	});
    	frame.setVisible(true);
    	
    }	
    
    public static void popup(){
		JOptionPane.showMessageDialog(frame, "NOT ENOUGH MONEY", "NOT ENOUGH MONEY", JOptionPane.INFORMATION_MESSAGE);

    }
}
