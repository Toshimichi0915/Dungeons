package net.toshimichi.dungeons.commands.admin.economy;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.entity.Player;

public class BalanceCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        int money = DungeonsPlugin.getEconomy().getMoney(player.getUniqueId());
        player.sendMessage("所持金: " + money);
    }

    @Override
    public String getDescription() {
        return "所持金を確認します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.economy.balance";
    }
}
