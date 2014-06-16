
package masSim.taems;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class Task extends Node {

	private QAF qaf;
	public Date earliest_start_time;
	public Date deadline;
	public boolean isComplete = false;
		
	public int GetUtility()
	{
		return 0;
	}
	
	public boolean IsTask(){return true;}
	
	public boolean hasChildren()
	{
		return this.children.size()>0;
	}
		
	// Constructor
	public Task(String label, QAF qaf, Date earliest_start, Date deadline, Method m, IAgent agent){
		this.label = label;
		children = new ArrayList<Node>();
		this.qaf = qaf;
		this.earliest_start_time = earliest_start;
		this.deadline = deadline;
		if (m!=null)
			this.children.add(m);
		this.agent = agent;
	}
	
	public Task(String name, QAF qaf, Method m, IAgent agent){
		this(name, qaf, new Date(), new Date(2015,1,1), m, agent);
	}
	
	public Task(String name, QAF qaf, Method m){
		this(name, qaf, new Date(), new Date(2015,1,1), m, null);
	}
	
	public void addTask(Node task){
		this.children.add(task);
	}
	
	public QAF getQAF(){
		return qaf;
	}
	
	@Override
	public void MarkCompleted()
	{
		super.MarkCompleted();
		System.out.println("Task " + label + " completed.");
		this.NotifyAll();
	}
	
	@Override
	public void Cleanup()
	{
		if (this.hasChildren())
			for(Node n : children)
			{
				if (n!=null)
				{
					if (n.IsComplete())
					{
						children.remove(n);
					}
					else
					{
						if (n.IsTask())
						{
							n.Cleanup();
							if (n.IsComplete())//Recheck after cleanup
							{
								children.remove(n);
							}
						}
					}
				}
			}
	}



	
}
