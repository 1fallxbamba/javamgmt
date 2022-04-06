package forms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

import config.Database;

public class NewClassForm extends JFrame implements ActionListener {
	
	private Container container = getContentPane();
	
	private JLabel classContentLabel = new JLabel("Contenu du cours");
	private JLabel startingTimeLabel = new JLabel("Heure de début");
	private JLabel endingTimeLabel = new JLabel("Heure de fin");
	
	private JTextField classContentField = new JTextField();
	
	private String startTimes[] = {"08H", "09H", "10H", "11H", "12H", "13H", "14H", "15H"};
	private String endTimes[] = {"09H", "10H", "11H", "12H", "13H", "14H", "15H", "16H", "17H"};
	
	private JComboBox<String> startTimesList = new JComboBox<String>(startTimes);
	private JComboBox<String> endTimesList = new JComboBox<String>(endTimes);
	
	private JButton validateButton = new JButton("Valider");
	private JButton exitButton = new JButton("Quitter");
	
	private String className;
	
	NewClassForm(String _className) {
		
		this.className = _className;
		
		container.setLayout(null);
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		
		this.setTitle("Nouveau cours de " + _className);
		this.setBounds(250, 250, 450, 300);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void setLocationAndSize() {
		
		classContentLabel.setBounds(50, 70, 250, 30);
		startingTimeLabel.setBounds(50, 120, 100, 30);
		endingTimeLabel.setBounds(50, 170, 100, 30);
		
		classContentField.setBounds(170, 70, 250, 30);
		startTimesList.setBounds(170, 120, 150, 30);
		endTimesList.setBounds(170, 170, 150, 30);
		validateButton.setBounds(50, 220, 100, 30);
		exitButton.setBounds(200, 220, 100, 30);
	}
	
	private void addComponentsToContainer() {
		container.add(classContentLabel);
		container.add(startingTimeLabel);
		container.add(endingTimeLabel);
		container.add(classContentField);
		container.add(startTimesList);
		container.add(endTimesList);
		container.add(validateButton);
		container.add(exitButton);
	}
	
	private void addNewClass() {
		
        Connection connection = null;
        PreparedStatement statement = null;
        
        String query = "INSERT into classes values(?, ?, ?, ?, ?)";
		
        try {
        	
            connection = Database.getConnection();
            statement = connection.prepareStatement(query);
            
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, this.className);
            statement.setString(3, this.classContentField.getText());
            statement.setString(4, this.startTimesList.getSelectedItem().toString());
            statement.setString(5, this.endTimesList.getSelectedItem().toString());
            
            int result = statement.executeUpdate();
            
            if (result == 0) {
            	JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'insertion des détails du cours, veuillez réessayer", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
            	JOptionPane.showMessageDialog(this, "Le cours a été ajouté avec succés" , "Ajout réussi", JOptionPane.INFORMATION_MESSAGE);
            	this.dispose();
            }
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(
					this, 
					"Une erreur inatendue est survenue lors de la tentative de connexion au serveur, veuillez réessayer. (Erreur) : " + e.getMessage(), 
					"Erreur innatendue", 
					JOptionPane.ERROR_MESSAGE
			);
		}
        
	}
	
    public void addActionEvent() {
    	startTimesList.addActionListener(this);
    	endTimesList.addActionListener(this);
        validateButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == validateButton) {
			
			if(this.classContentField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Les champs ne peuvent pas être vide", "Veuillez remplir les champs", JOptionPane.ERROR_MESSAGE);
			} else {
				addNewClass();
			}
			
		}
		
		if(e.getSource() == exitButton) {
			this.dispose();
		}
		
	}

}
