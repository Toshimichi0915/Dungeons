package net.toshimichi.dungeons.gui;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.lang.Locale;
import net.toshimichi.dungeons.misc.Stash;
import net.toshimichi.dungeons.utils.InventoryUtils;
import net.toshimichi.dungeons.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * エンチャントの管理GUIです.
 */
public class AdminEnchantGui implements Gui, Listener {

    private Player player;
    private Inventory inventory;

    private EnchantManager manager;
    private int id = 1;
    private int level = 1;
    private boolean updateGui;

    @Override
    public String getTitle(Player player) {
        return "Enchant Manager";
    }

    private ItemStack getAdminWell(Player player) {
        try {
            Stash stash = DungeonsPlugin.getStash();
            List<ItemStack> well = stash.getItemStacks(player.getUniqueId(), "admin_well");
            if (well.isEmpty())
                return null;
            return well.get(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setAdminWell(Player player, ItemStack itemStack) {
        try {
            if (itemStack != null)
                DungeonsPlugin.getStash().setItemStacks(player.getUniqueId(), "admin_well", itemStack);
            else
                DungeonsPlugin.getStash().clearStash(player.getUniqueId(), "admin_well");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GuiItem[] getItems() {
        GuiItem[] items = new GuiItem[36];
        items[10] = new PlainGuiItem(new ItemStack(Material.AIR), (p, g, i) -> {
            ItemStack itemStack = getAdminWell(p);
            if (itemStack == null) return;
            if (p.getInventory().addItem(itemStack).size() != 0) return;
            setAdminWell(p, null);
            updateGui = true;
        });
        items[12] = new PlainGuiItem(new ItemStack(Material.ENDER_EYE), (p, g, i) -> {
            manager.setLives(getAdminWell(p), manager.getLives(getAdminWell(p)) + 1);
            updateGui = true;
        });
        items[13] = new PlainGuiItem(new ItemStack(Material.PINK_DYE), (p, g, i) -> {
            manager.setMaxLives(getAdminWell(p), manager.getMaxLives(getAdminWell(p)) + 1);
            updateGui = true;
        });
        items[14] = new PlainGuiItem(new ItemStack(Material.LAVA_BUCKET), (p, g, i) -> {
            manager.setTier(getAdminWell(p), manager.getTier(getAdminWell(p)) + 1);
            updateGui = true;
        });
        items[15] = new PlainGuiItem(new ItemStack(Material.BLAZE_ROD), (p, g, i) -> {
            if (manager.getEnchant(++id, 1) == null)
                id = 1;
            if (manager.getEnchant(id, level) == null)
                level = 1;
            updateGui = true;
        });
        items[16] = new PlainGuiItem(new ItemStack(Material.STICK), (p, g, i) -> {
            if (manager.getEnchant(id, ++level) == null)
                level = 1;
            updateGui = true;
        });
        ItemStack book = new ItemStack(Material.BOOK);
        ItemStackUtils.setDisplay(book, ChatColor.GRAY + "錬金台のアイテムを編集できます");
        items[19] = new PlainGuiItem(book, new NullListener());
        items[21] = new PlainGuiItem(new ItemStack(Material.ENDER_PEARL), (p, g, i) -> {
            manager.setLives(getAdminWell(p), manager.getLives(getAdminWell(p)) - 1);
            updateGui = true;
        });
        items[22] = new PlainGuiItem(new ItemStack(Material.CYAN_DYE), (p, g, i) -> {
            manager.setMaxLives(getAdminWell(p), manager.getMaxLives(getAdminWell(p)) - 1);
            updateGui = true;
        });
        items[23] = new PlainGuiItem(new ItemStack(Material.WATER_BUCKET), (p, g, i) -> {
            manager.setTier(getAdminWell(p), manager.getTier(getAdminWell(p)) - 1);
            updateGui = true;
        });
        items[24] = new PlainGuiItem(new ItemStack(Material.GLOWSTONE_DUST), (p, g, i) -> {
            if (getAdminWell(p) == null) return;
            List<Locale> locales = DungeonsPlugin.getLocales();
            Locale locale = locales.get((locales.indexOf(manager.getLocale(getAdminWell(p))) + 1) % locales.size());
            manager.setLocale(getAdminWell(p), locale);
            updateGui = true;
        });
        items[25] = new PlainGuiItem(new ItemStack(Material.ENCHANTING_TABLE), (p, g, i) -> {
            Set<Enchant> enchants = DungeonsPlugin.getEnchantManager().getAllEnchants();
            Enchant e = enchants.stream()
                    .filter(p1 -> p1.getId() == id && p1.getLevel() == level)
                    .findAny().orElse(null);
            if (e == null) return;
            ItemStack itemStack = getAdminWell(p);
            if (itemStack == null) return;
            Set<Enchant> applied = DungeonsPlugin.getEnchantManager().getEnchants(itemStack);
            applied.removeIf(f -> f.getId() == e.getId());
            applied.add(e);
            DungeonsPlugin.getEnchantManager().setEnchants(itemStack, applied.toArray(new Enchant[0]));
            updateGui = true;
        });
        return items;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        manager = DungeonsPlugin.getEnchantManager();
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
        updateGui = true;
    }

    @Override
    public void onClose() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void next(Player player, Gui gui, Inventory inv) {
        if (!updateGui) return;
        ItemStack item = getAdminWell(player);
        inv.setItem(10, item);

        ItemStackUtils.setDisplay(inv.getItem(12), "残機を増加させる\n現在: " + manager.getLives(item));
        ItemStackUtils.setDisplay(inv.getItem(13), "最大残機を増加させる\n現在: " + manager.getMaxLives(item));
        ItemStackUtils.setDisplay(inv.getItem(14), "階位を増加させる\n現在: " + manager.getTier(item));
        ItemStackUtils.setDisplay(inv.getItem(21), "残機を減少させる\n現在: " + manager.getLives(item));
        ItemStackUtils.setDisplay(inv.getItem(22), "最大残機を減少させる\n現在: " + manager.getMaxLives(item));
        ItemStackUtils.setDisplay(inv.getItem(23), "階位を減少させる\n現在: " + manager.getTier(item));

        Enchant selectedEnchant = manager.getEnchant(id, level);
        ArrayList<Enchant> types = new ArrayList<>(manager.getAllEnchants());
        types.removeIf(p -> p.getLevel() != 1);
        types.sort(Comparator.comparingInt(Enchant::getId));

        StringBuilder idBuilder = new StringBuilder("付与するエンチャントの種類を変更する\n");
        for (Enchant type : types) {
            if (type.getId() == selectedEnchant.getId()) {
                idBuilder.append(ChatColor.GREEN);
                idBuilder.append(ChatColor.stripColor(type.getName()));
            } else {
                idBuilder.append(ChatColor.BLUE);
                idBuilder.append(type.getName());
            }
            idBuilder.append('\n');
        }
        ItemStackUtils.setDisplay(inv.getItem(15), idBuilder.toString());

        ItemStackUtils.setDisplay(inv.getItem(16), "付与するエンチャントのレベルを変更する\n選択されたレベル: " + level);

        StringBuilder localeBuilder = new StringBuilder("アイテムの言語を変更する\n");
        for (Locale locale : DungeonsPlugin.getLocales()) {
            if (locale.equals(manager.getLocale(item)))
                localeBuilder.append(ChatColor.GREEN);
            localeBuilder.append(locale.get("general.lang.name"));
            localeBuilder.append('\n');
        }
        ItemStackUtils.setDisplay(inv.getItem(24), localeBuilder.toString());
        ItemStackUtils.setDisplay(inv.getItem(25), "エンチャントを付与する\n" +
                "選択されたエンチャント: " + ChatColor.BLUE + selectedEnchant.getName());
        updateGui = false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!player.getInventory().equals(e.getClickedInventory())) return;
        if (e.getCurrentItem() == null) return;
        e.setCancelled(true);
        if (getAdminWell(player) != null)
            return;
        setAdminWell(player, e.getCurrentItem());
        updateGui = true;
        InventoryUtils.reduce(player.getInventory(), e.getCurrentItem());
    }
}
