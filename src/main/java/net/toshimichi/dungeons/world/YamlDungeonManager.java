package net.toshimichi.dungeons.world;

import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class YamlDungeonManager implements DungeonManager {

    private final File baseDir;
    private final ArrayList<DungeonFactory> dungeonFactories;
    private final ArrayList<DungeonData> dungeons = new ArrayList<>();
    private int dungeonCounter;

    public YamlDungeonManager(File baseDir, List<DungeonFactory> dungeonFactories) {
        this.baseDir = baseDir;
        this.dungeonFactories = new ArrayList<>(dungeonFactories);
    }

    @Override
    public List<Dungeon> getDungeons() {
        return dungeons.stream()
                .map(data -> data.dungeon)
                .collect(Collectors.toList());
    }

    @Override
    public List<DungeonFactory> getDungeonFactories() {
        return new ArrayList<>(dungeonFactories);
    }

    @Override
    public Dungeon newDungeon(String factoryId) {
        DungeonFactory factory = getDungeonFactoryById(factoryId);
        if (factory == null) {
            throw new IllegalStateException("Specified factory ID is not in the dungeon manager: " + factoryId);
        }
        String dungeonId = "dungeon-" + dungeonCounter++;
        Dungeon dungeon = factory.newDungeon(new File(baseDir, "world/" + dungeonId + "rooms"), dungeonId);
        dungeons.add(new DungeonData(dungeon, factory));
        return dungeon;
    }

    @Override
    public void load() {
        File[] listFiles = baseDir.listFiles();
        if (listFiles == null) {
            return;
        }
        dungeonCounter = 0;
        for (File dungeonFile : listFiles) {
            YamlConfiguration dungeonConfig = new YamlConfiguration();
            try {
                dungeonConfig.load(new File(dungeonFile, "config.yaml"));
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
            String dungeonId = dungeonConfig.getString("id");
            DungeonFactory dungeonFactory = getDungeonFactoryById(dungeonConfig.getString("factory"));
            if (dungeonFactory == null) {
                continue;
            }
            Dungeon dungeon = dungeonFactory.newDungeon(new File(dungeonFile, "rooms"), dungeonId);
            dungeon.load(dungeonConfig);
            dungeons.add(new DungeonData(dungeon, dungeonFactory));
            dungeonCounter++;
        }
    }

    @Override
    public void save() {
        for (DungeonData dungeonData : dungeons) {
            Dungeon dungeon = dungeonData.dungeon;
            YamlConfiguration dungeonConfig = new YamlConfiguration();
            dungeon.save(dungeonConfig);
            dungeonConfig.set("id", dungeon.getId());
            dungeonConfig.set("factory", dungeonData.dungeonFactory.getId());
            try {
                dungeonConfig.save(new File(baseDir, dungeon.getId() + "/config.yaml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private DungeonFactory getDungeonFactoryById(String factoryId) {
        for (DungeonFactory factory : dungeonFactories) {
            if (factory.getId().equals(factoryId)) {
                return factory;
            }
        }
        return null;
    }

    private static class DungeonData {
        Dungeon dungeon;
        DungeonFactory dungeonFactory;

        public DungeonData(Dungeon dungeon, DungeonFactory dungeonFactory) {
            this.dungeon = dungeon;
            this.dungeonFactory = dungeonFactory;
        }
    }
}
