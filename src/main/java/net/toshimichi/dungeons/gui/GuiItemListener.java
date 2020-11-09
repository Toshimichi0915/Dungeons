package net.toshimichi.dungeons.gui;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface GuiItemListener {
    void onClick(Player player, GuiInfo gui, GuiItem item);
}
