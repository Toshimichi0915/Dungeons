package net.toshimichi.dungeons.nat.api.nbt;

public class LocalNbtFloat implements NbtFloat {

    private final float f;

    public LocalNbtFloat(float f) {
        this.f = f;
    }

    @Override
    public float getAsFloat() {
        return f;
    }

    @Override
    public String toString() {
        return Float.toString(f);
    }
}
