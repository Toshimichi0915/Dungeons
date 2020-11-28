package net.toshimichi.dungeons.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.security.SecureRandom;
import java.util.Random;

/**
 * モブのスポーンに関するユーティリティです.
 */
public class MobUtils {

    private static final Random random = new SecureRandom();

    private static boolean checkBox(int width, int height, Location loc) {
        for (int x = loc.getBlockX() - width / 2; x <= loc.getBlockX() + width / 2; x++) {
            for (int y = loc.getBlockY(); y < loc.getBlockY() + height; x++) {
                for (int z = loc.getBlockZ() - width / 2; z <= loc.getBlockZ() + width / 2; x++) {
                    if (!loc.getBlock().isPassable()) return false;
                }
            }
        }
        return true;
    }

    /**
     * モブがスポーンできるかどうか調べます.
     *
     * @param type スポーンさせたいモブの種類
     * @param loc  スポーンさせる中央となる場所
     * @return スポーンできる場合は {@code true} そうでない場合は {@code false}
     */
    public static boolean canSpawn(EntityType type, Location loc) {
        switch (type) {
            case COD:
            case PUFFERFISH:
            case TROPICAL_FISH:
            case SALMON:
            case TURTLE:
            case RABBIT:
            case DOLPHIN:
            case CHICKEN:
            case CAT:
            case OCELOT:
            case WOLF:
            case PIG:
            case ENDERMITE:
            case SILVERFISH:
            case CAVE_SPIDER:
            case PHANTOM:
            case VEX:
            case GUARDIAN:
            case SPIDER:
            case SQUID:
            case BAT:
            case PARROT:
                return checkBox(1, 1, loc);
            case SHEEP:
            case COW:
            case MUSHROOM_COW:
            case LLAMA:
            case SHULKER:
            case ZOMBIE:
            case HUSK:
            case DROWNED:
            case CREEPER:
            case BLAZE:
            case EVOKER:
            case VILLAGER:
            case WITCH:
            case VINDICATOR:
            case ILLUSIONER:
            case ZOMBIFIED_PIGLIN:
            case PILLAGER:
            case SKELETON:
            case STRAY:
            case SNOWMAN:
            case PLAYER:
                return checkBox(1, 2, loc);
            case PANDA:
            case POLAR_BEAR:
            case HORSE:
            case ELDER_GUARDIAN:
                return checkBox(2, 1, loc);
            case RAVAGER:
            case WITHER:
                return checkBox(2, 3, loc);
            case SLIME:
            case MAGMA_CUBE:
                return checkBox(3, 3, loc);
            case GHAST:
                return checkBox(4, 4, loc);
            case GIANT:
                return checkBox(4, 12, loc);
            case ENDER_CRYSTAL:
                return checkBox(16, 8, loc);
            default:
                return true;
        }
    }

    /**
     * モブのスポーンを試行します.
     *
     * @param type  スポーンさせるモブの種類
     * @param range スポーンさせるモブの中心が取りうる範囲
     * @param world スポーンさせるワールド
     * @return スポーンできた場合はその {@link Entity} そうでない場合は {@code null}
     */
    public static Entity tryRandomSpawn(EntityType type, Range range, World world) {
        int randomX = random.nextInt(range.getXLength()) + range.getMinPoint().getX();
        int randomY = random.nextInt(range.getYLength()) + range.getMinPoint().getY();
        int randomZ = random.nextInt(range.getZLength()) + range.getMinPoint().getZ();
        Location loc = new Location(world, randomX, randomY, randomZ);
        if (!canSpawn(type, loc)) return null;
        return world.spawnEntity(loc, type);
    }
}
