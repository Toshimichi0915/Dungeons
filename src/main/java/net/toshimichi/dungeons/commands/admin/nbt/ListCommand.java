package net.toshimichi.dungeons.commands.admin.nbt;

import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import net.toshimichi.dungeons.nat.api.nbt.NbtCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ListCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() == Material.AIR)
            throw new CommandException("解析したいアイテムを手に持ってください");
        NbtItemStackFactory factory = Bukkit.getServicesManager().load(NbtItemStackFactory.class);
        NbtCompound compound = factory.newNbtItemStack(itemStack).getNbtCompound();
        if (compound == null)
            throw new CommandException("このアイテムにNBTタグは存在しません");
        player.sendMessage(compound.toString());
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
