package me.szabee.inventorystacks.scheduler;

import org.bukkit.Bukkit;

import me.szabee.inventorystacks.InventoryStacks;
import me.szabee.inventorystacks.handlers.ItemHandler;
import me.szabee.inventorystacks.util.ServerTypeUtil;

public abstract class Schedule implements Runnable {

	private final boolean USING_FOLIA = ItemHandler.getInstance().getServerType() == ServerTypeUtil.FOLIA;

	public void runTask() {
		if (USING_FOLIA) {
			Bukkit.getGlobalRegionScheduler().execute(InventoryStacks.getInstance(), this);
		} else {
			Bukkit.getScheduler().runTask(InventoryStacks.getInstance(), this);
		}
	}

	public void runTaskLater(long delay) {
		if (USING_FOLIA) {
			Bukkit.getGlobalRegionScheduler().runDelayed(InventoryStacks.getInstance(), t -> this.run(), delay);
		} else {
			Bukkit.getScheduler().runTaskLater(InventoryStacks.getInstance(), this, delay);
		}
	}
	
}