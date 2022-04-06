package forms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomeForm extends JFrame implements ActionListener {
	
	private JMenu menus[] = {new JMenu("Cours"), new JMenu("Etudiant")};
    private JMenuBar bar = new JMenuBar();
    private JMenuItem classMenuItems[] = {new JMenuItem("Liste"), new JMenuItem("Java"), new JMenuItem("JEE")};
    private JMenuItem studentMenuItems[] = {new JMenuItem("Nouveau"), new JMenuItem("Liste")};
    
    private Container container = getContentPane();
    private JLabel connectedUserNameLabel = new JLabel("");
   
    
    HomeForm(String _name) {
    	addItemsToMenu();
    	addActionEvent();
    	this.container.setLayout(null);
    	this.connectedUserNameLabel.setBounds(100, 140, 250, 30);
    	this.connectedUserNameLabel.setText("Espace de gestion de "  + _name);
    	this.container.add(this.connectedUserNameLabel);
    	this.setTitle("Gestion de cours");
    	this.setJMenuBar(bar);
        this.setBounds(250, 250, 400, 400);
        this.setLayout(null);  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
        this.setVisible(true);
    }
    
    private void addItemsToMenu() {
    	
    	for (JMenu menu : menus) {
			bar.add(menu);
		}
    	
    	for (JMenuItem item : classMenuItems) {
			menus[0].add(item);
		}
    	
    	for (JMenuItem item : studentMenuItems) {
			menus[1].add(item);
		}

    }
    
    public void addActionEvent() {
    	
    	for (JMenuItem item : classMenuItems) {
			item.addActionListener(this);
		}
    	
    	for (JMenuItem item : studentMenuItems) {
    		item.addActionListener(this);
		}
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == classMenuItems[0]) {
			DataListForm dataList = new DataListForm("Class");
			dataList.setVisible(true);
		}
		
		if(e.getSource() == studentMenuItems[1]) {
			DataListForm dataList = new DataListForm("Student");
			dataList.setVisible(true);
		}
		
		if(e.getSource() == classMenuItems[1]) {
			NewClassForm newClass = new NewClassForm(classMenuItems[1].getText());
			newClass.setVisible(true);
		}
		
		if(e.getSource() == classMenuItems[2]) {
			NewClassForm newClass = new NewClassForm(classMenuItems[2].getText());
			newClass.setVisible(true);
		}
		
		if(e.getSource() == studentMenuItems[0]) {
			NewStudentForm newStudent = new NewStudentForm();
			newStudent.setVisible(true);
		}
		
		
	}

}
