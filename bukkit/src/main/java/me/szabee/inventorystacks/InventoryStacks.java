package me.szabee.inventorystacks;

import org.bukkit.plugin.java.JavaPlugin;

import me.szabee.inventorystacks.commands.ReloadCmd;
import me.szabee.inventorystacks.commands.StackCmd;
import me.szabee.inventorystacks.handlers.ItemHandler;
import me.szabee.inventorystacks.listeners.correction.BlockDispense;
import me.szabee.inventorystacks.listeners.correction.BundleFix;
import me.szabee.inventorystacks.listeners.correction.FurnaceBurn;
import me.szabee.inventorystacks.listeners.correction.InventoryClick;
import me.szabee.inventorystacks.listeners.correction.InventoryMoveItem;
import me.szabee.inventorystacks.listeners.correction.PlayerBucketEmpty;
import me.szabee.inventorystacks.listeners.correction.PlayerInteract;
import me.szabee.inventorystacks.listeners.correction.PlayerItemConsume;
import me.szabee.inventorystacks.listeners.correction.TotemFix;
import me.szabee.inventorystacks.listeners.general.BlockPlace;
import me.szabee.inventorystacks.listeners.general.Commands;
import me.szabee.inventorystacks.listeners.general.DroppedItemMerge;
import me.szabee.inventorystacks.listeners.general.ItemHologram;
import me.szabee.inventorystacks.listeners.general.PlayerItemDamage;
import me.szabee.inventorystacks.listeners.general.TotemOffhandLimit;
import me.szabee.inventorystacks.listeners.itemmeta.UpdateItemMeta;
import me.szabee.inventorystacks.managers.ItemHologramManager;
import me.szabee.inventorystacks.managers.SettingsManager;
import me.szabee.inventorystacks.util.ConsoleUtil;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public class InventoryStacks extends JavaPlugin {

	private static InventoryStacks INSTANCE;
	private SettingsManager settingsManager;
	private ItemHologramManager itemHologramManager;
	private BukkitAudiences adventureAPI;

	public void onEnable() {
		INSTANCE = this;

		ConsoleUtil.sendPluginStartSetup();

		saveDefaultConfig();

		if (!ItemHandler.getInstance().setup())
			return;

		getCommand("stack").setExecutor(new StackCmd());
		getCommand("stacks").setExecutor(new ReloadCmd());
		getCommand("inventorystacks").setExecutor(new ReloadCmd());

		settingsManager = new SettingsManager();
		settingsManager.setup(this);

		itemHologramManager = new ItemHologramManager(this);
		itemHologramManager.enable();

		if (getConfig().getBoolean("use-mini-message")) {
			this.adventureAPI = BukkitAudiences.create(this);
		}

		long itemChangeDelay = InventoryStacks.getInstance().getConfig().getLong("item-change-delay", 2L);

		getServer().getPluginManager().registerEvents(new Commands(), this);
		getServer().getPluginManager().registerEvents(new PlayerItemDamage(itemChangeDelay), this);
		getServer().getPluginManager().registerEvents(new BlockPlace(itemChangeDelay), this);
		getServer().getPluginManager().registerEvents(new TotemOffhandLimit(), this);
		getServer().getPluginManager().registerEvents(new ItemHologram(), this);
		getServer().getPluginManager().registerEvents(new DroppedItemMerge(), this);
		getServer().getPluginManager().registerEvents(new BundleFix(), this);
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getServer().getPluginManager().registerEvents(new TotemFix(), this);

		if (ItemHandler.getInstance().isUsingModernAPI()) {
			getServer().getPluginManager().registerEvents(new UpdateItemMeta(), this);
		} else { // LEGACY SUPPORT
			getServer().getPluginManager().registerEvents(new PlayerBucketEmpty(itemChangeDelay), this);
			getServer().getPluginManager().registerEvents(new PlayerItemConsume(itemChangeDelay), this);
			getServer().getPluginManager().registerEvents(new InventoryMoveItem(), this);
			getServer().getPluginManager().registerEvents(new FurnaceBurn(), this);
			getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
			getServer().getPluginManager().registerEvents(new BlockDispense(), this);
		}

		ConsoleUtil.sendPluginEndSetup();
	}

	public void onDisable() {
		if (itemHologramManager != null) {
			itemHologramManager.disable();
		}

		closeAdventure();
	}

	public void reloadMessaging() {
		closeAdventure();

		if (getConfig().getBoolean("use-mini-message")) {
			this.adventureAPI = BukkitAudiences.create(this);
		}
	}

	public void reloadItemHologramManager() {
		if (itemHologramManager != null) {
			itemHologramManager.reload();
		}
	}

	private void closeAdventure() {
		if (this.adventureAPI == null)
			return;

		this.adventureAPI.close();
		this.adventureAPI = null;
	}

	public BukkitAudiences getAdventure() {
		return this.adventureAPI;
	}

	public ItemHologramManager getItemHologramManager() {
		return itemHologramManager;
	}

	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	public static InventoryStacks getInstance() {
		return INSTANCE;
	}

}