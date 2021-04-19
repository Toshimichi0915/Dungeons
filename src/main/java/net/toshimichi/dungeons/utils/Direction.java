package net.toshimichi.dungeons.utils;

public enum Direction {
    NORTH(0, -1, 0), EAST(1, 0, 90), SOUTH(0, 1, 180), WEST(-1, 0, 270);


    private final int x;
    private final int z;
    private final int angle;

    Direction(int x, int z, int angle) {
        this.x = x;
        this.z = z;
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getAngle(Direction direction) {
        int result = angle - direction.angle;
        if (result < 0) {
            result += 360;
        }
        return result;
    }
}
