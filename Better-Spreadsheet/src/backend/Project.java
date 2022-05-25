package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Project {
	
	ArrayList<Issue> issues; 
	private String projectName; 
	private String description; 
	private String projectManager; 
	private int ID; 
	boolean sorted; 
	
	public Project(String pName, String pManager, String desc, int id)
	{
		issues = new ArrayList<Issue>(); 
		this.projectName = pName; 
		this.projectManager = pManager; 
		this.description = desc; 
		this.ID = id; 
		sorted = false; 
	}
	
	public void addIssue(String desc, String status, int sw, int wr, int id, int prior)
	{
		Issue newIssue = new Issue(desc, status, sw, wr, id, prior); 
		issues.add(newIssue); 
	}
	public void addNewIssue(String description, String status, int startWeek, int issueNumber, int prior)
	{
		Issue newIssue = new Issue(description, status, startWeek, -1, issueNumber, prior); 
		issues.add(newIssue); 
		
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmanager", "root", "be4Panda");
			String sql = "INSERT INTO issue (i_description, i_status, weekCreated, i_id, p_id, priority)"
					+ " VALUES (?, ?, ?, ?, ?, ?)"; 
						 
			PreparedStatement statement = connection.prepareStatement(sql); 
			statement.setString(1, description); 
			statement.setString(2,  status); 
			statement.setInt(3,  startWeek); 
			statement.setInt(4,  issueNumber); 
			statement.setInt(5,  ID); 
			statement.setInt(6, prior); 
			
			statement.executeUpdate(); 
			connection.close(); 
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public int getConsecutiveWeeksWithIssue(int week)
	{
		int count = 0; 
		int temp = 0; 
		for(int x = week; x > 0; x--)
		{
			temp = x; 
			for(Issue i : issues)
			{
				boolean increased = false; 
				if(i.didIssueExistWeek(x) && !increased)
				{
					count++; 
					increased = true; 
					x--; 
				}
			}
			if(temp == x)
				return count; 
			else
				x++; 
		}
		return count;
	}
	
	public void editIssueStatus(int id, String edit)
	{
		for(Issue i : issues)
		{
			if(i.getID() == id)
			{
				i.setStatus(edit);
			}
		}
	}
	
	public void resolveIssue(int id, int endingWeek)
	{
		issues.get(id-1).setWeekResolved(endingWeek);
	}
	
	public int getUnresolvedIssues()
	{
		int count = 0; 
		for(Issue i : issues) 
		{
			if(i.getWeekResolved() < 1)
				count++; 
		}
		return count; 
	}
	
	public int getWeeksIssues(int week)
	{
		int count = 0; 
		for(Issue i: issues)
		{
			if((i.getWeekResolved() > week || i.getWeekResolved() < 1) && i.getWeekCreated() <= week)
			{
				count++; 
			}
		}
		return count; 
	}
	
	public ArrayList<Issue> getWeekIssueArray(int week)
	{
		ArrayList<Issue> temp = new ArrayList<Issue>(); 
		
		for(Issue i : issues)
		{
			if((i.getWeekResolved() > week || i.getWeekResolved() < 1) && i.getWeekCreated() <= week)
			{
				temp.add(i); 
			}
		}
		
		return temp; 
	}
	
	public void sortByIssueNumber()
	{

		ArrayList<Issue> newSort = new ArrayList<Issue>(); 
		int index; 
		Issue temp;
		
		if(sorted)
		{
			while(issues.size() > 0)
			{
				temp = issues.get(0); 
				index = 0; 
				for(int x = 0; x < issues.size(); x++)
				{
					Issue p = issues.get(x); 
					if(p.getID() > temp.getID())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				issues.remove(index); 
			}
			sorted = !sorted; 
		}
		else
		{
			while(issues.size() > 0)
			{
				temp = issues.get(0); 
				index = 0; 
				for(int x = 0; x < issues.size(); x++)
				{
					Issue p = issues.get(x); 
					if(p.getID() < temp.getID())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				issues.remove(index); 
			}
			sorted = !sorted; 
		}
		
		issues = newSort; 
	}
	
	public void sortByWeekCreated()
	{

		ArrayList<Issue> newSort = new ArrayList<Issue>(); 
		int index; 
		Issue temp;
		
		if(sorted)
		{
			while(issues.size() > 0)
			{
				temp = issues.get(0); 
				index = 0; 
				for(int x = 0; x < issues.size(); x++)
				{
					Issue p = issues.get(x); 
					if(p.getWeekCreated() > temp.getWeekCreated())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				issues.remove(index); 
			}
			sorted = !sorted; 
		}
		else
		{
			while(issues.size() > 0)
			{
				temp = issues.get(0); 
				index = 0; 
				for(int x = 0; x < issues.size(); x++)
				{
					Issue p = issues.get(x); 
					if(p.getWeekCreated() < temp.getWeekCreated())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				issues.remove(index); 
			}
			sorted = !sorted; 
		}
		
		issues = newSort; 
	}
	
	public void sortByPriority()
	{
		ArrayList<Issue> newSort = new ArrayList<Issue>(); 
		int index; 
		Issue temp;
		
		if(sorted)
		{
			while(issues.size() > 0)
			{
				temp = issues.get(0); 
				index = 0; 
				for(int x = 0; x < issues.size(); x++)
				{
					Issue p = issues.get(x); 
					if(p.getPriority() > temp.getPriority())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				issues.remove(index); 
			}
			sorted = !sorted; 
		}
		else
		{
			while(issues.size() > 0)
			{
				temp = issues.get(0); 
				index = 0; 
				for(int x = 0; x < issues.size(); x++)
				{
					Issue p = issues.get(x); 
					if(p.getPriority() < temp.getPriority())
					{
						index = x; 
						temp = p; 
					}
				}
				newSort.add(temp); 
				issues.remove(index); 
			}
			sorted = !sorted; 
		}
		
		issues = newSort; 
	}
	
	public ArrayList<Issue> getIssues()
	{
		return issues; 
	}
	
	public void setProjectName(String name)
	{
		this.projectName = name; 
	}
	
	public String getProjectName()
	{
		return this.projectName; 
	}
	
	public void setDescription(String desc)
	{
		this.description = desc; 
	}
	
	public String getDescription()
	{
		return this.description; 
	}
	
	public void setProjectManager(String name)
	{
		this.projectManager = name; 
	}
	
	public String getProjectManager()
	{
		return this.projectManager; 
	}
	
	public int getID()
	{
		return this.ID; 
	}
	
	public String toString()
	{
		return "Project ID: " + this.ID + "\nProject Name: " + this.projectName + "\nDescription: " + this.description
				+ "\nProject Manager: " + this.projectManager; 
	}
}
