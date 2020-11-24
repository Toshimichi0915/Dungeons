package net.toshimichi.dungeons.misc;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class YamlEconomy implements Economy {

    private final Map<UUID, Integer> cache = new WeakHashMap<>();
    private final File baseDir;

    public YamlEconomy(File baseDir) {
        this.baseDir = baseDir;
    }

    private synchronized File getFile(UUID uuid) {
        File f = new File(baseDir, uuid + ".yaml");
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return f;
    }

    private synchronized YamlConfiguration getYaml(UUID uuid) {
        File f = getFile(uuid);
        YamlConfiguration conf = new YamlConfiguration();
        try {
            conf.load(f);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        return conf;
    }

    @Override
    public synchronized int getMoney(UUID uuid) {
        Integer money = cache.get(uuid);
        if (money == null) {
            YamlConfiguration conf = getYaml(uuid);
            if (!conf.contains("money")) {
                cache.put(uuid, 0);
                return 0;
            }
            money = conf.getInt("money");
            cache.put(uuid, money);
        }
        return money;
    }

    @Override
    public synchronized boolean setMoney(UUID uuid, int money) {
        cache.put(uuid, money);
        return true;
    }

    @Override
    public synchronized boolean withdraw(UUID uuid, int money) {
        int after = getMoney(uuid) - money;
        if (after < 0) return false;
        setMoney(uuid, after);
        return true;
    }

    @Override
    public synchronized boolean deposit(UUID uuid, int money) {
        return setMoney(uuid, getMoney(uuid) + money);
    }

    @Override
    public synchronized void save(UUID uuid) throws IOException {
        YamlConfiguration conf = getYaml(uuid);
        conf.set("money", getMoney(uuid));
        conf.save(getFile(uuid));
    }

    @Override
    public synchronized void saveAll() throws IOException {
        for (UUID key : cache.keySet()) {
            save(key);
        }
    }
}
