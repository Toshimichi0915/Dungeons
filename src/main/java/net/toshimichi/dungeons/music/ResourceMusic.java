package net.toshimichi.dungeons.music;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.toshimichi.dungeons.DungeonsPlugin;
import org.apache.commons.io.IOUtils;
import org.bukkit.Sound;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 使用可能なリソースから {@link Music} を構成します.
 */
public class ResourceMusic implements Music {

    private final int length;
    private final Multimap<Integer, SimpleMusicSound> sounds = ArrayListMultimap.create();

    public ResourceMusic(String resourceName) throws IOException {
        String text = IOUtils.toString(DungeonsPlugin.getPlugin().getResource(resourceName), StandardCharsets.UTF_8);
        int length = 0;
        for (String line : text.split("[\\r\\n]+")) {
            String[] split = line.split("[\\s\\t,]+");
            try {
                int pos = Integer.parseInt(split[0]);
                float pitch = Float.parseFloat(split[1]);
                float volume = Float.parseFloat(split[2]);
                Sound type = Sound.valueOf(split[3]);
                if (pos > length)
                    length = pos;
                sounds.put(pos, new SimpleMusicSound(type, pitch, volume));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                //skip
            }
        }
        this.length = length;
    }

    @Override
    public MusicSound[] getMusicSounds(int pos) {
        return sounds.get(pos).toArray(new MusicSound[0]);
    }

    @Override
    public int getLength() {
        return length;
    }
}
