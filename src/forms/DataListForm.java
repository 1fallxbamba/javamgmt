package forms;

import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

import config.Database;

public class DataListForm extends JFrame {

	
	private JTable table;
	
	private JScrollPane scroll;
	
	DefaultTableModel defaultModel = new DefaultTableModel();
	
	private String dataType = "Student";

    
    DataListForm(String _dataType) {
    	
    	this.dataType = _dataType;
    	
    	String title = (this.dataType.equals("Student")) ? "Liste d'étudiants" : "Liste de cours";
    	
    	this.setTitle(title);
    	this.setBounds(180, 220, 1200, 400);
    	
    	fetchDataList();
    	
    	this.setVisible(true);
    }
    
    private void initTable() {
    	
    	defaultModel.addColumn("Identifiant");
    	
    	if(this.dataType.equals("Student")) {
        	defaultModel.addColumn("Nom");
        	defaultModel.addColumn("Age");
    	} else if (this.dataType.equals("Class")) {
        	defaultModel.addColumn("Nom du cours");
        	defaultModel.addColumn("Contenu");
        	defaultModel.addColumn("Heure de début");
        	defaultModel.addColumn("Heure de fin");
        	defaultModel.addColumn("Date du cours");
    	}
    	
    	
    	table = new JTable(defaultModel);
    	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    	
    	scroll = new JScrollPane(table);
    	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    	
    	this.add(scroll);
    	
    }
    
    private void fetchDataList() {
    	
    	initTable();
    	
        ResultSet result = null;
        Connection connection = null;
        Statement statement = null;
        
        String query = (this.dataType.equals("Student")) ? "SELECT * FROM students" : "SELECT * FROM classes";
        
        String id, studentName, age, className, content, startTime, endTime, date;
        
        try {
        	
            connection = Database.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            
            while(result.next()) {
            	
            	id = result.getString("id");
            	
            	if(this.dataType.equals("Student")) {
            		
            		studentName = result.getString("name");
            		age = result.getString("age");
            		
            		defaultModel.addRow(new Object[]{id, studentName, age});
            		
            	} else if (this.dataType.equals("Class")) {
            		
            		className = result.getString("name");
            		content = result.getString("content");
            		startTime = result.getString("starting");
            		endTime = result.getString("ending");
            		date = result.getString("date");
            		
            		defaultModel.addRow(new Object[]{id, className, content, startTime, endTime, date});
            	}
            	
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
    
    

}
