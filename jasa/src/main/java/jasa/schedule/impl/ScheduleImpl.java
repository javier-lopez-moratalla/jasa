package jasa.schedule.impl;

import jasa.schedule.Schedule;
import jasa.schedule.Task;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class ScheduleImpl implements Schedule {

	public static long RESOLUTION = 1000;
	
	private PriorityQueue<ScheduledTask> queue;
	private Object lockQueue;
	private Date nextExecution;
	
	private Object lockStop;
	private boolean stop;
	
	public ScheduleImpl() {
	
		queue = new PriorityQueue<ScheduledTask>(5,new ScheduledTaskComparator());
		lockQueue = new Object();
		
		stop = true;
		lockStop = new Object();
	}
	
	@Override
	public void addTask(Task task, Date date) {
				
		ScheduledTask scheduledTask = new ScheduledTask();
		scheduledTask.setTask(task);
		scheduledTask.setScheduledTime(date);
		
		synchronized (lockQueue) {
		
			queue.add(scheduledTask);
		}
		
		setNextExecution();
	}
	
	@Override
	public void start() {
	
		while(!isStop()){
			
			Date now = new Date();
			Date actualNextExecution = getNextExecution();
			
			if(actualNextExecution == null){
				
				if(now.equals(actualNextExecution)){
					
					executeNext();
				}
			}
			
			setNextExecution();
			
			try{
				Thread.sleep(RESOLUTION);
			}
			catch(InterruptedException e){
				
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void stop() {
	
		synchronized (lockStop) {
		
			stop = true;
		}		
	}
	
	private boolean isStop(){
		
		synchronized (lockStop) {
		
			return stop;
		}
	}
	
	private Date getNextExecution(){
		
		synchronized (lockQueue) {
		
			return nextExecution;
		}
	}
	
	private void setNextExecution(){
		
		Date date = null;
		
		synchronized (lockQueue) {
		
			ScheduledTask task = queue.peek();
			
			if(task != null){
				
				date = task.getScheduledTime(); 
			}
			
			nextExecution = date;
		}
	}
	
	private void executeNext(){
		
		Task task = null;
		
		synchronized (lockQueue) {
		
			ScheduledTask nextTask = queue.poll();
			task = nextTask.getTask();				
		}
		
		if(task != null){
			task.execute(this);
		}
	}
	
	private class ScheduledTaskComparator implements Comparator<ScheduledTask>{
		
		@Override
		public int compare(ScheduledTask o1, ScheduledTask o2) {
		
			return o1.getScheduledTime().compareTo(o2.getScheduledTime());
		}
	}
}
