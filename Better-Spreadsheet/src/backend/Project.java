package backend;

import java.util.ArrayList;

public class Project {
	
	ArrayList<Issue> issues; 
	private String projectName; 
	private String description; 
	private String projectManager; 
	private int ID; 
	public Project(String pName, String pManager, String desc, int id)
	{
		issues = new ArrayList<Issue>(); 
		this.projectName = pName; 
		this.projectManager = pManager; 
		this.description = desc; 
		this.ID = id; 
	}
	
	public void addIssue(String desc, String status, int sw, int wr, int id)
	{
		Issue newIssue = new Issue(desc, status, sw, wr, id); 
		issues.add(newIssue); 
	}
	public void addNewIssue(String description, int startWeek, int issueNumber)
	{
		Issue newIssue = new Issue(description, startWeek, issueNumber); 
		issues.add(newIssue); 
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
