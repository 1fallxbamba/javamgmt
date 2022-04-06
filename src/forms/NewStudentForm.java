package forms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

import config.Database;

public class NewStudentForm extends JFrame implements ActionListener {
	
	private Container container = getContentPane();
	
	private JLabel firstNameLabel = new JLabel("Prénom");
	private JLabel lastNameLabel = new JLabel("Nom");
	private JLabel ageLabel = new JLabel("Age");
	
	private JTextField firstNameField = new JTextField();
	private JTextField lastNameField = new JTextField();
	
	private String ages[] = {"18", "19", "20", "21", "22", "23", "24", "25"};
	
	private JComboBox<String> agesList = new JComboBox<String>(ages);
	
	private JButton validateButton = new JButton("Valider");
	private JButton exitButton = new JButton("Quitter");
	
	public NewStudentForm() {
		
		container.setLayout(null);
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		
		this.setTitle("Ajout de cours d'un nouvel etudiant");
		this.setBounds(250, 250, 450, 300);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	private void setLocationAndSize() {
		
		firstNameLabel.setBounds(50, 70, 250, 30);
		lastNameLabel.setBounds(50, 120, 100, 30);
		ageLabel.setBounds(50, 170, 100, 30);
		
		firstNameField.setBounds(170, 70, 250, 30);
		lastNameField.setBounds(170, 120, 150, 30);
		agesList.setBounds(170, 170, 150, 30);
		
		validateButton.setBounds(50, 220, 100, 30);
		exitButton.setBounds(200, 220, 100, 30);
	}
	
	private void addComponentsToContainer() {
		container.add(firstNameLabel);
		container.add(lastNameLabel);
		container.add(ageLabel);
		container.add(firstNameField);
		container.add(lastNameField);
		container.add(agesList);
		container.add(validateButton);
		container.add(exitButton);
	}
	
    public void addActionEvent() {
    	agesList.addActionListener(this);
        validateButton.addActionListener(this);
        exitButton.addActionListener(this);
    }
    
	private void addNewStudent() {
		
        Connection connection = null;
        PreparedStatement statement = null;
        
        String query = "INSERT into students values(?, ?, ?)";
        
        String fullName = this.firstNameField.getText() + " " + this.lastNameField.getText();
        int age = Integer.parseInt(this.agesList.getSelectedItem().toString());
		
        try {
        	
            connection = Database.getConnection();
            statement = connection.prepareStatement(query);
            
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, fullName);
            statement.setInt(3, age);
            
            int result = statement.executeUpdate();
            
            if (result == 0) {
            	JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'insertion des détails de l'étudiant, veuillez réessayer", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
            	JOptionPane.showMessageDialog(this, "Nouvel étudiant ajouté avec succés" , "Ajout réussi", JOptionPane.INFORMATION_MESSAGE);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == validateButton) {
			
			if(this.firstNameField.getText().equals("") || this.lastNameField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Les champs ne peuvent pas être vide", "Veuillez remplir les champs", JOptionPane.ERROR_MESSAGE);
			} else {
				addNewStudent();
			}
			
		}
		
		if(e.getSource() == exitButton) {
			this.dispose();
		}
		
	}

}
