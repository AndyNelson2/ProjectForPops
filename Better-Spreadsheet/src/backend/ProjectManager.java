package backend;

import java.util.ArrayList;

public class ProjectManager {
	
	ArrayList<Project> projects; 
	private int CurrentWeek; 
	public ProjectManager()
	{
		projects = new ArrayList<Project>(); 
	}
	
	public void addProject(Project project)
	{
		projects.add(project); 
	}
	
	public ArrayList<Project> getProjects()
	{
		return projects; 
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
	
	public void setCurrentWeek(int cw)
	{
		this.CurrentWeek = cw; 
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