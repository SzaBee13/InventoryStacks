package me.szabee.inventorystacks.util;

import me.szabee.inventorystacks.handlers.ItemHandler;

public enum VersionUtil {

  // Legacy NMS versions (per-version packages, field "maxStackSize", max 256)
  v1_8_R1(1),
  v1_8_R2(2),
  v1_8_R3(3),
  v1_9_R1(4),
  v1_9_R2(5),
  v1_10_R1(6),
  v1_11_R1(7),
  v1_12_R1(8),
  v1_13_R1(9),
  v1_13_R2(10),
  v1_14_R1(11),
  v1_15_R1(12),
  v1_16_R1(13),
  v1_16_R2(14),
  v1_16_R3(15),

  // 1.17: unified NMS package, field "c"
  v1_17_R1(16),

  // 1.18-1.19: unified NMS package, field "d"
  v1_18_R1(17),
  v1_18_R2(18),
  v1_19_R1(19),
  v1_19_R2(20),
  v1_19_R3(21),

  // 1.20: same as 1.19 but lower max (99)
  v1_20(22),

  // 1.21+: data components, no legacy stack field
  v1_21(23),

  // 1.26+: data components (same as 1.21)
  v1_26(24);

  private final int value;

  VersionUtil(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public boolean isServerVersionHigher() {
    return ItemHandler.getInstance().getServerVersion().getValue() >= getValue();
  }

  public boolean usesDataComponents() {
    return this == v1_21 || this == v1_26;
  }

  /**
   * Returns the NMS reflection configuration for the current server version.
   * This consolidates the per-version NMS class/field data that was previously
   * stored directly on each enum constant.
   */
  public static NmsReflection getNmsReflection() {
    VersionUtil v = ItemHandler.getInstance().getServerVersion();

    if (v.getValue() <= v1_16_R3.getValue()) {
      // Pre-1.17: per-version NMS packages
      String pkg = "net.minecraft.server." + v.name();
      return new NmsReflection(pkg + ".Item", pkg + ".Items", "maxStackSize", 256);
    }
    if (v.getValue() <= v1_17_R1.getValue()) {
      // 1.17
      return new NmsReflection("net.minecraft.world.item.Item", "net.minecraft.world.item.Items", "c", 256);
    }
    if (v.getValue() <= v1_19_R3.getValue()) {
      // 1.18-1.19
      return new NmsReflection("net.minecraft.world.item.Item", "net.minecraft.world.item.Items", "d", 256);
    }
    if (v == v1_20 || v.getValue() == v1_20.getValue()) {
      // 1.20
      return new NmsReflection("net.minecraft.world.item.Item", "net.minecraft.world.item.Items", "d", 256);
    }
    // 1.21+: data components
    return new NmsReflection("net.minecraft.world.item.Item", "net.minecraft.world.item.Items", null, 256);
  }

  /**
   * Immutable record holding NMS reflection metadata for a version range.
   */
  public record NmsReflection(
    String itemClass,
    String itemsClass,
    String legacyStackField,
    int absoluteMaxStackSize
  ) {
    public boolean usesDataComponents() {
      return legacyStackField == null;
    }
  }
}
