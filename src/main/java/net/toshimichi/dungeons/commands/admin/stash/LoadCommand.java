package net.toshimichi.dungeons.commands.admin.stash;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.misc.Stash;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        Stash stash = Dungeons.getInstance().getStash();
        String space = arguments.getString(0, "Stashの名前");
        ItemStack[] oldContents = player.getInventory().getContents();
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            List<ItemStack> list = stash.getItemStacksSilently(player.getUniqueId(), space);

            Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                HashMap<Integer, ItemStack> fail = player.getInventory().addItem(list.toArray(new ItemStack[0]));
                List<ItemStack> failList = new ArrayList<>(fail.values());

                Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
                    try {
                        stash.setItemStacks(player.getUniqueId(), space, failList.toArray(new ItemStack[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                        //revert the inventory
                        Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                            player.getInventory().setContents(oldContents);
                            player.sendMessage(ChatColor.RED + "Stashを編集できませんでした");
                        });
                    }
                });
            });
        });
    }

    @Override
    public String getDescription() {
        return "Stashをロードします";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.stash.load";
    }
}
