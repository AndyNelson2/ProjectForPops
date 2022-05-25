package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color; 

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import backend.Project;

public class ProjectLookup extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea idArea; 
	ProjectOverview projectO; 
	
	ProjectLookup(ProjectOverview po)
	{
		projectO = po; 
		setSize(250, 125); 
		setModalityType(JDialog.DEFAULT_MODALITY_TYPE); 
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null); 
		
		JLabel projectID = new JLabel("Project ID"); 
		projectID.setBounds(15, 15, 100, 25); 
		
		idArea = new JTextArea(); 
		idArea.setEditable(true); 
		idArea.setBounds(75, 18, 150, 20); 
		idArea.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JButton searchButton = new JButton("search"); 
		searchButton.addActionListener(this);
		searchButton.setBounds(125, 50, 100, 25); 
		
		add(idArea); 
		add(searchButton); 
		add(projectID); 
		setResizable(false); 
		setVisible(true); 
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		boolean found = false; 
		for(Project p : projectO.manager.getProjects())
		{
			if(p.getID() == Integer.parseInt(idArea.getText()))
			{
				new ProjectDetailsWindow(projectO, p, projectO.manager.getCurrentWeek()); 
				found = true; 
			}
		}
		
		if(!found)
		{
			JOptionPane.showMessageDialog(null,  "Project " + Integer.parseInt(idArea.getText()) + " not found", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
