package net.toshimichi.dungeons.enchants.bow.telebow;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.Enchanter;
import net.toshimichi.dungeons.enchants.bow.BowEnchanter;
import net.toshimichi.dungeons.utils.LocaleBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

public class TelebowEnchanter extends BowEnchanter implements Listener {

    private static final WeakHashMap<Player, Integer> cooldown = new WeakHashMap<>();
    private static final WeakHashMap<Player, Arrow> arrows = new WeakHashMap<>();

    static {
        Bukkit.getScheduler().runTaskTimer(DungeonsPlugin.getPlugin(), () -> {
            for (Map.Entry<Player, Integer> entry : cooldown.entrySet()) {
                int next = entry.getValue() - 1;
                if (next == 0)
                    cooldown.remove(entry.getKey());
                else
                    cooldown.put(entry.getKey(), next);
            }
            for (Arrow arrow : arrows.values()) {
                arrow.getWorld().spawnParticle(Particle.SPELL_MOB, arrow.getLocation(), 1);
            }
        }, 1, 1);
    }

    public TelebowEnchanter(Enchant enchant, Player player, ItemStack itemStack) {
        super(enchant, player, itemStack);
    }

    @Override
    protected void onEnabled() {
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
    }

    @Override
    public void tick() {
    }

    @Override
    protected void onDisabled() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onShoot(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof Arrow)) return;
        Arrow entity = (Arrow) e.getEntity();
        if (!getPlayer().equals(entity.getShooter())) return;
        if (!getPlayer().isSneaking()) return;
        if (cooldown.getOrDefault(getPlayer(), 0) > 0) {
            String message = new LocaleBuilder("enchant.telebow.cooldown")
                    .player(getPlayer())
                    .replace("{seconds}", Integer.toString(cooldown.get(getPlayer()) / 20))
                    .build();
            getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            return;
        }
        int timer;
        if (getEnchant().getLevel() == 1)
            timer = 300;
        else if (getEnchant().getLevel() == 2)
            timer = 180;
        else
            timer = 120;
        cooldown.put(getPlayer(), timer * 20);
        arrows.put(getPlayer(), entity);
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if (!arrows.containsValue(e.getEntity())) return;
        Arrow arrow = (Arrow) e.getEntity();
        arrows.remove(getPlayer());
        if (!getPlayer().equals(arrow.getShooter())) return;
        Location loc = arrow.getLocation();
        loc.setDirection(getPlayer().getLocation().getDirection());
        getPlayer().teleport(loc);
        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.7F, 2);
    }
}
