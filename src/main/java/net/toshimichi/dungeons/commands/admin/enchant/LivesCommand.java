package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LivesCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR)
            throw new CommandException("残機を設定するアイテムを手に持ってください");
        Dungeons.getInstance().getEnchantManager().setLives(item, arguments.getInt(0, "残機"));
        Dungeons.getInstance().getEnchantManager().setLocale(item, Dungeons.getInstance().getLocaleManager().getLocale(player));
    }

    @Override
    public String getDescription() {
        return "アイテムの残機を設定します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.lives";
    }
}
