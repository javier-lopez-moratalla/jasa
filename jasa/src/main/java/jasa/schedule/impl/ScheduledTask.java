package jasa.schedule.impl;

import jasa.schedule.Task;

import java.util.Date;

public class ScheduledTask {
	private Date scheduledTime;
	private Task task;

	public ScheduledTask() {
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}