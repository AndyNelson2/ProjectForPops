package backend;

public class Issue {
	
	private String description; 
	private String status; 
	private int weekCreated; 
	private int weekResolved; 
	private int id; 
	
	public Issue(String desc, int start, int id)
	{
		this.description = desc; 
		this.weekCreated = start; 
		this.id = id; 
		this.weekResolved = -1; 
	}
	
	public Issue(String desc, String status, int sw, int wr, int id)
	{
		this.description = desc; 
		this.status = status; 
		this.weekCreated = sw; 
		this.weekResolved = wr; 
		this.id = id; 
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
	
	public String toString()
	{
		
		return "Issue ID: " + this.id + "\nDescription: " + this.description
				+ "\nstatus: " + this.status + "\nWeek created: " + this.weekCreated
				+ "\nWeek resolved: " + this.weekResolved; 
	}
}
