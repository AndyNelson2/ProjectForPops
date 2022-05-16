package ui;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class MainButton extends JButton implements ActionListener{

	private int numErrors; 
	
	MainButton(String name)
	{
		this.setText(name);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this)		
		{
			System.out.println("It worked!"); 
		}
	}
}
