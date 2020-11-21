package net.toshimichi.dungeons.music;

import org.bukkit.Sound;

public class SimpleMusicSound implements MusicSound {

    private final Sound type;
    private final float pitch;
    private final float volume;

    public SimpleMusicSound(Sound type, float pitch, float volume) {
        this.type = type;
        this.pitch = pitch;
        this.volume = volume;
    }

    @Override
    public Sound getType() {
        return type;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getVolume() {
        return volume;
    }
}
