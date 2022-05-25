package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class CreateProjectWindow extends JDialog implements ActionListener{
	
	String name; 
	String manager; 
	String desc; 
	
	JTextArea nameTextArea, managerTextArea, descriptionTextArea; 
	CreateProjectWindow()
	{
		setSize(new Dimension(400,400)); 
		setModalityType(JDialog.DEFAULT_MODALITY_TYPE); 
		setLayout(null); 
		setResizable(false); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		JLabel nameLabel, managerLabel, descriptionLabel; 
		JButton save; 
		
		nameLabel = new JLabel("Project Name"); 
		managerLabel = new JLabel("Manager Name"); 
		descriptionLabel = new JLabel("Description"); 
		
		nameLabel.setBounds(15, 25, 100, 25); 
		managerLabel.setBounds(15, 75, 100, 25); 
		descriptionLabel.setBounds(15, 125, 100, 25); 
		
		nameTextArea = new JTextArea(); 
		managerTextArea = new JTextArea(); 
		descriptionTextArea = new JTextArea(); 
		
		nameTextArea.setEditable(true);
		nameTextArea.setWrapStyleWord(true);
		nameTextArea.setLineWrap(false);
		nameTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		
		managerTextArea.setBounds(125, 100, 250, 25); 
		managerTextArea.setEditable(true);
		managerTextArea.setWrapStyleWord(true);
		managerTextArea.setLineWrap(true);
		managerTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		
		descriptionTextArea.setEditable(true);
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JScrollPane nameJSP = new JScrollPane(
				nameTextArea, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
				); 
		nameJSP.setBounds(125, 25, 250, 50);
		
		JScrollPane descJSP = new JScrollPane(
				descriptionTextArea, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
				); 
		descJSP.setBounds(125, 150, 250, 200); 
		
		save = new JButton("Save"); 
		save.setBounds(15, 325, 100, 25); 
		save.addActionListener(this);
		
		add(save); 
		add(nameJSP); 
		add(managerTextArea); 
		add(descJSP); 
		add(nameLabel); 
		add(managerLabel); 
		add(descriptionLabel); 
		
		setVisible(true); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		name = nameTextArea.getText(); 
		manager = managerTextArea.getText(); 
		desc = descriptionTextArea.getText(); 
		dispose(); 
		System.out.println(name + " " + desc + " " + manager);
	}
}
