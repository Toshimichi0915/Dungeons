package net.toshimichi.dungeons.gui;

import net.toshimichi.dungeons.DungeonsPlugin;
import net.toshimichi.dungeons.enchants.Enchant;
import net.toshimichi.dungeons.enchants.EnchantManager;
import net.toshimichi.dungeons.enchants.EnchantType;
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
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * エンチャントの管理GUIです.
 */
public class AdminEnchantGui implements Gui, Listener {

    private Player player;
    private Inventory inventory;

    private EnchantManager manager;
    private int id = 0;
    private int level = 0;
    private boolean updateGui;

    private BukkitTask updater;
    private int counter;
    private boolean forceUpdate;
    private ItemStack adminWell;

    private ArrayList<Enchant> getEnchants(ItemStack itemStack) {
        ArrayList<Enchant> types = new ArrayList<>(manager.getAllEnchants());
        types.removeIf(p -> !EnchantType.matchEnchantType(itemStack, p.getEnchantType()));
        types.sort(Comparator.comparingInt(Enchant::getId));
        return types;
    }

    private Enchant getEnchant(ItemStack itemStack, int id, int level) {
        ArrayList<Enchant> enchants = getEnchants(itemStack);
        if (enchants.isEmpty()) return null;
        List<Integer> idList = enchants.stream()
                .map(Enchant::getId)
                .distinct()
                .collect(Collectors.toList());
        List<Enchant> sameIds = enchants.stream()
                .filter(p -> p.getId() == idList.get(id % idList.size()))
                .sorted(Comparator.comparingInt(Enchant::getLevel))
                .collect(Collectors.toList());
        return sameIds.get(level % sameIds.size());
    }

    @Override
    public String getTitle(Player player) {
        return "Enchant Manager";
    }

    private void setAdminWell(ItemStack itemStack) {
        Bukkit.getScheduler().runTaskAsynchronously(DungeonsPlugin.getPlugin(), () -> {
            try {
                if (itemStack != null)
                    DungeonsPlugin.getStash().setItemStacks(player.getUniqueId(), "admin_well", itemStack);
                else
                    DungeonsPlugin.getStash().clearStash(player.getUniqueId(), "admin_well");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () -> forceUpdate = true);
        });
    }

