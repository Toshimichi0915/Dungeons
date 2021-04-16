package net.toshimichi.dungeons.commands.admin.stash;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.misc.Stash;
import net.toshimichi.dungeons.nat.api.NbtItemStack;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import net.toshimichi.dungeons.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.ServicesManager;

import java.util.List;

public class CheckCommand implements PlayerCommand {

    private TextComponent getTextComponent(ItemStack itemStack) {
        ServicesManager services = Bukkit.getServicesManager();
        NbtItemStackFactory factory = services.load(NbtItemStackFactory.class);
        NbtItemStack nbtItemStack = factory.newNbtItemStack(itemStack);
        String name = nbtItemStack.getName();
        TextComponent result = new TextComponent(name + ChatColor.GRAY + " x" + itemStack.getAmount());
        result.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(ItemStackUtils.getDisplay(itemStack))}));
        return result;
    }

    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        Stash stash = Dungeons.getInstance().getStash();
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            List<ItemStack> list = stash.getItemStacksSilently(player.getUniqueId(), arguments.getString(0, "Stashの名前"));
            Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                if (list.isEmpty()) {
                    player.sendMessage("Stashには何も入っていません");
                    return;
                }
                for (ItemStack itemStack : list) {
                    player.spigot().sendMessage(getTextComponent(itemStack));
                }
            });
        });
    }

    @Override
    public String getDescription() {
        return "Stashの中身を確認します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.stash.check";
    }
}
