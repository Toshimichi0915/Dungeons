package net.toshimichi.dungeons.misc;

import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Stash {
    /**
     * Stashをセーブします.
     *
     * @param uuid プレイヤーの {@link UUID}
     * @throws IOException セーブできなかった場合
     */
    void save(UUID uuid) throws IOException;

    /**
     * ロードされているStashを全てセーブします
     *
     * @throws IOException セーブできなかった場合
     */
    void saveAll() throws IOException;

    /**
     * 利用できるStashを返します.
     *
     * @param uuid プレイヤーの {@link UUID}
     * @return 利用できるStashの一覧
     * @throws IOException ロードできなかった場合
     */
    Set<String> getStashes(UUID uuid) throws IOException;

    /**
     * Stashの中身を設定します.
     *
     * @param uuid       プレイヤーの {@link UUID}
     * @param space      Stashの名前
     * @param itemStacks Stashの中身
     * @throws IOException ロードできなかった場合
     */
    void setItemStacks(UUID uuid, String space, ItemStack... itemStacks) throws IOException;

    /**
     * Stashからアイテム一覧を取得します.
     *
     * @param uuid  プレイヤーの {@link UUID}
     * @param space Stashの名前
     * @return アイテム一覧
     * @throws IOException ロードできなかった場合
     */
    List<ItemStack> getItemStacks(UUID uuid, String space) throws IOException;

    /**
     * Stashからアイテム一覧を取得します.
     * ロードできなかった場合, 空の {@link List} を返します.
     *
     * @param uuid  プレイヤーの {@link UUID}
     * @param space Stashの名前
     * @return アイテム一覧
     */
    List<ItemStack> getItemStacksSilently(UUID uuid, String space);

    /**
     * Stashにアイテムを追加します.
     *
     * @param uuid       プレイヤーの {@link UUID}
     * @param space      Stashの名前
     * @param itemStacks 追加するアイテム
     * @throws IOException ロードできなかった場合
     */
    void addItemStack(UUID uuid, String space, ItemStack... itemStacks) throws IOException;

    /**
     * Stashから指定された個数アイテムを削除します.
     * 指定された個数がStashにある個数よりも多い場合、Stash内にある指定されたアイテムが全て削除されます.
     *
     * @param uuid       プレイヤーの {@link UUID}
     * @param space      Stashの名前
     * @param itemStacks 削除するアイテム
     * @throws IOException ロードできなかった場合
     */
    void removeItemStack(UUID uuid, String space, ItemStack... itemStacks) throws IOException;

    /**
     * Stashの中身を空にします.
     *
     * @param uuid  プレイヤーの {@link UUID}
     * @param space Stashの名前
     * @throws IOException ロードできなかった場合
     */
    void clearStash(UUID uuid, String space) throws IOException;
}
