package net.toshimichi.dungeons.services;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class GuiService implements Service {
    @Override
    public void run() {
        Set<Player> players = Dungeons.getInstance().getGuiManager().getPlayers();
        for (Player player : players) {
            Gui gui = Dungeons.getInstance().getGuiManager().getGui(player);
            Inventory inventory = Dungeons.getInstance().getGuiManager().getInventory(player);
            if (gui == null || inventory == null) continue;
            gui.next(player, gui, inventory);
        }
    }
}
