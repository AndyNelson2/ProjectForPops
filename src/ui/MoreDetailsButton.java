package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import backend.Issue;

public class MoreDetailsButton extends JButton implements ActionListener {

	Issue issue; 
	ProjectOverview po; 
	MoreDetailsButton(ProjectOverview po, Issue i)
	{
		this.issue = i; 
		this.po = po; 
		this.setText("More info");
		this.setFocusable(false); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		new IssueDetailsWindow(po, issue); 
		
	}

}
