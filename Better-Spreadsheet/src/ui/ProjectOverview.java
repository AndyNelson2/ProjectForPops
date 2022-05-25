package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.FlowLayout; 
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Component; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
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
import backend.Queries;
import backend.Project; 
import backend.Issue;

public class ProjectOverview implements ActionListener{

	ArrayList<JButton> buttons; 
	ProjectManager manager; 
	JButton newWeek, newProject, searchProject, searchIssue, adminLogin; 
	JFrame frame; 
	JScrollPane jsp; 
	private boolean isAdmin; 
	Queries que; 
	
	public ProjectOverview(ProjectManager pm)
	{
		//Initial setup
		this.manager = pm; 
		buttons = new ArrayList<JButton>(); 
		frame = new JFrame();
		isAdmin = false; 
		que = new Queries(); 
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setSize(new Dimension(JFrame.MAXIMIZED_VERT, JFrame.MAXIMIZED_HORIZ));
		frame.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("C:\\Users\\them4\\eclipse-workspace\\Better-Spreadsheet\\src\\ui\\images\\Untitled_smaller.png");
		
		newWeek = new JButton("Add Week"); 
		newProject = new JButton("Add Project");
		searchProject = new JButton("Project Lookup"); 
		searchIssue = new JButton ("Issue Lookup"); 
		adminLogin = new JButton("Admin Login"); 
		
		JPanel topPanel = new JPanel(new FlowLayout()); 
		
		topPanel.setBackground(Color.blue);
		newWeek.addActionListener(this);
		topPanel.add(newWeek);
		newProject.addActionListener(this);
		topPanel.add(newProject); 
		searchProject.addActionListener(this);
		topPanel.add(searchProject); 
		searchIssue.addActionListener(this);
		topPanel.add(searchIssue); 
		adminLogin.addActionListener(this);
		topPanel.add(adminLogin); 
		topPanel.setPreferredSize(new Dimension(200, 50));
		
		
		updateWindow(false); 
		
		frame.setIconImage(icon.getImage()); 
		frame.setTitle("Project Manager"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(500,300)); 
		frame.setVisible(true); 
		
		frame.add(topPanel, BorderLayout.NORTH); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == newWeek)
		{
			manager.nextWeek();
			updateWindow(true); 
		}
		else if(e.getSource() == newProject)
		{
			CreateProjectWindow cpw = new CreateProjectWindow(); 
			if(cpw.name.length() > 0 && cpw.desc.length() > 0 && cpw.manager.length() > 0)
			{
				manager.addProject(cpw.name, cpw.manager, cpw.desc);
			}
			else
			{
				JOptionPane.showMessageDialog(null,  "Project not created. Each field (name, description, and manager) must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			updateWindow(true); 
		}
		else if(e.getSource() == searchProject)
		{
			new ProjectLookup(this); 
		}
		else if(e.getSource() == searchIssue)
		{
			new IssueLookup(this); 
		}
		else
		{
			LoginWindow admin = new LoginWindow(); 
			isAdmin = que.loginQuery(admin.username, admin.password);
			if(isAdmin)
			{
				updateWindow(true); 
			}
			else
			{
				JOptionPane.showMessageDialog(null,  "Login Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public void updateWindow(boolean remove)
	{
		if(remove)
			frame.remove(jsp);
		
		JPanel gridPanel; 
		
		int gridPanelX = 0; 
		int gridPanelY = 0; 
		
		//Sets the grid size. Minimum size is 51x51 so that the buttons aren't too big. 
		//If that isn't large enough for the number of projects or weeks then it will expand. 
		if(manager.getCurrentWeek() < 51 && manager.getProjects().size() < 51)
		{
			gridPanel = new JPanel(new GridLayout(51,51)); 
			gridPanelX = 51; 
			gridPanelY = 51; 
		}
		else
		{
			if(manager.getCurrentWeek() > 50) // if there are more than 50 weeks
			{
				if(manager.getProjects().size() > 50) //if there are more than 50 projects
				{ 
					gridPanel = new JPanel(new GridLayout(manager.getCurrentWeek()+1, manager.getProjects().size()+1));
					gridPanelX = manager.getCurrentWeek() + 1; 
					gridPanelY = manager.getProjects().size() + 1; 
				}
				else //less than 50 projects
				{
					gridPanel = new JPanel(new GridLayout(manager.getCurrentWeek()+1, 51));
					gridPanelX = manager.getCurrentWeek() + 1; 
					gridPanelY = 51; 
				}
			}
			else //less than 50 weeks
			{
				if(manager.getProjects().size() > 50) //more than 50 projects
				{
					gridPanel = new JPanel(new GridLayout(51, manager.getProjects().size()+1));
					gridPanelX = 51; 
					gridPanelY = manager.getProjects().size() + 1;  
				}
				else //less than 50 weeks and less than 50 projects. Shouldn't be reachable. 
				{
					System.out.println("We shouldn't get here: ProjectOverview line 163");
					gridPanel = new JPanel(new GridLayout(51,51)); 
					gridPanelX = 51; 
					gridPanelY = 51; 
				}
			}
		}
		
		for(int y = 0; y < gridPanelY; y++)
		{
			if(y!=0) 
			{
				if(y < manager.getProjects().size()+1)  //prints the project ID in first column. 
					gridPanel.add(new JLabel(Integer.toString(manager.getProjects().get(y-1).getID()), SwingConstants.CENTER));
				else
					gridPanel.add(new JLabel());
			}
			else
			{
				JButton temp = new JButton(new AbstractAction("Project ID")
						{
							@Override
							public void actionPerformed(ActionEvent e) {

								manager.sortByID();
								updateWindow(true); 
							}
						}); 
				temp.setFocusPainted(false);
				temp.setMargin(new Insets(0, 0, 0, 0));
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				temp.setOpaque(false);
				gridPanel.add(temp); //top left is Project ID label
			}
			for(int x = 0; x < gridPanelX-1; x++)
			{
				if(y == 0)
				{
					if(x+1 <= manager.getCurrentWeek())//puts 'week x' label up until current week in data base.
						gridPanel.add(new JLabel("Week " + (x+1), SwingConstants.CENTER));
					else if(x == (manager.getCurrentWeek()+1))
					{
						JButton temp = new JButton(new AbstractAction("Running Issues")
								{
									@Override
									public void actionPerformed(ActionEvent e) {

										manager.sortByRunningIssues();
										updateWindow(true); 
									}
								}); 
						temp.setFocusPainted(false);
						temp.setMargin(new Insets(0, 0, 0, 0));
						temp.setContentAreaFilled(false);
						temp.setBorderPainted(false);
						temp.setOpaque(false);
						gridPanel.add(temp); //top left is Project ID label
					}
					else if(x == (manager.getCurrentWeek()+2))
					{

						JButton temp = new JButton(new AbstractAction("Total Issues")
								{
									@Override
									public void actionPerformed(ActionEvent e) {

										manager.sortByTotalIssues();
										updateWindow(true); 
									}
								}); 
						temp.setFocusPainted(false);
						temp.setMargin(new Insets(0, 0, 0, 0));
						temp.setContentAreaFilled(false);
						temp.setBorderPainted(false);
						temp.setOpaque(false);
						gridPanel.add(temp); //top left is Project ID label
					}
					else
						gridPanel.add(new JLabel());
				}
				else
				{
					MainButton temp; 
					if(x < manager.getCurrentWeek() && y <= manager.getProjects().size())
					{
						temp = new MainButton(this, manager.getProjects().get(y-1), (x+1)); 
						temp.addActionListener(temp);
					}
					else if(x == (manager.getCurrentWeek()+1) && y <= manager.getProjects().size())
					{
						temp = new MainButton(this, manager.getProjects().get(y-1), true); 
						temp.addActionListener(temp);
					}
					else if(x == (manager.getCurrentWeek()+2) && y <= manager.getProjects().size())
					{
						temp = new MainButton(this, manager.getProjects().get(y-1), false); 
						temp.addActionListener(temp);
					}
					else
					{
						temp = new MainButton(); 
						temp.setFocusPainted(false);
						temp.setMargin(new Insets(0, 0, 0, 0));
						temp.setContentAreaFilled(false);
						temp.setBorderPainted(false);
						temp.setOpaque(false);
					}
					temp.setFocusable(false); 
					buttons.add(temp); 
					gridPanel.add(temp); 
				}
			}
		}
		
		jsp = new JScrollPane(
				gridPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
				); 

		frame.add(jsp, BorderLayout.CENTER); 
		
		if(remove)
		{
			frame.repaint(); 
			frame.validate(); 
		}
	}
	
}

