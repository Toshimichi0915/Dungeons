package net.toshimichi.dungeons.commands.admin.nbt;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.nat.api.NbtItemStack;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import net.toshimichi.dungeons.nat.api.nbt.Nbt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ListCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        NbtItemStackFactory factory = Bukkit.getServicesManager().load(NbtItemStackFactory.class);
        NbtItemStack nbtItemStack = factory.newNbtItemStack(itemStack);
        for(Map.Entry<String, Nbt> entry : nbtItemStack.getNbtCompound().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public String getDescription() {
        return "NBTを表示します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.nbt.list";
    }
}
