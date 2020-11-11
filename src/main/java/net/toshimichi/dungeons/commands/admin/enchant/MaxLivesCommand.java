package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MaxLivesCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR)
            throw new CommandException("最大残機を設定するアイテムを手に持ってください");
        DungeonsPlugin.getEnchantManager().setMaxLives(item, arguments.getInt(0, "最大残機"));
        DungeonsPlugin.getEnchantManager().setLocale(item, DungeonsPlugin.getLocaleManager().getLocale(player));
    }

    @Override
    public String getDescription() {
        return "アイテムの最大残機を設定します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.maxlives";
    }
}
