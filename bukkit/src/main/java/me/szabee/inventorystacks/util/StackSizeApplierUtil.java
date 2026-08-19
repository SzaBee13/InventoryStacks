package me.szabee.inventorystacks.util;

import org.bukkit.inventory.meta.ItemMeta;

import me.szabee.inventorystacks.handlers.ItemHandler;
import me.szabee.inventorystacks.items.ItemMetaStackSizeApplier;
import me.szabee.inventorystacks.items.LegacyNmsStackSizeApplier;
import me.szabee.inventorystacks.items.StackSizeApplier;

public final class StackSizeApplierUtil {

	public static StackSizeApplier create() {
		if (hasItemMetaSetMaxStackSize() && !ItemHandler.getInstance().useLegacyReflection()) {
			return new ItemMetaStackSizeApplier();
		}

		return new LegacyNmsStackSizeApplier();
	}

	private static boolean hasItemMetaSetMaxStackSize() {
		try {
			ItemMeta.class.getMethod("setMaxStackSize", Integer.class);
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}
}
