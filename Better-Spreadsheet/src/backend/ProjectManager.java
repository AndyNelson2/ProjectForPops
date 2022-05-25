package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectManager {
	
	ArrayList<Project> projects; 
	private int CurrentWeek; 
	boolean sorted; 
	public ProjectManager()
	{
		projects = new ArrayList<Project>(); 
		sorted = false; 
	}
	
	public void addProject(Project project)
	{
		projects.add(project); 
	}
	
	public void addProject(String name, String manager, String desc)
	{
		Project newProject = new Project(name, manager, desc, projects.size()+1); 
		projects.add(newProject); 
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanager", "root", "be4Panda");
			String sql = "INSERT INTO project VALUES(?, ?, ?, ?)"; 
			PreparedStatement statement = connection.prepareStatement(sql); 
			statement.setString(1, name); 
			statement.setString(2, desc); 
			statement.setString(3,  manager); 
			statement.setInt(4,  newProject.getID()); 
			
			statement.executeUpdate(); 
			connection.close(); 
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public Project getProjectforIssue(int issueID)
	{
		for(Project p : projects)
		{
			for(Issue i : p.getIssues())
			{
				if(issueID == i.getID())
					return p; 
			}
		}
		
		return null; 
	}
	
	public ArrayList<Project> getProjects()
	{
		return projects; 
	}
	
	public Project getProject(int id)
	{
		for(Project p : projects)
		{
			if(p.getID() == id)
				return p; 
		}
		
		return null; 
	}
	
	public int totalIssues()
	{
		int numberOfIssues = 0; 
		for(Project p : projects)
		{
			numberOfIssues += p.issues.size(); 
		}
		return numberOfIssues; 
	}
	
	public void sortByID()
	{
		ArrayList<Project> newSort = new ArrayList<Project>(); 
		int index; 
		Project temp;
		
		if(sorted)
		{
			while(projects.size() > 0)
			{
				temp = projects.get(0); 
				index = 0; 
				for(int x = 0; x < projects.size(); x++)
				{
					Project p = projects.get(x); 
					if(p.getID() > temp.getID())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				projects.remove(index); 
			}
			sorted = !sorted; 
		}
		else
		{
			while(projects.size() > 0)
			{
				temp = projects.get(0); 
				index = 0; 
				for(int x = 0; x < projects.size(); x++)
				{
					Project p = projects.get(x); 
					if(p.getID() < temp.getID())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				projects.remove(index); 
			}
			sorted = !sorted; 
		}
		
		projects = newSort; 
	}

	public void sortByRunningIssues()
	{

		ArrayList<Project> newSort = new ArrayList<Project>(); 
		int index; 
		Project temp;
		
		if(sorted)
		{
			while(projects.size() > 0)
			{
				temp = projects.get(0); 
				index = 0; 
				for(int x = 0; x < projects.size(); x++)
				{
					Project p = projects.get(x); 
					if(p.getConsecutiveWeeksWithIssue(CurrentWeek) > temp.getConsecutiveWeeksWithIssue(CurrentWeek))
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				projects.remove(index); 
			}
			sorted = !sorted; 
		}
		else
		{
			while(projects.size() > 0)
			{
				temp = projects.get(0); 
				index = 0; 
				for(int x = 0; x < projects.size(); x++)
				{
					Project p = projects.get(x); 
					if(p.getConsecutiveWeeksWithIssue(CurrentWeek) < temp.getConsecutiveWeeksWithIssue(CurrentWeek))
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				projects.remove(index); 
			}
			sorted = !sorted; 
		}
		
		projects = newSort; 
	}
	
	public void sortByTotalIssues()
	{

		ArrayList<Project> newSort = new ArrayList<Project>(); 
		int index; 
		Project temp;
		
		if(sorted)
		{
			while(projects.size() > 0)
			{
				temp = projects.get(0); 
				index = 0; 
				for(int x = 0; x < projects.size(); x++)
				{
					Project p = projects.get(x); 
					if(p.issues.size() > temp.issues.size())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				projects.remove(index); 
			}
			sorted = !sorted; 
		}
		else
		{
			while(projects.size() > 0)
			{
				temp = projects.get(0); 
				index = 0; 
				for(int x = 0; x < projects.size(); x++)
				{
					Project p = projects.get(x); 
					if(p.issues.size() < temp.issues.size())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				projects.remove(index); 
			}
			sorted = !sorted; 
		}
		
		projects = newSort; 
	}
	
	public void setCurrentWeek(int cw)
	{
		this.CurrentWeek = cw; 
	}
	
	public void nextWeek()
	{
		this.CurrentWeek++; 
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanager", "root", "be4Panda");
			String sql = "UPDATE currentWeek SET current_week = ? WHERE current_week = ?"; 
			PreparedStatement statement = connection.prepareStatement(sql); 
			statement.setInt(1, this.CurrentWeek); 
			statement.setInt(2,  this.CurrentWeek-1); 
			
			statement.executeUpdate(); 
			connection.close(); 
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public int getCurrentWeek()
	{
		return this.CurrentWeek; 
	}
	
	public void printInfo()
	{
		System.out.println("Current Week: " + CurrentWeek);
		for(Project p : projects)
		{
			System.out.println("===================================================");
			System.out.println(p.toString());
			for(Issue i : p.issues)
			{
				System.out.println("========================");
				System.out.println(i.toString()); 
			}
		}
	}
}