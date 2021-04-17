package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.Pos;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class NormalActiveRoom implements ActiveRoom {

    private final NormalRoom room;
    private HashSet<ModifiedBlock> modifiedBlocks = new HashSet<>();

    public NormalActiveRoom(NormalRoom room) {
        this.room = room;
    }

    @Override
    public NormalRoom getRoom() {
        return room;
    }

    @Override
    public Collection<ModifiedBlock> getModifiedBlocks() {
        return new HashSet<>(modifiedBlocks);
    }

    @Override
    public void addModifiedBlock(ModifiedBlock block) {
        modifiedBlocks.add(block);
    }

    @Override
    public void removeModifiedBlock(Pos pos) {
        modifiedBlocks.removeIf(block -> block.getCompleteBlock().getPos().equals(pos));
    }

    @SuppressWarnings("unchecked")
    public void load(ConfigurationSection section) {
        modifiedBlocks = new HashSet<>((List<ModifiedBlock>) section.getList("modifiedBlocks"));
    }

    public void save(ConfigurationSection section) {
        section.set("modifiedBlocks", modifiedBlocks);
    }
}
