package net.toshimichi.dungeons.commands.admin.economy;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class SetCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        DungeonsPlugin.getEconomy().setMoney(player.getUniqueId(), arguments.getInt(0, "所持金"));
    }

    @Override
    public String getDescription() {
        return "所持金を設定します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.economy.set";
    }
}
