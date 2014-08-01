package jasa.schedule.impl;

import jasa.schedule.Schedule;
import jasa.schedule.Task;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class ScheduleImpl implements Schedule {

	private PriorityQueue queue;
	
	public ScheduleImpl() {
	
		queue = new PriorityQueue<ScheduledTask>(5,new ScheduledTaskComparator());
	}
	
	@Override
	public void addTask(Task task, Date date) {
		
		ScheduledTask scheduledTask = new ScheduledTask();
		scheduledTask.setTask(task);
		scheduledTask.setScheduledTime(date);
		
		queue.add(scheduledTask);
		
	}
	
	private class ScheduledTaskComparator implements Comparator<ScheduledTask>{
		
		@Override
		public int compare(ScheduledTask o1, ScheduledTask o2) {
		
			return o1.getScheduledTime().compareTo(o2.getScheduledTime());
		}
	}
}
