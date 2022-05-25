package ui;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import backend.Issue;
import backend.Project;
import backend.ProjectManager;

public class CreateIssueWindow extends JDialog implements ActionListener{

	JTextArea descriptionText, statusText; 
	JComboBox weekCreatedBox, priorityBox; 
	
	ProjectOverview po;
	Project p; 
	CreateIssueWindow(ProjectOverview po, Project p, int w)
	{
		this.po = po; 
		this.p = p; 
		setSize(new Dimension(600, 300));
		setModalityType(JDialog.DEFAULT_MODALITY_TYPE); 
		setLayout(null); 
		setResizable(false); 
		JLabel description, status, weekCreated, priority; 

		JButton save; 
		
		description = new JLabel("Description"); 
		status = new JLabel("Status"); 
		weekCreated = new JLabel("Week Created"); 
		priority = new JLabel("Priority"); 
		
		description.setBounds(15, 0, 100, 25); 
		status.setBounds(15, 100, 100, 25); 
		weekCreated.setBounds(15, 200, 100, 25); 
		priority.setBounds(130, 200, 100, 25); 
		
		descriptionText = new JTextArea(); 
		descriptionText.setEditable(true);
		descriptionText.setWrapStyleWord(true);
		descriptionText.setLineWrap(true);
		descriptionText.setBorder(BorderFactory.createLineBorder(Color.black));
		
		statusText = new JTextArea(); 
		statusText.setEditable(true);
		statusText.setWrapStyleWord(true);
		statusText.setLineWrap(true);
		statusText.setBorder(BorderFactory.createLineBorder(Color.black));
		
		descriptionText.setBounds(150, 10, 425, 90); 
		statusText.setBounds(150, 110, 425, 75); 
		
		Integer[] weeks = new Integer[po.manager.getCurrentWeek()]; 
		Integer[] priorities = {1, 2, 3}; 
		
		for(int x = 0; x < po.manager.getCurrentWeek(); x++)
		{
			weeks[x] = x+1; 
		}
		
		weekCreatedBox = new JComboBox(weeks); 
		priorityBox = new JComboBox(priorities); 
		
		weekCreatedBox.setEditable(false);
		priorityBox.setEditable(false);
		weekCreatedBox.setSelectedIndex(weekCreatedBox.getItemCount()-1);
		weekCreatedBox.setBounds(15, 225, 75, 25); 
		priorityBox.setBounds(130, 225, 50, 25); 
		
		save = new JButton("Save");
		save.setFocusable(false); 
		save.addActionListener(this);
		save.setBounds(475, 225, 100, 25); 
		
		add(description); 
		add(status); 
		add(weekCreated); 
		add(priority); 
		add(descriptionText); 
		add(statusText); 
		add(weekCreatedBox); 
		add(priorityBox); 
		add(save); 
		
		
		setVisible(true); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		p.addNewIssue(descriptionText.getText(), statusText.getText(), (int) weekCreatedBox.getSelectedItem(), po.manager.totalIssues()+1, (int) priorityBox.getSelectedItem());
		dispose(); 
		
	}
	
}
