package me.szabee.inventorystacks.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.szabee.inventorystacks.InventoryStacks;

/**
 * Centralized permission checking with support for per-player stack size tiers.
 *
 * <p>Permission nodes:
 * <ul>
 *   <li>{@code STACKS.*} — all permissions</li>
 *   <li>{@code STACKS.COMMAND} — use /stack</li>
 *   <li>{@code STACKS.RELOAD} — reload config</li>
 *   <li>{@code STACKS.USE} — benefit from custom stack sizes</li>
 *   <li>{@code STACKS.SIZE.<amount>} — override stack size per player</li>
 * </ul>
 *
 * <p>When {@code permission-based-stack-sizes.enabled} is true, the highest
 * {@code STACKS.SIZE.<amount>} the player has is used instead of the config value.
 */
public final class PermissionUtil {

    private static volatile List<Integer> cachedSizes = Collections.emptyList();
    private static volatile boolean sizesEnabled = false;

    private PermissionUtil() {
    }

    public static boolean hasAnyPermission(Player player, String... permissions) {
        if (player == null)
            return false;

        for (String permission : permissions) {
            if (player.hasPermission(permission))
                return true;
        }

        return false;
    }

    public static boolean hasPermission(Player player, String permission) {
        return player != null && player.hasPermission(permission);
    }

    public static boolean hasPermissionOrWildcard(Player player, String permission) {
        return player != null
                && (player.hasPermission("STACKS.*") || player.hasPermission(permission));
    }

    /**
     * Returns the highest stack size the player is permitted to use via
     * {@code STACKS.SIZE.<amount>} permissions, or {@code -1} if the player
     * has no size permission (fall back to config).
     */
    public static int getPlayerStackSize(Player player) {
        if (player == null || !sizesEnabled)
            return -1;

        List<Integer> sizes = cachedSizes;
        int best = -1;

        for (int size : sizes) {
            if (player.hasPermission("STACKS.SIZE." + size) && size > best) {
                best = size;
            }
        }

        return best;
    }

    /**
     * Checks whether the given player is allowed to use custom stack sizes.
     * When {@code use-permission.enabled} is true, requires {@code STACKS.USE}
     * or {@code STACKS.*}. Otherwise returns true.
     */
    public static boolean canUseCustomStacks(Player player) {
        if (player == null)
            return true;

        ConfigurationSection config = InventoryStacks.getInstance().getConfig();

        if (!config.getBoolean("use-permission.enabled", false))
            return true;

        return hasPermissionOrWildcard(player, "STACKS.USE");
    }

    /**
     * Reloads the permission-based stack size configuration.
     * Called on plugin enable and config reload.
     */
    public static void reload() {
        ConfigurationSection config = InventoryStacks.getInstance().getConfig();
        ConfigurationSection section = config.getConfigurationSection("permission-based-stack-sizes");

        if (section == null || !section.getBoolean("enabled", false)) {
            sizesEnabled = false;
            cachedSizes = Collections.emptyList();
            return;
        }

        sizesEnabled = true;

        List<Integer> sizes = section.getIntegerList("sizes");

        if (sizes.isEmpty()) {
            sizes = Arrays.asList(16, 32, 64, 128, 256);
        }

        Collections.sort(sizes, Collections.reverseOrder());
        cachedSizes = Collections.unmodifiableList(sizes);

        ConsoleUtil.debug("Permission-based stack sizes loaded: " + cachedSizes);
    }
}
