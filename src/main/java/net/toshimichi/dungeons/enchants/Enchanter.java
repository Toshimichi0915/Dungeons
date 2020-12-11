package net.toshimichi.dungeons.enchants;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * エンチャントの実行クラスです.
 */
abstract public class Enchanter {

    private final Enchant enchant;
    private final Player player;
    private final ItemStack itemStack;

    private boolean enabled;

    public Enchanter(Enchant enchant, Player player, ItemStack itemStack) {
        this.enchant = enchant;
        this.player = player;
        this.itemStack = itemStack;
    }

    /**
     * エンチャントを有効にします.
     */
    public void enable() {
        if (!enabled)
            onEnabled();
        enabled = true;
    }

    /**
     * エンチャントが有効にされた時に実行されます.
     */
    abstract protected void onEnabled();

    /**
     * 1 tick の処理を実行します.
     */
    abstract public void tick();

    /**
     * エンチャントを無効にします.
     */
    public void disable() {
        if (enabled)
            onDisabled();
        enabled = false;
    }

    /**
     * エンチャントが無効にされた時に実行されます.
     */
    abstract protected void onDisabled();

    /**
     * エンチャントを返します.
     *
     * @return エンチャント
     */
    public Enchant getEnchant() {
        return enchant;
    }

    /**
     * エンチャントを利用しているプレイヤーを返します.
     *
     * @return エンチャントを利用しているプレイヤー
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * エンチャントが適用されているアイテムを返します.
     *
     * @return エンチャントが適用されているアイテム
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * エンチャントの有効、無効を返します.
     *
     * @return エンチャントが有効な場合は {@code true} そうでない場合は {@code false}
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * エンチャントを有効化できるかを返します.
     *
     * @return 有効化できる場合は {@code true} そうでなければ {@code false}
     */
    public boolean isAvailable() {
        return true;
    }
}
