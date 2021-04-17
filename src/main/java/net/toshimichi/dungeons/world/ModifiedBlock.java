package net.toshimichi.dungeons.world;

import net.toshimichi.dungeons.utils.CompleteBlock;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

final public class ModifiedBlock implements ConfigurationSerializable {

    private final long timeStamp;
    private final CompleteBlock completeBlock;

    public ModifiedBlock(long timeStamp, CompleteBlock completeBlock) {
        this.timeStamp = timeStamp;
        this.completeBlock = completeBlock;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public CompleteBlock getCompleteBlock() {
        return completeBlock;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeStamp", timeStamp);
        result.put("completeBlock", completeBlock);
        return result;
    }

    public static ModifiedBlock deserialize(Map<String, Object> map) {
        long timeStamp = (long) map.get("timeStamp");
        CompleteBlock completeBlock = (CompleteBlock) map.get("completeBlock");
        return new ModifiedBlock(timeStamp, completeBlock);
    }
}
