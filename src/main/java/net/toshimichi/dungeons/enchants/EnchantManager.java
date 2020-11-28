package net.toshimichi.dungeons.enchants;

import net.toshimichi.dungeons.lang.Locale;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * プレイヤーのエンチャントを管理します.
 */
public interface EnchantManager {

    /**
     * プレイヤーのエンチャントを更新します.
     *
     * @param player 更新するプレイヤー
     */
    void refresh(Player player);

    /**
     * プレイヤーの全エンチャントを無効にします.
     *
     * @param player 無効にするプレイヤー
     */
    void disable(Player player);

    /**
     * 1 tick 経過したことを通知します.
     */
    void tick();

    /**
     * アイテムに適用されているエンチャントを返します.
     *
     * @param itemStack 適用されているエンチャント
     * @return 適用されているエンチャント
     */
    Set<Enchant> getEnchants(ItemStack itemStack);

    /**
     * アイテムの名前や説明などを更新します.
     *
     * @param itemStack 更新するアイテム
     */
    void refresh(ItemStack itemStack);

    /**
     * アイテムにエンチャントを適用します.
     * エンチャント適用時にアイテムの名前や説明なども合わせて変更されます.
     *
     * @param itemStack 適用するアイテム
     * @param enchants  適用するエンチャント
     */
    void setEnchants(ItemStack itemStack, Enchant... enchants);

    /**
     * アイテムの階位を返します
     *
     * @param itemStack アイテム
     * @return アイテムの階位 設定されていない場合は {@code -1}
     */
    int getTier(ItemStack itemStack);

    /**
     * アイテムの階位を設定します.
     * アイテムの名前や説明なども自動で変更されます.
     *
     * @param itemStack アイテム
     * @param tier      アイテムの階位
     */
    void setTier(ItemStack itemStack, int tier);

    /**
     * アイテムの残り残機を返します.
     *
     * @param itemStack アイテム
     * @return 残り残機. 設定されていない場合は {@code -1}
     */
    int getLives(ItemStack itemStack);

    /**
     * アイテムの残り残機を設定します.
     * アイテムの名前や説明なども自動で変更されます.
     *
     * @param itemStack アイテム
     * @param lives     残り残機
     */
    void setLives(ItemStack itemStack, int lives);

    /**
     * アイテムの最大残り残機を返します.
     *
     * @param itemStack アイテム
     * @return 最大残り残機. 設定されていない場合は {@code -1}
     */
    int getMaxLives(ItemStack itemStack);

    /**
     * アイテムの最大残り残機を設定します.
     * アイテムの名前や説明なども自動で変更されます.
     *
     * @param itemStack アイテム
     * @param lives     最大残り残機
     */
    void setMaxLives(ItemStack itemStack, int lives);

    /**
     * アイテムに設定された言語情報を返します.
     *
     * @param itemStack アイテム
     * @return 言語情報. 存在しない場合は {@code null}
     */
    Locale getLocale(ItemStack itemStack);

    /**
     * アイテムの言語情報を設定します.
     *
     * @param itemStack アイテム
     * @param locale    言語情報. 設定を消去する場合は {@code null}
     */
    void setLocale(ItemStack itemStack, Locale locale);

    /**
     * プレイヤーに適用されているエンチャントを返します.
     *
     * @param player 適用されているプレイヤー
     * @return 適用されている {@link Enchanter} 一覧
     */
    Set<Enchanter> getEnchanters(Player player);

    /**
     * 利用できる全てのエンチャントを返します.
     *
     * @return 利用できる全てのエンチャント
     */
    Set<Enchant> getAllEnchants();

    /**
     * 指定されたIDとレベルを持ったエンチャントを返します.
     *
     * @param id    ID
     * @param level レベル
     * @return エンチャント, 存在しない場合は {@code null}
     */
    Enchant getEnchant(int id, int level);
}
