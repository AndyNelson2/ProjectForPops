package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GridBagConstraints;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import backend.Issue;
import backend.Project;

public class ProjectDetailsWindow implements ActionListener{
	
	JDialog frame; 
	ProjectOverview po;
	Project project; 
	JScrollPane jsp; 
	int week; 
	
	ProjectDetailsWindow(ProjectOverview po, Project p, int week)
	{
		this.po = po; 
		this.project = p; 
		this.week = week; 
		frame = new JDialog(); 
		frame.setSize(new Dimension(500,500)); 
		frame.setLayout(null);
		frame.setModalityType(JDialog.DEFAULT_MODALITY_TYPE); 
		
		JPanel namePanel = new JPanel(new GridBagLayout()); 
		JPanel headerPanel = new JPanel(null); 
		JPanel footerPanel = new JPanel(new BorderLayout()); 
		JPanel detailsPanel; 
		
		JButton addIssue = new JButton("add"); 
		addIssue.addActionListener(this);
		addIssue.setFocusable(false);
		updateWindow(false);  
		
		//=======================================================================
		
		namePanel.setBounds(0,0,500,75); 
		
		JLabel nameLabel = new JLabel(p.getProjectName() + " (ID: " + p.getID() + ") : Week " + week + " Issues"); 
		nameLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		namePanel.add(nameLabel); 
		//=======================================================================
		
		//=======================================================================
		
		headerPanel.setBounds(0,75,500,25); 
		
		JButton issueNumber, weekCreated, weeksOngoing, priority; 
		
		issueNumber = new JButton(new AbstractAction("Issue #")
				{

					@Override
					public void actionPerformed(ActionEvent e) {

						project.sortByIssueNumber(); 
						updateWindow(true); 
					} 
		
				});
		
		weekCreated = new JButton(new AbstractAction("Week Created")
		{

			@Override
			public void actionPerformed(ActionEvent e) {

				project.sortByWeekCreated(); 
				updateWindow(true); 
			} 

		});
		
		weeksOngoing = new JButton(new AbstractAction("Weeks Ongoing")
		{

			@Override
			public void actionPerformed(ActionEvent e) {

				project.sortByWeekCreated(); 
				updateWindow(true); 
			} 

		});
		
		priority = new JButton(new AbstractAction("Priority")
		{

			@Override
			public void actionPerformed(ActionEvent e) {

				project.sortByPriority(); 
				updateWindow(true); 
			} 

		});
		
		issueNumber.setFont(new Font("Calibri", Font.BOLD, 13)); 
		issueNumber.setFocusPainted(false);
		issueNumber.setMargin(new Insets(0, 0, 0, 0));
		issueNumber.setContentAreaFilled(false);
		issueNumber.setBorderPainted(false);
		issueNumber.setOpaque(false);
		
		weekCreated.setFont(new Font("Calibri", Font.BOLD, 13)); 
		weekCreated.setFocusPainted(false);
		weekCreated.setMargin(new Insets(0, 0, 0, 0));
		weekCreated.setContentAreaFilled(false);
		weekCreated.setBorderPainted(false);
		weekCreated.setOpaque(false);
		
		weeksOngoing.setFont(new Font("Calibri", Font.BOLD, 13));           //header panel set up
		weeksOngoing.setFocusPainted(false);
		weeksOngoing.setMargin(new Insets(0, 0, 0, 0));
		weeksOngoing.setContentAreaFilled(false);
		weeksOngoing.setBorderPainted(false);
		weeksOngoing.setOpaque(false);
		
		priority.setFont(new Font("Calibri", Font.BOLD, 13)); 
		priority.setFocusPainted(false);
		priority.setMargin(new Insets(0, 0, 0, 0));
		priority.setContentAreaFilled(false);
		priority.setBorderPainted(false);
		priority.setOpaque(false);
		
		addIssue.setFont(new Font("Calibri", Font.ITALIC, 13)); 
		
		issueNumber.setBounds(-15, 0, 100, 25); 
		weekCreated.setBounds(65, 0, 100, 25);
		weeksOngoing.setBounds(165, 0, 100, 25);
		priority.setBounds(250, 0, 100, 25);
		addIssue.setBounds(385, 0, 60, 25); 
		headerPanel.add(issueNumber); 
		headerPanel.add(weekCreated); 
		headerPanel.add(weeksOngoing); 
		headerPanel.add(priority); 
		headerPanel.add(addIssue); 
		
		//=======================================================================

		//=======================================================================
		
		footerPanel.setBounds(0,425, 485, 25); 
		JLabel managerName = new JLabel("Project Manager: " + p.getProjectManager()); 
		footerPanel.add(managerName, BorderLayout.WEST); 
		//=======================================================================
		
		frame.add(headerPanel); 
		frame.add(namePanel); 
		frame.add(footerPanel); 
		frame.setResizable(false); 
		frame.setVisible(true); 
	}

	public void updateWindow(boolean removeJSP)
	{
		if(removeJSP)
			frame.remove(jsp);
		ArrayList<Issue> i = project.getWeekIssueArray(week); 
		
		int loops = 8; 
		JPanel detailsPanel; 
		detailsPanel = new JPanel(new GridBagLayout()); 
		GridBagConstraints gbc = new GridBagConstraints(); 

		gbc.weightx = 1; 
		gbc.weighty = 1; 
		
		//creates hidden row of jlabels. For some reason it helps with formatting. 
		for(int x = 0; x < 8; x++)
		{
			gbc.gridx = x; 
			gbc.gridy = Math.max(i.size(), loops) + 1; 
			detailsPanel.add(new JLabel(""), gbc); 
		}
		JLabel label; 
		for(int y = 0; y < Math.max(i.size(), loops); y++)
		{
			for(int x = 0; x < 5; x++)
			{
				gbc.gridx = x*2-1; 
				gbc.gridy = y; 
				gbc.gridwidth = 2; 
				if(i.size() < loops && y < i.size())
				{
					switch(x)
					{
					case 0: 
						label = new JLabel(Integer.toString(i.get(y).getID())); 
						gbc.gridx = x; 
						gbc.gridy = y; 
						gbc.gridwidth = 1; 
						detailsPanel.add(label, gbc); 
						break; 
					case 1: 
						label = new JLabel(Integer.toString(i.get(y).getWeekCreated()));  
						detailsPanel.add(label, gbc); 
						break; 
					case 2: 
						label = new JLabel(Integer.toString((week - i.get(y).getWeekCreated())));  
						detailsPanel.add(label, gbc); 
						break; 
					case 3: 
						label = new JLabel(Integer.toString(i.get(y).getPriority()));  
						detailsPanel.add(label, gbc); 
						break; 
					case 4: 
						MoreDetailsButton moreInfo = new MoreDetailsButton(po, i.get(y));  
						moreInfo.addActionListener(moreInfo); 
						gbc.gridwidth = 1; 
						detailsPanel.add(moreInfo, gbc); 
						break; 
					default: 
						System.out.println("huh");
					}
				}
				else
				{
					detailsPanel.add(new JLabel(""), gbc);
				}
			}
		}
		
		jsp = new JScrollPane(
				detailsPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
				); 

		jsp.setBounds(0,100,485,325); 
		frame.add(jsp); 
		
		if(removeJSP)
		{
			frame.repaint();
			frame.validate();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		new CreateIssueWindow(po, project, week); 
		updateWindow(true); 
		po.updateWindow(true); 
	}
}
