package me.szabee.inventorystacks.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import me.szabee.inventorystacks.InventoryStacks;
import me.szabee.inventorystacks.util.ServerTypeUtil;

public class SchedulerHandler {

	private static final SchedulerHandler INSTANCE = new SchedulerHandler();

	public void runTask(Runnable runnable) {
		if (ServerTypeUtil.isFolia()) {
			Bukkit.getGlobalRegionScheduler().execute(InventoryStacks.getInstance(), runnable);
		} else {
			Bukkit.getScheduler().runTask(InventoryStacks.getInstance(), runnable);
		}
	}

	public void runTaskLater(Runnable runnable, long delay) {
		if (ServerTypeUtil.isFolia()) {
			Bukkit.getGlobalRegionScheduler().runDelayed(InventoryStacks.getInstance(), t -> runnable.run(), delay);
		} else {
			Bukkit.getScheduler().runTaskLater(InventoryStacks.getInstance(), runnable, delay);
		}
	}

	public void runTask(Entity entity, Runnable runnable) {
		if (ServerTypeUtil.isFolia()) {
			entity.getScheduler().execute(InventoryStacks.getInstance(), runnable, null, 0);
		} else {
			Bukkit.getScheduler().runTask(InventoryStacks.getInstance(), runnable);
		}
	}

	public void runTaskLater(Entity entity, Runnable runnable, long delay) {
		if (ServerTypeUtil.isFolia()) {
			entity.getScheduler().execute(InventoryStacks.getInstance(), runnable, null, delay);
		} else {
			Bukkit.getScheduler().runTaskLater(InventoryStacks.getInstance(), runnable, delay);
		}
	}

	public void runTask(Location location, Runnable runnable) {
		if (ServerTypeUtil.isFolia()) {
			Bukkit.getRegionScheduler().execute(InventoryStacks.getInstance(), location, runnable);
		} else {
			Bukkit.getScheduler().runTask(InventoryStacks.getInstance(), runnable);
		}
	}

	public void runTaskLater(Location location, Runnable runnable, long delay) {
		if (ServerTypeUtil.isFolia()) {
			Bukkit.getRegionScheduler().runDelayed(InventoryStacks.getInstance(), location, t -> runnable.run(), delay);
		} else {
			Bukkit.getScheduler().runTaskLater(InventoryStacks.getInstance(), runnable, delay);
		}
	}

	public static SchedulerHandler getInstance() {
		return INSTANCE;
	}
}
