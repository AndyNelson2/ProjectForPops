package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Component; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import backend.ProjectManager;
import backend.Project; 
import backend.Issue;

public class ProjectOverview {

	ArrayList<JButton> buttons; 
	
	public ProjectOverview(ProjectManager pm)
	{
		buttons = new ArrayList<JButton>(); 
		JFrame frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setSize(new Dimension(JFrame.MAXIMIZED_VERT, JFrame.MAXIMIZED_HORIZ));
		frame.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("C:\\Users\\them4\\eclipse-workspace\\Better-Spreadsheet\\src\\ui\\images\\Untitled_smaller.png"); 
		JButton newWeek = new JButton("Add Week"); 
		JButton newProject = new JButton("Add Project"); 
		
		JPanel topPanel = new JPanel(); 
		JPanel gridPanel; 
		
		int gridPanelX = 0; 
		int gridPanelY = 0; 
		
		//Sets the grid size. Minimum size is 51x51 so that the buttons aren't too big. 
		//If that isn't large enough for the number of projects or weeks then it will expand. 
		if(pm.getCurrentWeek() < 51 && pm.getProjects().size() < 51)
		{
			gridPanel = new JPanel(new GridLayout(51,51)); 
			gridPanelX = 51; 
			gridPanelY = 51; 
		}
		else
		{
			if(pm.getCurrentWeek() > 50)
			{
				if(pm.getProjects().size() > 50)
				{
					gridPanel = new JPanel(new GridLayout(pm.getCurrentWeek()+1, pm.getProjects().size()+1));
					gridPanelX = pm.getCurrentWeek() + 1; 
					gridPanelY = pm.getProjects().size() + 1; 
				}
				else
				{
					gridPanel = new JPanel(new GridLayout(pm.getCurrentWeek()+1, 51));
					gridPanelX = pm.getCurrentWeek() + 1; 
					gridPanelY = 51; 
				}
			}
			else
			{
				if(pm.getProjects().size() > 50)
				{
					gridPanel = new JPanel(new GridLayout(51, pm.getProjects().size()+1));
					gridPanelX = 51; 
					gridPanelY = pm.getProjects().size() + 1;  
				}
				else
				{
					System.out.println("We shouldn't get here: ProjectOverview line 74");
					gridPanel = new JPanel(new GridLayout(51,51)); 
					gridPanelX = 51; 
					gridPanelY = 51; 
				}
			}
		}
		
		
		topPanel.setBackground(Color.blue);
		topPanel.add(newWeek, BorderLayout.EAST);
		topPanel.add(newProject, BorderLayout.WEST); 
		topPanel.setPreferredSize(new Dimension(200, 50));
		
		for(int y = 0; y < gridPanelY; y++)
		{
			if(y!=0)
			{
				if(y < pm.getProjects().size()+1)
					gridPanel.add(new JLabel(Integer.toString(pm.getProjects().get(y-1).getID()), SwingConstants.CENTER));
				else
					gridPanel.add(new JLabel());
			}
			else
				gridPanel.add(new JLabel("Project ID", SwingConstants.CENTER));
			for(int x = 0; x < gridPanelX-1; x++)
			{
				if(y == 0)
				{
					if(x+1 <= pm.getCurrentWeek())
						gridPanel.add(new JLabel("Week " + (x+1), SwingConstants.CENTER));
					else
						gridPanel.add(new JLabel());
				}
				else
				{
					MainButton temp; 
					if(x < pm.getCurrentWeek() && y <= pm.getProjects().size())
					{
						temp = new MainButton(Integer.toString(pm.getProjects().get(y-1).getWeeksIssues(x+1))); 
						temp.addActionListener(temp);
					}
					else
					{
						temp = new MainButton(""); 
					}
					temp.setFocusable(false); 
					buttons.add(temp); 
					gridPanel.add(temp); 
				}
			}
		}
		JScrollPane jsp = new JScrollPane(
				gridPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS); 
				
		
		frame.setIconImage(icon.getImage()); 
		frame.setTitle("Project Manager"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(500,300)); 
		frame.setVisible(true); 
		
		//buttons.get(399).setText("500");
		frame.add(topPanel, BorderLayout.NORTH); 
		frame.add(jsp, BorderLayout.CENTER); 
	}
	
}

