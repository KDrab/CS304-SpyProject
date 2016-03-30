package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.Database;

public class AssassinationDialog extends JDialog {
	
	public Database database; 
	public JFrame frame;
	public JPanel panel;
	public GridBagConstraints cs;
	public JTable playerList;
	public TableView playerListModel;
	public int appWidth = 520;
	public int appHeight = 480;
	public boolean isAdmin = false;
	
	public AssassinationDialog(JFrame parent, Database database, int charID) {
		   super(parent, "Assassination", true);
		    
	       frame = new JFrame("Choose your Victim!");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       frame.setSize(appWidth, appHeight);
	       frame.setLayout(new FlowLayout());
	       frame.setVisible(true);
	       
	       panel = new JPanel(new GridBagLayout());
	       cs = new GridBagConstraints();
	       frame.add(panel);
	        
	       this.displayEnemyList(charID, 0, 1);	   
	}
	
	public void displayEnemyList(int charID, int x, int y) {
    	ArrayList<String> enemies = database.getEnemiesList(charID);
    	TableView enemyListModel = new TableView(new String[]{"ID", "Name", "Level"}, 0);
    	
    	JTable enemyList = new JTable(enemyListModel);
    	
    	for (int i = 0; i < enemies.size(); i = i + 3) {
    		String[] toAdd = new String[3];
    		toAdd[0] = enemies.get(i);
    		toAdd[1] = enemies.get(i+1);
    		toAdd[2] = enemies.get(i+2);
    		enemyListModel.addRow(toAdd);
    	}
    	
    	cs.gridx = x;
    	cs.gridy = y;
    	JScrollPane pane = new JScrollPane(enemyList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
    													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	pane.setPreferredSize(new Dimension(appWidth/2, Math.min(20 + enemies.size() * 16 / 3, 132)));
    	panel.add(pane, cs);
    	frame.setVisible(true);
    	
    	MouseListener tableMouseListener = new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			int row = enemyList.getSelectedRow();
    			int col = 0;
    			// selected = charID to assassinate
    			int selected = Integer.parseInt(enemyList.getModel().getValueAt(row, col).toString().trim());
    			
    			frame.repaint();
    		}
    	};
    	
    	enemyList.addMouseListener(tableMouseListener);
    }
}
