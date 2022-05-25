package ui;

import javax.swing.JButton;

import backend.Project;
import backend.ProjectManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class MainButton extends JButton implements ActionListener{

	private Project project; 
	private int buttonWeek; 
	private ProjectOverview po; 
	MainButton(ProjectOverview po, Project p, int week)
	{
		this.po = po; 
		this.project = p; 
		this.buttonWeek = week; 
		this.setText(Integer.toString(p.getWeeksIssues(week)));
	}
	
	MainButton(ProjectOverview po, Project p, boolean consecORtotal)
	{

		this.po = po; 
		this.project = p; 
		this.buttonWeek = po.manager.getCurrentWeek(); 
		if(consecORtotal)
			this.setText(Integer.toString(p.getConsecutiveWeeksWithIssue(buttonWeek)));
		else
			this.setText(Integer.toString(p.getIssues().size()));
	}
	
	MainButton()
	{
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this)		
		{
			System.out.println("It worked!"); 
			new ProjectDetailsWindow(po, project, buttonWeek); 
		}
	}
}
