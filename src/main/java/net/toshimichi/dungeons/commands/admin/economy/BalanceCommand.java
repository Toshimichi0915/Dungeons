package net.toshimichi.dungeons.commands.admin.economy;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BalanceCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
            int money = DungeonsPlugin.getEconomy().getBalance(player.getUniqueId());
            Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () -> {
                player.sendMessage("所持金: " + money);
            });
        });
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
