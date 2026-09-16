package me.szabee.inventorystacks.scheduler;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import me.szabee.inventorystacks.handlers.SchedulerHandler;

public abstract class Schedule implements Runnable {

	public void runTask() {
		SchedulerHandler.getInstance().runTask(this);
	}

	public void runTaskLater(long delay) {
		SchedulerHandler.getInstance().runTaskLater(this, delay);
	}

	public void runTask(Entity entity) {
		if (entity != null) {
			SchedulerHandler.getInstance().runTask(entity, this);
		} else {
			runTask();
		}
	}

	public void runTaskLater(Entity entity, long delay) {
		if (entity != null) {
			SchedulerHandler.getInstance().runTaskLater(entity, this, delay);
		} else {
			runTaskLater(delay);
		}
	}

	public void runTask(Location location) {
		if (location != null) {
			SchedulerHandler.getInstance().runTask(location, this);
		} else {
			runTask();
		}
	}

	public void runTaskLater(Location location, long delay) {
		if (location != null) {
			SchedulerHandler.getInstance().runTaskLater(location, this, delay);
		} else {
			runTaskLater(delay);
		}
	}

}