package jasa.schedule;

import java.util.Date;

public interface Schedule {

	public void addTask(Task task,Date date);
	public void removeTask(Task task, Date date);
	
	
	public void start();
	
	public void stop();
}
