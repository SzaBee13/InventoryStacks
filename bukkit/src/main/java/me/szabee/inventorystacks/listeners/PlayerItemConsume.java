package me.szabee.inventorystacks.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import me.szabee.inventorystacks.scheduler.ChangeItemInHandWithItemTask;
import me.szabee.inventorystacks.util.ItemUtil;
import me.szabee.inventorystacks.util.VersionUtil;
import me.szabee.inventorystacks.util.XMaterialUtil;

public class PlayerItemConsume implements Listener {

	private final long itemChangeDelay;

	public PlayerItemConsume(long itemChangeDelay) {
		this.itemChangeDelay = itemChangeDelay;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		if (VersionUtil.v1_21.isServerVersionHigher())
			return;

		if (e.getItem() == null)
			return;

		if (e.getItem().getAmount() <= 1)
			return;

		if (e.getItem().getType() == XMaterialUtil.MILK_BUCKET.get()) {
			ItemUtil.addItem(e.getPlayer(), new ItemStack(XMaterialUtil.BUCKET.get()));
			return;
		}

		if (e.getItem().getType() != XMaterialUtil.RABBIT_STEW.get()
				&& e.getItem().getType() != XMaterialUtil.SUSPICIOUS_STEW.get()
				&& e.getItem().getType() != XMaterialUtil.MUSHROOM_STEW.get()
				&& e.getItem().getType() != XMaterialUtil.BEETROOT_SOUP.get())
			return;

		ItemStack clone = e.getItem().clone();
		clone.setAmount(e.getItem().getAmount() - 1);

		ChangeItemInHandWithItemTask changeItemTask = new ChangeItemInHandWithItemTask(e.getPlayer(), clone,
				new ItemStack(XMaterialUtil.BOWL.get()), XMaterialUtil.BOWL.get());
		changeItemTask.runTaskLater(itemChangeDelay);
	}

}