package net.toshimichi.dungeons.nat.v1_15_2;

import net.minecraft.server.v1_15_R1.Item;
import net.minecraft.server.v1_15_R1.ItemCooldown;
import net.toshimichi.dungeons.nat.api.CooldownUtils;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * アイテムのクールダウンをNMSを利用して管理するクラスです.
 */
public class NativeCooldownUtils implements CooldownUtils {

    private static ItemCooldown getCooldownTracker(Player player) {
        return ((CraftPlayer) player).getHandle().getCooldownTracker();
    }

    @Override
    public void setCooldown(Player player, Material material, int tick) {
        getCooldownTracker(player).setCooldown(CraftItemStack.asNMSCopy(new ItemStack(material)).getItem(), tick);
    }

    @Override
    public int getCooldown(Player player, Material material) {
        ItemCooldown tracker = getCooldownTracker(player);
        Map<Item, ItemCooldown.Info> map = tracker.cooldowns;
        ItemCooldown.Info info = map.get(CraftItemStack.asNMSCopy(new ItemStack(material)).getItem());
        if (info == null) return 0;
        return info.endTick - tracker.currentTick;
    }
}
