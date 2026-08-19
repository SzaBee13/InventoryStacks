# Introduction

**InventoryStacks** lets you change how many items can fit in a single stack. If you want to let players stack things like potions, totems, or ender pearls past the usual limit of 64, this is the way to do it.

## About This Fork

This is a hard fork of [InventoryStacks](https://github.com/BrilliantTeam/InventoryStacks).

### What's different

**New features:**

- **Dropped-item merging** -- nearby identical drops are automatically merged into larger stacks, reducing entity lag
- **Item holograms** -- configurable floating labels above dropped items showing amount, name, and despawn timer
- **Permission-based stacking** -- opt-in stacking via the `STACKS.USE` permission node (1.20.5+)
- **WorldGuard region support** -- restrict custom stacking to specific WorldGuard regions (1.20.5+)
- **Configurable merge radius** -- tune how far dropped items search for merge candidates
- **Debug mode** -- detailed console logging for ground-merge and pickup troubleshooting

**Fixes:**

- **Totem offhand fixes** -- resolves totem stacking issues in the offhand slot (shift-click and direct-click)
- **Bundle fix** -- prevents inventory glitches with bundles on 1.20.5+

**Dropped from upstream (not carried forward):**

- `OminousBannerPin` listener
- `PlayerInteractEntity` listener
- `RegionSchedule` scheduler
- `BlockUtil` utility

### Relationship to upstream

This is an independent fork. Not affiliated with, endorsed by, or supported by the original maintainers.

## Supported Versions

| Minecraft Version | Stack Sizing Method | Max Stack |
| --- | --- | --- |
| 1.8 -- 1.19.x | NMS reflection | Up to 256 |
| 1.20.x | NMS reflection | Up to 256 |
| 1.20.5+ | ItemMeta API (default) or NMS data-component reflection | Up to 256 |
