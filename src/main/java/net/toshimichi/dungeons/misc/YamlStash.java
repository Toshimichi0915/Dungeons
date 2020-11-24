package net.toshimichi.dungeons.misc;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class YamlStash implements Stash {

    private static Map<UUID, YamlConfiguration> cache = new WeakHashMap<>();

    private final File baseDir;

    /**
     * データフォルダを指定してインスタンスを作成します.
     *
     * @param baseDir データフォルダ
     */
    public YamlStash(File baseDir) {
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

    @Override
    public synchronized void save(UUID uuid) throws IOException {
        YamlConfiguration conf = cache.get(uuid);
        if (conf == null) return;
        conf.save(getFile(uuid));
    }

    @Override
    public synchronized void saveAll() throws IOException {
        for (Map.Entry<UUID, YamlConfiguration> entry : cache.entrySet()) {
            entry.getValue().save(getFile(entry.getKey()));
        }
    }

    @Override
    public synchronized Set<String> getStashes(UUID uuid) throws IOException {
        return getYaml(uuid).getKeys(false);
    }

    @Override
    public synchronized void setItemStacks(UUID uuid, String space, ItemStack... itemStacks) throws IOException {
        YamlConfiguration conf = getYaml(uuid);
        conf.set(space, Arrays.asList(itemStacks));
    }

    @Override
    public synchronized List<ItemStack> getItemStacks(UUID uuid, String space) throws IOException {
        YamlConfiguration conf = getYaml(uuid);
        List<?> list = conf.getList(space, new ArrayList<>());
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for (Object o : list)
            if (o instanceof ItemStack)
                itemStacks.add((ItemStack) o);
        return itemStacks;
    }

    @Override
    public synchronized List<ItemStack> getItemStacksSilently(UUID uuid, String space) {
        try {
            return getItemStacks(uuid, space);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public synchronized void addItemStack(UUID uuid, String space, ItemStack... itemStacks) throws IOException {
        List<ItemStack> list = getItemStacks(uuid, space);
        list.addAll(Arrays.asList(itemStacks));
        getYaml(uuid).set(space, list);
    }

    @Override
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

    @Override
    public synchronized void clearStash(UUID uuid, String space) throws IOException {
        YamlConfiguration conf = getYaml(uuid);
        conf.set(space, null);
    }

}
