package net.toshimichi.dungeons.economy;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class SimpleEconomy implements Economy {

    private final Map<UUID, Integer> cache = new WeakHashMap<>();
    private final File baseDir;

    public SimpleEconomy(File baseDir) {
        this.baseDir = baseDir;
    }

    private File getFile(UUID uuid) {
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

    private YamlConfiguration getYaml(UUID uuid) {
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
    public int getMoney(UUID uuid) {
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
    public void setMoney(UUID uuid, int money) {
        cache.put(uuid, money);
    }

    @Override
    public boolean withdraw(UUID uuid, int money) {
        int after = getMoney(uuid) - money;
        if (after < 0) return false;
        setMoney(uuid, money);
        return true;
    }

    @Override
    public void deposit(UUID uuid, int money) {
        setMoney(uuid, getMoney(uuid) + money);
    }

    @Override
    public void save(UUID uuid) {
        YamlConfiguration conf = getYaml(uuid);
        conf.set("money", getMoney(uuid));
        try {
            conf.save(getFile(uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll() {
        cache.keySet().forEach(this::save);
    }
}
