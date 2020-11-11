package net.toshimichi.dungeons.commands.admin;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.commands.CommandException;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomModelDataCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR)
            throw new CommandException("カスタムモデルデータの操作対象を手に持ってください");
        int data;
        if (arguments.length() < 1) {
            data = item.getItemMeta() == null ? 0 : item.getItemMeta().getCustomModelData();
            if (data == 0)
                player.sendMessage("カスタムモデルデータ: なし");
            else
                player.sendMessage("カスタムモデルデータ: " + data);
        } else {
            data = arguments.getInt(0);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setCustomModelData(data);
                item.setItemMeta(meta);
            }
        }
    }

    @Override
    public String getDescription() {
        return "カスタムモデルデータを操作します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.model";
    }
}
