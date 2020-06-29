package net.toshimichi.dungeons.misc;

import org.bukkit.GameMode;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.WeakHashMap;

public class SimpleManaManager implements ManaManager {

    private final WeakHashMap<Player, Integer> manaMap = new WeakHashMap<>();
    private final WeakHashMap<Player, Integer> maxManaMap = new WeakHashMap<>();
    private final File baseDir;

    public SimpleManaManager(File baseDir) {
        this.baseDir = baseDir;
    }

    private File getFile(Player player) {
        File f = new File(baseDir, player.getUniqueId() + ".yml");
        if (!f.exists()) {
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return f;
    }

    private YamlConfiguration getYaml(Player player) {
        YamlConfiguration conf = new YamlConfiguration();
        try {
            conf.load(getFile(player));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        return conf;
    }

    @Override
    public int getMana(Player player) {
        Integer mana = manaMap.get(player);
        if (mana != null) return mana;
        YamlConfiguration conf = getYaml(player);
        if (conf.contains("mana")) {
            return getYaml(player).getInt("mana");
        } else {
            int maxMana = getMaxMana(player);
            setMana(player, maxMana);
            return maxMana;
        }
    }

    @Override
    public int getMaxMana(Player player) {
        Integer maxMana = maxManaMap.get(player);
        if (maxMana != null) return maxMana;
        YamlConfiguration conf = getYaml(player);
        if (conf.contains("maxMana")) {
            return getYaml(player).getInt("maxMana");
        } else {
            setMaxMana(player, 100);
            return 100;
        }
    }

    @Override
    public void setMana(Player player, int mana) {
        manaMap.put(player, mana);
    }

    @Override
    public void setMaxMana(Player player, int mana) {
        maxManaMap.put(player, mana);
        if(getMana(player) > mana)
            setMana(player, mana);
    }

    @Override
    public boolean consumeMana(Player player, int mana) {
        if(player.getGameMode() == GameMode.CREATIVE) return true;
        int current = getMana(player);
        if (current < mana) return false;
        setMana(player, current - mana);
        return true;
    }

    @Override
    public void save(Player player) {
        Integer mana = manaMap.get(player);
        Integer maxMana = maxManaMap.get(player);
        if (mana == null && maxMana == null) return;
        YamlConfiguration conf = getYaml(player);
        if (mana != null)
            conf.set("mana", mana);
        if (maxMana != null)
            conf.set("maxMana", maxMana);
        try {
            conf.save(getFile(player));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll() {
        manaMap.keySet().forEach(this::save);
        maxManaMap.keySet().forEach(this::save);
    }
}
