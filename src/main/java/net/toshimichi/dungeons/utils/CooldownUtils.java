package net.toshimichi.dungeons.utils;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.Item;
import net.minecraft.server.v1_15_R1.ItemCooldown;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * アイテムのクールダウンをNMSを利用して管理するクラスです.
 */
public class CooldownUtils {

    private static ItemCooldown getCooldownTracker(Player player) {
        return ((CraftPlayer)player).getHandle().getCooldownTracker();
    }

    /**
     * 指定されたアイテムに関してクールダウンを設定します.
     * クールダウン中は指定されたアイテムが使えなくなります.
     * @param player クールダウンの影響を受けるプレイヤー
     * @param material クールダウンを設定するアイテム
     * @param tick クールダウンの期間 (tick)
     */
    public static void setCooldown(Player player, Material material, int tick) {
        getCooldownTracker(player).setCooldown(CraftItemStack.asNMSCopy(new ItemStack(material)).getItem(), tick);
    }

    /**
     * 指定されたアイテムに関してプレイヤーが持つクールダウンを返します.
     * クールダウン中は指定されたアイテムが使えなくなります.
     * @param player クールダウンの影響を受けるプレイヤー
     * @param material クールダウンが設定されたアイテム
     * @return クールダウンの期間 ( tick )
     */
    public static int getCooldown(Player player, Material material) {
        ItemCooldown tracker = getCooldownTracker(player);
        Map<Item, ItemCooldown.Info> map = tracker.cooldowns;
        ItemCooldown.Info info = map.get(CraftItemStack.asNMSCopy(new ItemStack(material)).getItem());
        if(info == null) return 0;
        return info.endTick - tracker.currentTick;
    }
}
