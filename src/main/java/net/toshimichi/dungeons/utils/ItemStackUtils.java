package net.toshimichi.dungeons.utils;

import net.toshimichi.dungeons.nat.api.LocaleLanguage;
import net.toshimichi.dungeons.nat.api.NbtItemStack;
import net.toshimichi.dungeons.nat.api.NbtItemStackFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.ServicesManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemStackUtils {

    public static String getDisplay(ItemStack itemStack) {
        ServicesManager services = Bukkit.getServicesManager();
        NbtItemStackFactory factory = services.load(NbtItemStackFactory.class);
        NbtItemStack nbtItemStack = factory.newNbtItemStack(itemStack);
        LocaleLanguage lang = services.load(LocaleLanguage.class);
        String name = nbtItemStack.getName();
        StringBuilder builder = new StringBuilder(name);
        builder.append('\n');
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                for (String s : lore) {
                    if (s.isEmpty())
                        s = " ";
                    builder.append(s);
                    builder.append('\n');
                }
            }
            if (!meta.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
                for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    builder.append(ChatColor.GRAY);
                    builder.append(lang.getMessage("enchantment.minecraft." + entry.getKey().getKey().getKey().toLowerCase()));
                    builder.append(' ');
                    builder.append(RomanNumber.convert(entry.getValue()));
                }
            }
        }
        return builder.toString();
    }

    public static void setDisplay(ItemStack itemStack, String... display) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : display)
            list.addAll(Arrays.asList(s.split("[\\r\\n]+")));
        if (list.isEmpty()) return;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        meta.setDisplayName(ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', list.get(0)));
        List<String> lore = list.subList(1, list.size());
        lore = lore.stream()
                .map(m -> ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', m))
                .collect(Collectors.toList());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }
}
