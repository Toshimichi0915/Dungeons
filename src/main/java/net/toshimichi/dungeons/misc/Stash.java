package net.toshimichi.dungeons.misc;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * アイテムの格納目的で作られたクラスです.
 * このインターフェースのメソッドはスレッドセーフであることが保証されます.
 */
public class Stash {

    private static Map<UUID, YamlConfiguration> cache = new WeakHashMap<>();

    private final File baseDir;

    /**
     * データフォルダを指定してインスタンスを作成します.
     *
     * @param baseDir データフォルダ
     */
    public Stash(File baseDir) {
        this.baseDir = baseDir;
    }

    private synchronized File getFile(UUID uuid) {
        return new File(baseDir, uuid.toString() + ".yaml");
    }

    private synchronized YamlConfiguration getYaml(UUID uuid) throws IOException {
        YamlConfiguration result = cache.get(uuid);
        if (result != null) return result;
        result = new YamlConfiguration();
        File file = getFile(uuid);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            result.load(getFile(uuid));
        } catch (InvalidConfigurationException e) {
            throw new IOException(e);
        }
        cache.put(uuid, result);
        return result;
    }

    /**
     * Stashをセーブします.
     *
     * @param uuid プレイヤーの {@link UUID}
     * @throws IOException セーブできなかった場合
     */
    public synchronized void save(UUID uuid) throws IOException {
        YamlConfiguration conf = cache.get(uuid);
        if (conf == null) return;
        conf.save(getFile(uuid));
    }

    /**
     * ロードされているStashを全てセーブします
     *
     * @throws IOException セーブできなかった場合
     */
    public synchronized void saveAll() throws IOException {
        for (Map.Entry<UUID, YamlConfiguration> entry : cache.entrySet()) {
            entry.getValue().save(getFile(entry.getKey()));
        }
    }

    /**
     * 利用できるStashを返します.
     *
     * @param uuid プレイヤーの {@link UUID}
     * @return 利用できるStashの一覧
     * @throws IOException ロードできなかった場合
     */
    public synchronized Set<String> getStashes(UUID uuid) throws IOException {
        return getYaml(uuid).getKeys(false);
    }

    /**
     * Stashの中身を設定します.
     *
     * @param uuid       プレイヤーの {@link UUID}
     * @param space      Stashの名前
     * @param itemStacks Stashの中身
     * @throws IOException ロードできなかった場合
     */
    public synchronized void setItemStacks(UUID uuid, String space, ItemStack... itemStacks) throws IOException {
        YamlConfiguration conf = getYaml(uuid);
        conf.set(space, Arrays.asList(itemStacks));
    }

    /**
     * Stashからアイテム一覧を取得します.
     *
     * @param uuid  プレイヤーの {@link UUID}
     * @param space Stashの名前
     * @return アイテム一覧
     * @throws IOException ロードできなかった場合
     */
    public synchronized List<ItemStack> getItemStacks(UUID uuid, String space) throws IOException {
        YamlConfiguration conf = getYaml(uuid);
        List<?> list = conf.getList(space, new ArrayList<>());
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for (Object o : list)
            if (o instanceof ItemStack)
                itemStacks.add((ItemStack) o);
        return itemStacks;
    }

    /**
     * Stashからアイテム一覧を取得します.
     * ロードできなかった場合空の {@link List} を返します.
     *
     * @param uuid  プレイヤーの {@link UUID}
     * @param space Stashの名前
     * @return アイテム一覧
     */
    public synchronized List<ItemStack> getItemStacksSilently(UUID uuid, String space) {
        try {
            return getItemStacks(uuid, space);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Stashにアイテムを追加します.
     *
     * @param uuid       プレイヤーの {@link UUID}
     * @param space      Stashの名前
     * @param itemStacks 追加するアイテム
     * @throws IOException ロードできなかった場合
     */
    public synchronized void addItemStack(UUID uuid, String space, ItemStack... itemStacks) throws IOException {
        List<ItemStack> list = getItemStacks(uuid, space);
        list.addAll(Arrays.asList(itemStacks));
        getYaml(uuid).set(space, list);
    }

    /**
     * Stashから指定された個数アイテムを削除します.
     * 指定された個数がStashにある個数よりも多い場合、Stash内にある指定されたアイテムが全て削除されます.
     *
     * @param uuid       プレイヤーの {@link UUID}
     * @param space      Stashの名前
     * @param itemStacks 削除するアイテム
     * @throws IOException ロードできなかった場合
     */
    public synchronized void removeItemStack(UUID uuid, String space, ItemStack... itemStacks) throws IOException {
        List<ItemStack> list = getItemStacks(uuid, space);
        for (ItemStack itemStack : itemStacks) {
            int remaining = itemStack.getAmount();
            Iterator<ItemStack> iterator = list.iterator();
            while (iterator.hasNext()) {
                ItemStack element = iterator.next();
                if (!element.isSimilar(itemStack)) continue;
                if (remaining >= element.getAmount()) {
                    iterator.remove();
                } else {
                    element.setAmount(element.getAmount() - remaining);
                }
                remaining -= element.getAmount();
                if (remaining <= 0)
                    break;
            }
        }
        setItemStacks(uuid, space, itemStacks);
    }

    /**
     * Stashの中身を空にします.
     *
     * @param uuid  プレイヤーの {@link UUID}
     * @param space Stashの名前
     * @throws IOException ロードできなかった場合
     */
    public synchronized void clearStash(UUID uuid, String space) throws IOException {
        YamlConfiguration conf = getYaml(uuid);
        conf.set(space, null);
    }

}
