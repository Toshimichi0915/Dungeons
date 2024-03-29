package net.toshimichi.dungeons.enchants;

import net.toshimichi.dungeons.lang.Locale;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Map;

/**
 * 単一のエンチャントを表します.
 * インスタンスは複数のアイテムで共有可能です.
 */
public interface Enchant {

    /**
     * エンチャントのIDを返します.
     * 同一の内容で違うレベルのエンチャントは同じIDを持っていなくてはなりません.
     *
     * @return エンチャントのID
     */
    int getId();

    /**
     * エンチャントの表示用の名前を返します.
     *
     * @return エンチャントの名前
     */
    String getName();

    /**
     * エンチャントのレベルを返します.
     *
     * @return エンチャントのレベル
     */
    int getLevel();

    /**
     * エンチャントの出現比を返します.
     *
     * @return 出現比. 高いほうが出現確率が高い
     */
    int getRarity();

    /**
     * エンチャントの表示上の説明を返します.
     *
     * @param itemStack エンチャントされているアイテム
     * @param locale    使用する言語
     * @return エンチャントの表示上の説明
     */
    String getDescription(ItemStack itemStack, Locale locale);

    /**
     * エンチャントがつけられる部位を返します.
     *
     * @return エンチャントがつけられる部位
     */
    EnchantType[] getEnchantType();

    /**
     * エンチャントの題目を返します.
     *
     * @return エンチャントの題目
     */
    Title getTitle();

    /**
     * エンチャントの実行クラスを返します.
     *
     * @param player    エンチャントを適用するプレイヤー
     * @param itemStack エンチャントのついたアイテム
     * @return エンチャントの実行クラス
     */
    Enchanter getEnchanter(Player player, ItemStack itemStack);

    /**
     * このエンチャントの条件となる, マインクラフトのエンチャントを返します.
     *
     * @return このエンチャントを付与する際に必要となるマインクラフトのエンチャント
     */
    default Map<Enchantment, Integer> getEnchantments() {
        return Collections.emptyMap();
    }

}
