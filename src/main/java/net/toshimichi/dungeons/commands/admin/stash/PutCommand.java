package net.toshimichi.dungeons.commands.admin.stash;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.misc.Stash;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class PutCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() == Material.AIR)
            throw new CommandException("stashに入れるアイテムを手に持ってください");
        Stash stash = Dungeons.getInstance().getStash();
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            try {
                stash.addItemStack(player.getUniqueId(), arguments.getString(0, "Stashの名前"), itemStack);
                player.getInventory().setItemInMainHand(null);
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () ->
                        player.sendMessage(ChatColor.RED + "Stashをロードできませんでした"));
            }
        });
    }

    @Override
    public String getDescription() {
        return "Stashにアイテムを追加します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.stash.put";
    }
}
