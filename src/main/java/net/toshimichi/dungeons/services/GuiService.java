package net.toshimichi.dungeons.services;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class GuiService implements Service {
    @Override
    public void run() {
        Set<Player> players = DungeonsPlugin.getGuiManager().getPlayers();
        for (Player player : players) {
            Gui gui = DungeonsPlugin.getGuiManager().getGui(player);
            Inventory inventory = DungeonsPlugin.getGuiManager().getInventory(player);
            if (gui == null || inventory == null) continue;
            gui.next(player, gui, inventory);
        }
    }
}
