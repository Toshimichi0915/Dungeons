package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.gui.EnchantGui;
import org.bukkit.entity.Player;

public class WellCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        Dungeons.getInstance().getGuiManager().show(player, new EnchantGui());
    }

    @Override
    public String getDescription() {
        return "錬金台を表示します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.well";
    }
}
