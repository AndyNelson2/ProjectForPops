package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ui.ProjectOverview;

public class Main {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/projectmanager"; 
		String username = "root"; 
		String password = "be4Panda"; 
		
		ProjectManager jim = new ProjectManager(); 
		
		
		
		try {
			Connection connection = DriverManager.getConnection(url, username, password); 
			System.out.println("Conected to the database.");
			
			String sql = "SELECT * FROM project"; 
			Statement statement = connection.createStatement(); 
			ResultSet result = statement.executeQuery(sql); 
			
			while(result.next())
			{				
				Project newProject = new Project(result.getString("p_name"), result.getString("p_manager_name"), 
						result.getString("p_description"), result.getInt("p_id")); 
				jim.addProject(newProject); 
			}
			
			sql = "SELECT * FROM issue"; 
			statement = connection.createStatement(); 
			result = statement.executeQuery(sql); 
			
			while(result.next())
			{
				for (Project p : jim.getProjects())
				{
					if(result.getInt("p_id") == p.getID())
					{
						p.addIssue(result.getString("i_description"), result.getString("i_status"), result.getInt("weekCreated"), 
								result.getInt("weekResolved"), result.getInt("i_id"), result.getInt("priority"));
					}
				}
			}
			
			sql = "SELECT * FROM currentWeek"; 
			statement = connection.createStatement(); 
			result = statement.executeQuery(sql); 
			
			result.next(); 
			int currentWeek = result.getInt("current_week"); 
			System.out.println(currentWeek);
			jim.setCurrentWeek(currentWeek);
			
			connection.close();
		}catch(SQLException e) {
			System.out.println("Oops, error!" + e.toString());
		}
		jim.printInfo(); 
		new ProjectOverview(jim);

	}

}
