package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.utils.Nonce;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NonceCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR)
            throw new CommandException("固有番号を確認するアイテムを手に持ってください");
        player.sendMessage("Nonce: " + Nonce.getNonce(item));
    }

    @Override
    public String getDescription() {
        return "アイテムの固有番号を確認します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.nonce";
    }
}
