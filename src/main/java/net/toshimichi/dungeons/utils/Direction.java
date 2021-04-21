package net.toshimichi.dungeons.utils;

import com.sk89q.worldedit.math.Vector2;
import org.bukkit.util.Vector;

/**
 * 方角を表します.
 */
public enum Direction {
    NORTH(0, -1, Vector2.at(0, -1)),
    EAST(1, 0, Vector2.at(1, 0)),
    SOUTH(0, 1, Vector2.at(0, 1)),
    WEST(-1, 0, Vector2.at(-1, 0));


    private final int x;
    private final int z;
    private final Vector2 vector;

    Direction(int x, int z, Vector2 vector) {
        this.x = x;
        this.z = z;
        this.vector = vector;
    }

    /**
     * 正規化された方角が示す方向ベクトルのX座標を返します.
     *
     * @return 正規化された方角が示す方向ベクトルのx座標
     */
    public int getX() {
        return x;
    }

    /**
     * 正規化された方角が示す方向ベクトルのZ座標を返します.
     *
     * @return 正規化された方角が示す方向ベクトルのx座標
     */
    public int getZ() {
        return z;
    }

    /**
     * この方角を {@code x} rad回転させたとき指定された方角になるような {@code x} を返します.
     *
     * @param direction 方角
     * @return {@code x}
     */
    public double rad(Direction direction) {
        return rad(direction.vector);
    }

    /**
     * この方角を {@code x} rad回転させたとき指定された方向ベクトルになるような {@code x} を返します.
     *
     * @param vector2 方角
     * @return {@code x}
     */
    public double rad(Vector vector2) {
        return rad(Vector2.at(vector2.getX(), vector2.getZ()));
    }

    /**
     * この方角を {@code x} rad回転させたとき指定された方向ベクトルになるような {@code x} を返します.
     *
     * @param vector2 方角
     * @return {@code x}
     */
    public double rad(Vector2 vector2) {
        return Math.atan2(vector.getX() * vector2.getZ() - vector2.getX() * vector.getZ(),
                vector.getX() * vector2.getX() + vector.getZ() * vector2.getZ());
    }

    /**
     * 方向ベクトルの方角を返します.
     * 方向ベクトルが45度の倍数の場合は, 結果が保証されないことに留意してください.
     *
     * @param vector 方向ベクトル
     * @return 方向ベクトルの方角
     */
    public static Direction getDirection(Vector vector) {
        return getDirection(Vector2.at(vector.getX(), vector.getZ()));
    }

    /**
     * 方向ベクトルの方角を返します.
     * 方向ベクトルが45度の倍数の場合は, 結果が保証されないことに留意してください.
     *
     * @param vector 方向ベクトル
     * @return 方向ベクトルの方角
     */
    public static Direction getDirection(Vector2 vector) {
        for (Direction direction : values()) {
            double degrees = Math.toDegrees(direction.rad(vector));
            if (-45 < degrees && degrees <= 45) {
                return direction;
            }
        }
        throw new RuntimeException();
    }
}
