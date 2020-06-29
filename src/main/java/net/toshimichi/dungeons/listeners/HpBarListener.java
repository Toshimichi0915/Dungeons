package net.toshimichi.dungeons.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.toshimichi.dungeons.events.BetterDamageEvent;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HpBarListener implements Listener {

    private static final int maxDisplayed = 10;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        BetterDamageEvent e = new BetterDamageEvent(event);
        if (!(e.getTrueDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;
        Player attacker = (Player) e.getTrueDamager();
        LivingEntity victim = (LivingEntity) e.getEntity();
        int maxHp = (int) Math.ceil(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2);
        int maxHpShown;
        int count = maxHp / maxDisplayed;
        if (count > 0) maxHpShown = maxDisplayed;
        else maxHpShown = maxHp;
        int absortionHp = (int) Math.ceil(victim.getAbsorptionAmount() / 2);
        int absortionHpRemaining = absortionHp + (int) Math.floor(e.getDamage(EntityDamageEvent.DamageModifier.ABSORPTION) / 2);
        int hpRemaining = (int) (victim.getHealth() - e.getFinalDamage()) / 2;
        int hpOld = (int) Math.ceil(victim.getHealth() / 2);
        if (hpRemaining < 0) hpRemaining = 0;
        int index = hpRemaining / maxDisplayed;
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.GRAY);
        if (victim instanceof Player) {
            builder.append(((Player) victim).getDisplayName());
            builder.append(' ');
        } else if (victim.getCustomName() != null) {
            builder.append(victim.getCustomName());
            builder.append(' ');
        }
        builder.append(ChatColor.DARK_RED);
        for (int i = 0; i < maxHpShown + absortionHp; i++) {
            if (i == hpRemaining % maxDisplayed)
                builder.append(ChatColor.RED);
            if (i == hpOld % maxDisplayed && i != 0 && hpOld % maxDisplayed > hpRemaining % maxDisplayed)
                builder.append(ChatColor.BLACK);
            if (index == count && i == maxHp % maxDisplayed && i != 0)
                builder.append(ChatColor.GRAY);
            if (i == maxHpShown)
                builder.append(ChatColor.YELLOW);
            if (i == maxHpShown + absortionHpRemaining)
                builder.append(ChatColor.GOLD);
            builder.append('❤');
        }
        builder.append(' ');
        if (index > 0) {
            builder.append(ChatColor.AQUA);
            builder.append(" x");
            builder.append(index + 1);
            builder.append(' ');
        }
        builder.append(ChatColor.RED);
        builder.append(String.format("%.1f", e.getFinalDamage()));

        attacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(builder.toString()));
    }
}
