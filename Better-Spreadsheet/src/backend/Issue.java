package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Issue {
	
	private String description; 
	private String status; 
	private int priority; 
	private int weekCreated; 
	private int weekResolved; 
	private int id; 
	
	public Issue(String desc, int start, int id, int prior)
	{
		this.description = desc; 
		this.weekCreated = start; 
		this.id = id; 
		this.weekResolved = -1; 
		this.priority = prior; 
	}
	
	public Issue(String desc, String status, int sw, int wr, int id, int prior)
	{
		this.description = desc; 
		this.status = status; 
		this.weekCreated = sw; 
		this.weekResolved = wr; 
		this.id = id; 
		this.priority = prior; 
	}
	
	public boolean didIssueExistWeek(int week)
	{
		if(weekCreated <= week && weekResolved < 1)
			return true; 
		else if(weekCreated <= week && weekResolved > week)
			return true; 
		else
			return false; 
	}
	public void setDescription(String dis)
	{
		this.description = dis; 
	}
	
	public String getDescription()
	{
		return this.description; 
	}
	
	public void setStatus(String stat)
	{
		this.status = stat; 
	}
	
	public String getStatus()
	{
		return this.status; 
	}
	
	public void setWeekCreated(int created)
	{
		this.weekCreated = created; 
	}
	
	public int getWeekCreated()
	{
		return this.weekCreated; 
	}
	
	public void setWeekResolved(int resolved)
	{
		try {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanager", "root", "be4Panda");
		String sql = "UPDATE issue SET weekResolved = ? WHERE i_id = ?";
					 
		PreparedStatement statement = connection.prepareStatement(sql); 
		statement.setInt(1, resolved); 
		statement.setInt(2,  this.id); 
		
		statement.executeUpdate(); 
		connection.close(); 
		
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
		this.weekResolved = resolved; 
	}
	
	public int getWeekResolved()
	{
		return this.weekResolved; 
	}
	
	public void setID(int id)
	{
		this.id = id; 
	}
	
	public int getID()
	{
		return this.id; 
	}
	
	public void setPriority(int p)
	{
		this.priority = p; 
	}
	
	public int getPriority()
	{
		return this.priority; 
	}
	
	public String toString()
	{
		
		return "Issue ID: " + this.id + "\nDescription: " + this.description
				+ "\nstatus: " + this.status + "\nWeek created: " + this.weekCreated
				+ "\nWeek resolved: " + this.weekResolved + "\nPriority: " + this.priority; 
	}
}
