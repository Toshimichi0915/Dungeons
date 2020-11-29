package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.enchants.Enchant;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class EnchantListCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack item = player.getInventory().getItemInMainHand();
        Set<Enchant> enchants = DungeonsPlugin.getEnchantManager().getEnchants(item);
        for (Enchant enchant : enchants) {
            player.sendMessage(enchant.getName() + ChatColor.WHITE + " (id: " + enchant.getId() + " level: " + enchant.getLevel() + ")");
        }
    }

    @Override
    public String getDescription() {
        return "アイテムについているエンチャントを調べます";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.list";
    }
}
