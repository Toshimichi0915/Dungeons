package net.toshimichi.dungeons.commands.admin.enchant;

import net.md_5.bungee.api.ChatColor;
import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.enchants.Enchanter;
import org.bukkit.entity.Player;

public class AppliedCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        DungeonsPlugin.getEnchantManager().getEnchanters(player)
                .stream()
                .map(Enchanter::getEnchant)
                .forEach(a -> {
                    player.sendMessage(ChatColor.BLUE + a.getName() + ChatColor.WHITE + " (id: " + a.getId() + " level: " + a.getLevel() + ")");
                });
    }

    @Override
    public String getDescription() {
        return "適用されているエンチャントを確認します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.enchant.applied";
    }
}
