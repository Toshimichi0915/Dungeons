package net.toshimichi.dungeons.commands.admin.enchant;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.commands.Arguments;
import net.toshimichi.dungeons.commands.CommandException;
import net.toshimichi.dungeons.commands.PlayerCommand;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.Set;

public class EnchantAddCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR)
            throw new CommandException("エンチャントするアイテムを手に持ってください");
        EnchantManager em = Dungeons.getInstance().getEnchantManager();
        Set<Enchant> set = em.getEnchants(item);
        int id = arguments.getInt(0, "id");
        int level = arguments.getInt(1, "level");
        Optional<Enchant> opt = em.getAllEnchants().stream().filter(p -> p.getId() == id && p.getLevel() == level).findAny();
        if (!opt.isPresent())
            throw new CommandException("そのエンチャントは存在しません");
        set.add(opt.get());
        em.setEnchants(item, set.toArray(new Enchant[0]));
        em.setLocale(item, Dungeons.getInstance().getLocaleManager().getLocale(player));
    }

    @Override
    public String getDescription() {
        return "アイテムにエンチャントを強制的に付与します";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.enchant.add";
    }
}
