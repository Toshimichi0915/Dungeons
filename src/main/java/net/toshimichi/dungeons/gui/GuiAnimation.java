package net.toshimichi.dungeons.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface GuiAnimation {
    void next(Player player, Gui gui, Inventory inv);
}
