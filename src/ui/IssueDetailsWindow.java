package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import backend.Issue;

public class IssueDetailsWindow {

	JDialog frame; 
	JButton resolveButton;
	JLabel weekResolvedText; 
	Issue issue; 
	ProjectOverview po; 
	IssueDetailsWindow(ProjectOverview po, Issue i)
	{
		issue = i; 
		this.po = po; 
		frame = new JDialog(); 
		frame.setSize(new Dimension(500,500)); 
		frame.setLayout(null);
		frame.setModalityType(JDialog.DEFAULT_MODALITY_TYPE); 
		JLabel heading, issueDescription, issueStatus, weekCreated, weekCreatedText, 
		       priority, priorityText, weekResolved; 
		
		JTextArea issueDescriptionText = new JTextArea(i.getDescription()); 
		JTextArea issueStatusText = new JTextArea(i.getStatus()); 

		issueDescription = new JLabel("Description"); 
		issueStatus = new JLabel("Status"); 
		weekCreated = new JLabel("Week Created"); 
		priority = new JLabel("Priority");
		weekResolved = new JLabel("Week Resolved"); 
		
		heading = new JLabel("Issue " + i.getID() + " (" + po.manager.getProjectforIssue(i.getID()).getProjectName() + ")"); 
		weekCreatedText = new JLabel(Integer.toString(i.getWeekCreated()));
		priorityText = new JLabel(Integer.toString(i.getPriority())); 
		weekResolvedText = new JLabel(); 
		
		if(i.getWeekResolved() > 0)
		{
			weekResolvedText.setText(Integer.toString(i.getWeekResolved()));
		}
		else
		{
			weekResolvedText.setText("Has not been resolved");
		}
		
		//headingPanel
		JPanel headingPanel = new JPanel(new GridBagLayout());  
		headingPanel.setBounds(0,0,500,50); 
		heading.setFont(new Font("Calibri", Font.BOLD, 20));
		headingPanel.add(heading); 
		
		//issueDescription
		issueDescription.setBounds(15,50, 100, 25); 
		
		//sets up text areas
		issueDescriptionText.setWrapStyleWord(true);
		issueDescriptionText.setLineWrap(true);
		issueDescriptionText.setEditable(false); 

		issueStatusText.setWrapStyleWord(true);
		issueStatusText.setLineWrap(true);
		issueStatusText.setEditable(false); 
		
		//puts description text into scroll pane
		JScrollPane descriptionJSP = new JScrollPane(
				issueDescriptionText, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
				);
		
		descriptionJSP.setBounds(150, 50, 325, 150); 
		
		//puts issue status into scroll pane
		issueStatus.setBounds(15, 225, 100, 25); 
		
		JScrollPane statusJSP = new JScrollPane(
				issueStatusText, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
				); 
		
		statusJSP.setBounds(150, 225, 325, 75); 
		
		//weekCreated section
		weekCreated.setBounds(15, 325, 100, 25); 
		weekCreatedText.setBounds(150, 325, 325, 25); 
		
		//Priority section
		priority.setBounds(15, 375, 100, 25); 
		priorityText.setBounds(150, 375, 325, 25); 
		
		//Week Resolved Section
		weekResolved.setBounds(15, 425, 100, 25); 
		weekResolvedText.setBounds(150, 425, 200, 25); 	
		
		//setting up buttons===================================================
		
		//description save button. Stops text area from being edited. Saves new text to data base. 
		JButton descSaveButton = new JButton(new AbstractAction("save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				issueDescriptionText.setEditable(false);
				String text = issueDescriptionText.getText(); 
				
				try {
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanager", "root", "be4Panda");
					String sql = "UPDATE issue SET i_description = ? WHERE i_id = ?"; 
					PreparedStatement statement = connection.prepareStatement(sql); 
					statement.setString(1, text); 
					statement.setInt(2,  i.getID()); 
					
					statement.executeUpdate(); 
					i.setDescription(text); 
					connection.close(); 
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}); 
		descSaveButton.setBounds(15, 100, 100, 25); 
		//description edit button. Allows text area to be edited. 
		JButton descEditButton = new JButton(new AbstractAction("edit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				issueDescriptionText.setEditable(true);
				frame.add(descSaveButton); 
				frame.revalidate(); 
				frame.repaint(); 
			}
		}); 
		descEditButton.setBounds(15, 75, 100, 25); 

		JButton statusSaveButton = new JButton(new AbstractAction("save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				issueStatusText.setEditable(false);
				String text = issueStatusText.getText(); 
				
				try {
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanager", "root", "be4Panda");
					String sql = "UPDATE issue SET i_status = ? WHERE i_id = ?"; 
					PreparedStatement statement = connection.prepareStatement(sql); 
					statement.setString(1, text); 
					statement.setInt(2,  i.getID()); 
					
					statement.executeUpdate(); 
					i.setStatus(text); 
					connection.close(); 
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}); 
		statusSaveButton.setBounds(15, 275, 100, 25); 
		
		JButton statusEditButton = new JButton(new AbstractAction("edit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				issueStatusText.setEditable(true);
				frame.add(statusSaveButton); 
				frame.revalidate(); 
				frame.repaint(); 
			}
		}); 
		statusEditButton.setBounds(15, 250, 100, 25); 
		resolveButton = new JButton(new AbstractAction("resolve")
			{

				@Override
				public void actionPerformed(ActionEvent e) {
					//Sets the week resolved to the current week. 
					//Possibly switch so that the resolved week is set to the next week since the issue was
					//still persisting during the current week. 
					i.setWeekResolved(po.manager.getCurrentWeek()); 
					updateWeekResolved(); 
				}
			}); 
		resolveButton.setBounds(325, 425, 100, 25); 
		if(i.getWeekResolved() < 1)
			frame.add(resolveButton); 
		//adding all components to frame
		frame.add(statusEditButton); 
		frame.add(descEditButton); 
		frame.add(weekResolved); 
		frame.add(weekResolvedText); 
		frame.add(priorityText); 
		frame.add(priority); 
		frame.add(weekCreatedText); 
		frame.add(weekCreated);
		frame.add(statusJSP);
		frame.add(issueStatus);
		frame.add(descriptionJSP); 
		frame.add(issueDescription);
		frame.add(headingPanel); 
		frame.setResizable(false); 
		frame.setVisible(true); 
	}
	
	public void updateWeekResolved()
	{
		frame.remove(resolveButton);
		weekResolvedText.setText(Integer.toString(issue.getWeekResolved()));
		po.updateWindow(true);
		frame.repaint(); 
	}
}
