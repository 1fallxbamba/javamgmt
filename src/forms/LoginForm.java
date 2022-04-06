package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

import config.Database;

public class LoginForm extends JFrame implements ActionListener {

	private Container container = getContentPane();
	private JLabel usernameLabel = new JLabel("Login");
	private JLabel passwordLabel = new JLabel("Mot de passe");
	private JTextField usernameTextField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginButton = new JButton("Connexion");
	private JButton clearButton = new JButton("Effacer");
	private JCheckBox showPassword = new JCheckBox("Voir mot de passe");

	public LoginForm() {
		container.setLayout(null);
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		
		this.setTitle("Bienvenue, veuillez vous connecter");
		this.setBounds(250, 250, 380, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void setLocationAndSize() {
		usernameLabel.setBounds(50, 70, 100, 30);
		passwordLabel.setBounds(50, 140, 100, 30);
		usernameTextField.setBounds(150, 70, 150, 30);
		passwordField.setBounds(150, 140, 150, 30);
		showPassword.setBounds(150, 170, 150, 30);
		loginButton.setBounds(50, 220, 100, 30);
		clearButton.setBounds(200, 220, 100, 30);
	}

	private void addComponentsToContainer() {
		container.add(usernameLabel);
		container.add(passwordLabel);
		container.add(usernameTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(clearButton);
	}
	
    public void addActionEvent() {
        loginButton.addActionListener(this);
        clearButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
        if (e.getSource() == loginButton) {
        	
        	if(usernameTextField.getText().equals("")  || passwordField.getText().equals("")) {
        		JOptionPane.showMessageDialog(this, "Les champs ne peuvent pas être vide", "Veuillez remplir les champs", JOptionPane.ERROR_MESSAGE);
        	} else {
        		logUserIn();
        	}
 
        }
        
        if (e.getSource() == clearButton) {
            usernameTextField.setText("");
            passwordField.setText("");
        }
        
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
		
	}
	
	private void logUserIn() {
		
        ResultSet result = null;
        Connection connection = null;
        Statement statement = null;
        
        String givenUsername = usernameTextField.getText() ;
        String givenPassword = passwordField.getText();
        
        String query = "SELECT * FROM users WHERE username = '" + givenUsername + "'";
        
        String fetchedName, fetchedPassword;
        
    	try {
    		
            connection = Database.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            
            if(result.next()) {
            	
            	fetchedName = result.getString("name");
            	fetchedPassword = result.getString("password");
            	
            	if(fetchedPassword.equals(givenPassword)) {
            		JOptionPane.showMessageDialog(this, "Bienvenue " + fetchedName, "Connexion réussie", JOptionPane.INFORMATION_MESSAGE);
            		HomeForm home = new HomeForm(fetchedName);
            		home.setVisible(true);
            		this.setVisible(false);
            	} else {
            		JOptionPane.showMessageDialog(this, "Le mot de passe est invalide, veuillez réessayer", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            	}
            	
            } else {
            	JOptionPane.showMessageDialog(this, "Ce nom d'utilisateur n'existe pas", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            }
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(
					this, 
					"Une erreur est survenue lors de la tentative de connexion au serveur, veuillez réessayer. (Erreur) : " + e.getMessage(), 
					"Erreur innatendue", 
					JOptionPane.ERROR_MESSAGE
			);
			
		}
		
	}

}
