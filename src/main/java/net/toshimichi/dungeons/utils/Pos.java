package net.toshimichi.dungeons.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 整数で表される座標です.
 */
public class Pos implements Cloneable, Serializable, ConfigurationSerializable {
    private final int x;
    private final int y;
    private final int z;

    /**
     * 空間座標を元にインスタンスを作成します.
     *
     * @param x x座標
     * @param y y座標
     * @param z z座標
     */
    public Pos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * x座標を取得します
     *
     * @return x座標
     */
    public int getX() {
        return x;
    }

    /**
     * y座標を取得します
     *
     * @return y座標
     */
    public int getY() {
        return y;
    }

    /**
     * z座標を取得します
     *
     * @return z座標
     */
    public int getZ() {
        return z;
    }

    /**
     * このインスタンスが表す空間座標に指定されたインスタンスが表す空間座標をそれぞれ足し合わせ返します.
     *
     * @param pos 足し合わせる空間座標を持つインスタンス
     * @return 足し合わされた空間座標を持つ新規インスタンス
     */
    public Pos add(Pos pos) {
        return new Pos(x + pos.x, y + pos.y, z + pos.z);
    }

    /**
     * このインスタンスが表す空間座標に指定されたインスタンスが表す空間座標をそれぞれ引き返します.
     *
     * @param pos 引く空間座標を持つインスタンス
     * @return 引く空間座標を持つ新規インスタンス
     */
    public Pos subtract(Pos pos) {
        return new Pos(x - pos.x, y - pos.y, z - pos.z);
    }

    /**
     * このインスタンスが表す空間座標に指定されたインスタンスが表す空間座標をそれぞれかけて返します.
     *
     * @param pos かける空間座標を持つインスタンス
     * @return かけた空間座標を持つ新規インスタンス
     */
    public Pos multiply(Pos pos) {
        return new Pos(x * pos.x, y * pos.y, z * pos.z);
    }

    /**
     * このインスタンスが表す空間座標に指定されたインスタンスが表す空間座標をそれぞれ割って返します.
     *
     * @param pos 割る空間座標を持つインスタンス
     * @return 割った空間座標を持つ新規インスタンス
     */
    public Pos divine(Pos pos) {
        return new Pos(x / pos.x, y / pos.y, z / pos.z);
    }

    /**
     * このインスタンスを {@link Location} に変換します.
     *
     * @param world {@link Location#getWorld()}
     * @return 変換された {@link Location}
     */
    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pos pos = (Pos) o;

        if (x != pos.x) return false;
        if (y != pos.y) return false;
        return z == pos.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    protected Pos clone() {
        try {
            return (Pos) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        return map;
    }

    /**
     * {@link #serialize()} により返された {@link Map} を用いて非直列化を行います.
     *
     * @param map {@link #serialize()} により返された直列化済みインスタンス
     * @return 非直列化されたインスタンス
     */
    public static Pos deserialize(Map<String, Object> map) {
        try {
            return new Pos((Integer) map.get("x"), (Integer) map.get("y"), (Integer) map.get("z"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
