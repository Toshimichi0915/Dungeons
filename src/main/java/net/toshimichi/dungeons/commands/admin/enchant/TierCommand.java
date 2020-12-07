package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TierCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR)
            throw new CommandException("階位を設定するアイテムを手に持ってください");
        Dungeons.getInstance().getEnchantManager().setTier(item, arguments.getInt(0, "階位"));
        Dungeons.getInstance().getEnchantManager().setLocale(item, Dungeons.getInstance().getLocaleManager().getLocale(player));
    }

    @Override
    public String getDescription() {
        return "アイテムの階位を設定します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.tier";
    }
}
