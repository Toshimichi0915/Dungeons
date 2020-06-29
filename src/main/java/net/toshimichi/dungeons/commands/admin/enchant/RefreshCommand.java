package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.exceptions.IllegalCommandUsageException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RefreshCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() == Material.AIR)
            throw new IllegalCommandUsageException("更新するアイテムを手に持ってください");
        DungeonsPlugin.getEnchantManager().setLocale(item, DungeonsPlugin.getLocaleManager().getLocale(player));
        DungeonsPlugin.getEnchantManager().refresh(item);
    }

    @Override
    public String getDescription() {
        return "アイテムの名前や説明を更新します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.refresh";
    }
}