    @Override
    public GuiItem[] getItems() {
        GuiItem[] items = new GuiItem[36];
        items[10] = new PlainGuiItem(new ItemStack(Material.AIR), (p, g, i) -> {
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            if (p.getInventory().addItem(itemStack).size() != 0) return;
            setAdminWell(null);
        });
        items[12] = new PlainGuiItem(new ItemStack(Material.ENDER_EYE), (p, g, i) -> {
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            itemStack = itemStack.clone();
            manager.setLives(itemStack, manager.getLives(itemStack) + 1);
            setAdminWell(itemStack);
        });
        items[13] = new PlainGuiItem(new ItemStack(Material.PINK_DYE), (p, g, i) -> {
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            itemStack = itemStack.clone();
            manager.setMaxLives(itemStack, manager.getMaxLives(itemStack) + 1);
            setAdminWell(itemStack);
        });
        items[14] = new PlainGuiItem(new ItemStack(Material.LAVA_BUCKET), (p, g, i) -> {
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            itemStack = itemStack.clone();
            manager.setTier(itemStack, manager.getTier(itemStack) + 1);
            setAdminWell(itemStack);
        });
        items[15] = new PlainGuiItem(new ItemStack(Material.BLAZE_ROD), (p, g, i) -> {
            id++;
            updateGui = true;
        });
        items[16] = new PlainGuiItem(new ItemStack(Material.STICK), (p, g, i) -> {
            level++;
            updateGui = true;
        });
        ItemStack book = new ItemStack(Material.BOOK);
        ItemStackUtils.setDisplay(book, ChatColor.GRAY + "錬金台のアイテムを編集できます");
        items[19] = new PlainGuiItem(book, new NullListener());
        items[21] = new PlainGuiItem(new ItemStack(Material.ENDER_PEARL), (p, g, i) -> {
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            itemStack = itemStack.clone();
            manager.setLives(itemStack, manager.getLives(itemStack) - 1);
            setAdminWell(itemStack);
        });
        items[22] = new PlainGuiItem(new ItemStack(Material.CYAN_DYE), (p, g, i) -> {
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            itemStack = itemStack.clone();
            manager.setMaxLives(itemStack, manager.getMaxLives(itemStack) - 1);
            setAdminWell(itemStack);
        });
        items[23] = new PlainGuiItem(new ItemStack(Material.WATER_BUCKET), (p, g, i) -> {
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            itemStack = itemStack.clone();
            manager.setTier(itemStack, manager.getTier(itemStack) - 1);
            setAdminWell(itemStack);
        });
        items[24] = new PlainGuiItem(new ItemStack(Material.GLOWSTONE_DUST), (p, g, i) -> {
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            itemStack = itemStack.clone();
            List<Locale> locales = DungeonsPlugin.getLocales();
            Locale locale = locales.get((locales.indexOf(manager.getLocale(itemStack)) + 1) % locales.size());
            manager.setLocale(itemStack, locale);
            setAdminWell(itemStack);
        });
        items[25] = new PlainGuiItem(new ItemStack(Material.ENCHANTING_TABLE), (p, g, i) -> {
            Enchant e = getEnchant(adminWell, id, level);
            if (e == null) return;
            ItemStack itemStack = adminWell;
            if (itemStack == null) return;
            itemStack = itemStack.clone();
            Set<Enchant> applied = DungeonsPlugin.getEnchantManager().getEnchants(itemStack);
            applied.removeIf(f -> f.getId() == e.getId());
            applied.add(e);
            DungeonsPlugin.getEnchantManager().setEnchants(itemStack, applied.toArray(new Enchant[0]));
            setAdminWell(itemStack);
        });
        return items;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        manager = DungeonsPlugin.getEnchantManager();
        Bukkit.getPluginManager().registerEvents(this, DungeonsPlugin.getPlugin());
        updater = Bukkit.getScheduler().runTaskTimerAsynchronously(DungeonsPlugin.getPlugin(), () -> {
            if (counter % 200 != 0 && !forceUpdate) return;
            forceUpdate = false;
            Stash stash = DungeonsPlugin.getStash();
            List<ItemStack> well;
            try {
                well = stash.getItemStacks(player.getUniqueId(), "admin_well");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Bukkit.getScheduler().runTask(DungeonsPlugin.getPlugin(), () -> {
                if (well.isEmpty())
                    adminWell = null;
                else
                    adminWell = well.get(0);
                updateGui = true;
            });
        }, 1, 1);
    }

    @Override
    public void onClose() {
        HandlerList.unregisterAll(this);
        if (updater != null)
            updater.cancel();
    }

    @Override
    public void next(Player player, Gui gui, Inventory inv) {
        counter++;
        if (!updateGui) return;
        updateGui = false;
        inv.setItem(10, adminWell);

        ItemStackUtils.setDisplay(inv.getItem(12), "残機を増加させる\n現在: " + manager.getLives(adminWell));
        ItemStackUtils.setDisplay(inv.getItem(13), "最大残機を増加させる\n現在: " + manager.getMaxLives(adminWell));
        ItemStackUtils.setDisplay(inv.getItem(14), "階位を増加させる\n現在: " + manager.getTier(adminWell));
        ItemStackUtils.setDisplay(inv.getItem(21), "残機を減少させる\n現在: " + manager.getLives(adminWell));
        ItemStackUtils.setDisplay(inv.getItem(22), "最大残機を減少させる\n現在: " + manager.getMaxLives(adminWell));
        ItemStackUtils.setDisplay(inv.getItem(23), "階位を減少させる\n現在: " + manager.getTier(adminWell));

        Enchant selectedEnchant = getEnchant(adminWell, id, level);

        StringBuilder idBuilder = new StringBuilder("付与するエンチャントの種類を変更する\n");
        ArrayList<Enchant> types = getEnchants(adminWell);
        types.removeIf(p -> p.getLevel() != 1);
        for (Enchant type : types) {
            if (selectedEnchant != null && type.getId() == selectedEnchant.getId()) {
                idBuilder.append(ChatColor.GREEN);
                idBuilder.append(ChatColor.stripColor(type.getName()));
            } else {
                idBuilder.append(ChatColor.BLUE);
                idBuilder.append(type.getName());
            }
            idBuilder.append('\n');
        }
        ItemStackUtils.setDisplay(inv.getItem(15), idBuilder.toString());

        if (selectedEnchant == null)
            ItemStackUtils.setDisplay(inv.getItem(16), "付与するエンチャントのレベルを変更する");
        else
            ItemStackUtils.setDisplay(inv.getItem(16), "付与するエンチャントのレベルを変更する\n選択されたレベル: " + selectedEnchant.getLevel());

        StringBuilder localeBuilder = new StringBuilder("アイテムの言語を変更する\n");
        for (Locale locale : DungeonsPlugin.getLocales()) {
            if (locale.equals(manager.getLocale(adminWell)))
                localeBuilder.append(ChatColor.GREEN);
            localeBuilder.append(locale.get("general.lang.name"));
            localeBuilder.append('\n');
        }
        ItemStackUtils.setDisplay(inv.getItem(24), localeBuilder.toString());
        if (selectedEnchant == null)
            ItemStackUtils.setDisplay(inv.getItem(25), "エンチャントを付与する");
        else
            ItemStackUtils.setDisplay(inv.getItem(25), "エンチャントを付与する\n" +
                    "選択されたエンチャント: " + ChatColor.BLUE + selectedEnchant.getName());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!player.getInventory().equals(e.getClickedInventory())) return;
        if (e.getCurrentItem() == null) return;
        e.setCancelled(true);
        if (adminWell != null)
            return;
        setAdminWell(e.getCurrentItem());
        InventoryUtils.reduce(player.getInventory(), e.getCurrentItem());
    }
}
