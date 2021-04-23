package net.toshimichi.dungeons.utils;

import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.regions.Region;
import net.royawesome.jlibnoise.MathHelper;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 1つの {@link Pos} により示される空間上の範囲を示します.
 */
final public class Range implements Cloneable, Serializable, ConfigurationSerializable {
    private final Pos pos1;
    private final Pos pos2;

    // lazy-init
    private Pos minPoint;
    private Pos maxPoint;

    /**
     * 1つの {@link Pos} によって範囲を指定しインスタンスを作成します.
     *
     * @param pos1 1つ目の空間座標を持つインスタンス
     * @param pos2 2つめの空間座標を持つインスタンス
     */
    public Range(Pos pos1, Pos pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    /**
     * {@link Region} に対応するインスタンスを作成します.
     * 1つ目の空間座標は {@link Region#getMinimumPoint()} に対応し, 2つ目の空間座標は
     * {@link Region#getMaximumPoint()} に対応することが保証されます.
     *
     * @param region 対応する {@link Region}
     */
    public Range(Region region) {
        this.pos1 = new Pos(region.getMinimumPoint());
        this.pos2 = new Pos(region.getMaximumPoint());
        this.minPoint = this.pos1;
        this.maxPoint = this.pos2;
    }

    /**
     * 1つ目の空間座標を持つインスタンスを返します.
     *
     * @return 1つ目の空間座標を持つインスタンス
     */
    public Pos getPos1() {
        return pos1;
    }

    /**
     * 2つ目の空間座標を持つインスタンスを返します.
     *
     * @return 2つ目の空間座標を持つインスタンス
     */
    public Pos getPos2() {
        return pos2;
    }

    /**
     * 範囲によって示される、x y z座標が共に最も小さい地点を返します.
     *
     * @return x y z座標が共に最も小さい地点
     */
    public Pos getMinPoint() {
        if (minPoint == null) {
            int x = Math.min(pos1.getX(), pos2.getX());
            int y = Math.min(pos1.getY(), pos2.getY());
            int z = Math.min(pos1.getZ(), pos2.getZ());
            minPoint = new Pos(x, y, z);
        }
        return minPoint;
    }

    /**
     * 範囲によって示される、x y z座標が共に最も大さい地点を返します.
     *
     * @return x y z座標が共に最も大さい地点
     */
    public Pos getMaxPoint() {
        if (maxPoint == null) {
            int x = Math.max(pos1.getX(), pos2.getX());
            int y = Math.max(pos1.getY(), pos2.getY());
            int z = Math.max(pos1.getZ(), pos2.getZ());
            maxPoint = new Pos(x, y, z);
        }
        return maxPoint;
    }

    @Override
    public String toString() {
        return "Range{" +
                "pos1=" + pos1 +
                ", pos2=" + pos2 +
                ", minPoint=" + minPoint +
                ", maxPoint=" + maxPoint +
                '}';
    }

    /**
     * 最小地点と最大地点の間を線形補間します.
     *
     * @param x 0から1までの値
     * @param y 0から1までの値
     * @param z 0から1までの値
     * @return 線形補間された座標
     */
    public Pos lerp(double x, double y, double z) {
        x = MathHelper.clamp(x, 0, 1);
        y = MathHelper.clamp(y, 0, 1);
        z = MathHelper.clamp(z, 0, 1);
        if (x == 0 && y == 0 && z == 0) {
            return getMinPoint();
        } else if (x == 1 && y == 1 && z == 1) {
            return getMaxPoint();
        }
        Pos min = getMinPoint();
        Pos max = getMaxPoint();
        return new Pos(MathHelper.lerp(min.getX(), max.getX(), x),
                MathHelper.lerp(min.getY(), max.getY(), y),
                MathHelper.lerp(min.getZ(), max.getZ(), z));
    }

    /**
     * 指定された座標が範囲の中に含まれる場合は {@code true} そうでない場合は {@code false} を返します.
     *
     * @param pos 範囲の中にあるか確認したい座標
     * @return 範囲の中にある場合は {@code true} そうでなければ {@code false}
     */
    public boolean contains(Pos pos) {
        Pos min = getMinPoint();
        Pos max = getMaxPoint();
        return min.getX() <= pos.getX() && pos.getX() <= max.getX() &&
                min.getY() <= pos.getY() && pos.getY() <= max.getY() &&
                min.getZ() <= pos.getZ() && pos.getZ() <= max.getZ();
    }

    /**
     * 範囲のx座標に関する長さを求めます.
     *
     * @return x座標に関する長さ
     */
    public int getXLength() {
        return getMaxPoint().getX() - getMinPoint().getX();
    }

    /**
     * 範囲のy座標に関する長さを求めます.
     *
     * @return y座標に関する長さ
     */
    public int getYLength() {
        return getMaxPoint().getY() - getMinPoint().getY();
    }

    /**
     * 範囲のz座標に関する長さを求めます.
     *
     * @return z座標に関する長さ
     */
    public int getZLength() {
        return getMaxPoint().getZ() - getMinPoint().getZ();
    }

    /**
     * 範囲内に存在する整数値を持つ空間座標全てに対して指定された処理を実行します.
     * 複数回このメソッドを実行したとき、全ての場合において同じ順番で実行されることが保証されます.
     *
     * @param consumer 空間座標に対して行う処理
     */
    public void forEach(Consumer<Pos> consumer) {
        Pos min = getMinPoint();
        Pos max = getMaxPoint();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getX(); y <= max.getX(); y++) {
                for (int z = min.getX(); z <= max.getX(); z++) {
                    consumer.accept(new Pos(x, y, z));
                }
            }
        }
    }

    /**
     * このインスタンスによって表される立方体を, 指定された方向ベクトルに並行移動させます.
     *
     * @param pos 方向ベクトル
     * @return 平行移動された立方体
     */
    public Range move(Pos pos) {
        return new Range(getPos1().add(pos), getPos2().add(pos));
    }

    /**
     * このインスタンスによって表される立方体を, 指定された軸回りに回転させます.
     *
     * @param x {@code x軸の回転角 / 90}
     * @param y {@code y軸の回転角 / 90}
     * @param z {@code z軸の回転角 / 90}
     * @return 回転された立方体
     */
    public Range rotate(int x, int y, int z) {
        AffineTransform transform = new AffineTransform()
                .rotateX(-x * 90).rotateY(-y * 90).rotateZ(-z * 90);
        return new Range(new Pos(transform.apply(getPos1().toVector3())),
                new Pos(transform.apply(getPos2().toVector3())));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (!pos1.equals(range.pos1)) return false;
        return pos2.equals(range.pos2);
    }

    @Override
    public int hashCode() {
        int result = pos1 != null ? pos1.hashCode() : 0;
        result = 31 * result + (pos2 != null ? pos2.hashCode() : 0);
        return result;
    }

    @Override
    protected Range clone() {
        try {
            return (Range) super.clone(); //Pos も final であることに注意
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("pos1", pos1);
        map.put("pos2", pos2);
        return map;
    }

    /**
     * {@link #serialize()} により返された {@link Map} を用いて非直列化を行います.
     *
     * @param map {@link #serialize()} により返された直列化済みインスタンス
     * @return 非直列化されたインスタンス
     */
    public static Range deserialize(Map<String, Object> map) {
        return new Range((Pos) map.get("pos1"), (Pos) map.get("pos2"));
    }
}
