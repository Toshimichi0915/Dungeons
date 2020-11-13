package net.toshimichi.dungeons.utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemStackUtils {
    public static void setDisplay(ItemStack itemStack, String... display) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : display)
            list.addAll(Arrays.asList(s.split("\n")));
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
