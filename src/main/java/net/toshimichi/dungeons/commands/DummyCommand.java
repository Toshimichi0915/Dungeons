package net.toshimichi.dungeons.commands;

import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DummyCommand implements PlayerCommand {
    @Override
    public void onCommand(Player player, Arguments arguments, String cmd) {
        World world = player.getWorld();
        Pig pig = (Pig) world.spawnEntity(player.getLocation(), EntityType.PIG);
        pig.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(150);
        pig.setHealth(150);
        pig.setAI(false);
        pig.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, 5));
    }

    @Override
    public String getDescription() {
        return "攻撃用のダミーを出現させます";
    }

    @Override
    public String getPermission() {
        return "net.toshimichi.dungeons.dummy";
    }
}
