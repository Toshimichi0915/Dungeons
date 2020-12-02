package net.toshimichi.dungeons.nat.api.nbt;

public class LocalNbtDouble implements NbtDouble {

    private final double d;

    public LocalNbtDouble(double d) {
        this.d = d;
    }

    @Override
    public float getAsFloat() {
        return (float)d;
    }

    @Override
    public double getAsDouble() {
        return d;
    }

    @Override
    public String toString() {
        return Double.toString(d);
    }
}
