package net.toshimichi.dungeons.gui;

import net.toshimichi.dungeons.Dungeons;
import net.toshimichi.dungeons.misc.Stash;
import net.toshimichi.dungeons.music.MusicPlayer;
import net.toshimichi.dungeons.music.PersonalMusicPlayer;
import net.toshimichi.dungeons.music.ResourceMusic;
import net.toshimichi.dungeons.utils.*;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Mystic WellのGUIです.
 */
public class EnchantGui implements Gui, Listener {

    private static final Material[] dyes;
    private static final int[] indexes = {10, 11, 12, 21, 30, 29, 28, 19};
    private static ResourceMusic music;
    private int counter;
    private Player player;
    private Inventory inventory;
    private MusicPlayer musicPlayer;
    private BukkitTask updater;

    //update
    private boolean forceUpdate;
    private boolean updateGui;
    private EnchantState state;
    private ItemStack mysticWell;
    private int money;

    static {
        try {
            music = new ResourceMusic("music/enchant_normal.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        dyes = Arrays.stream(Material.values()).filter(p -> p.name().endsWith("DYE")).toArray(Material[]::new);
    }

    private String getString(String key, Player player, ChatColor color) {
        LocaleBuilder builder = new LocaleBuilder(key).player(player);
        if (color != null)
            builder.replace("{color}", color.toString());
        return builder.build();
    }

    @Override
    public String getTitle(Player player) {
        return getString("mysticwell.title", player, null);
    }

    @Override
    public GuiItem[] getItems() {
        GuiItem[] items = new GuiItem[45];
        for (int i : indexes) {
            ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemStackUtils.setDisplay(glass, getString("mysticwell.not_set", player, ChatColor.GRAY));
            items[i] = new PlainGuiItem(glass, new NullListener());
        }

        items[20] = new PlainGuiItem(new ItemStack(Material.AIR), (p, g, i) -> {
            if (mysticWell == null) return;
            if (p.getInventory().addItem(mysticWell).size() != 0) {
                p.sendMessage(getString("mysticwell.full", p, null));
                return;
            }
            Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
                try {
                    Dungeons.getInstance().getStash().clearStash(p.getUniqueId(), "mystic_well");
                } catch (IOException e) {
                    e.printStackTrace();
                    Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                        InventoryUtils.reduce(p.getInventory(), mysticWell);
                        p.sendMessage(getString("mysticwell.unknown", p, null));
                    });
                }
                Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 0.5F);
                    forceUpdate = true;
                });
            });
        });

        items[25] = new PlainGuiItem(new ItemStack(Material.AIR), (p, g, i) -> {
            if (mysticWell == null) return;
            if (state != EnchantState.AVAILABLE) return;
            ItemStack enchanted = mysticWell.clone();
            EnchantUtils.enchant(enchanted);
            Dungeons.getInstance().getEnchantManager().setLocale(enchanted, Dungeons.getInstance().getLocaleManager().getLocale(p));
            Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
                Dungeons.getInstance().getEconomy().withdraw(p.getUniqueId(), EnchantUtils.getCost(mysticWell));
                try {
                    Dungeons.getInstance().getStash().setItemStacks(p.getUniqueId(), "mystic_well", enchanted);
                } catch (IOException e) {
                    e.printStackTrace();
                    Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () ->
                            p.sendMessage(getString("mysticwell.unknown", p, null)));
                }
                Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> forceUpdate = true);
            });
            musicPlayer = new PersonalMusicPlayer(music, p);
            musicPlayer.start();
        });
        return items;
    }

    @Override
    public void onOpen(Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
        Bukkit.getPluginManager().registerEvents(this, Dungeons.getInstance().getPlugin());

        updater = Bukkit.getScheduler().runTaskTimerAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            if (counter % 200 != 0 && !forceUpdate) return;
            forceUpdate = false;
            EnchantState state = EnchantState.getEnchantState(player);
            ItemStack mysticWell;
            Stash stash = Dungeons.getInstance().getStash();
            List<ItemStack> well = null;
            try {
                well = stash.getItemStacks(player.getUniqueId(), "mystic_well");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (well == null || well.isEmpty())
                mysticWell = null;
            else
                mysticWell = well.get(0);
            int money = Dungeons.getInstance().getEconomy().getBalance(player.getUniqueId());

            Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                this.state = state;
                this.mysticWell = mysticWell;
                this.money = money;
                updateGui = true;
            });
        }, 1, 1);
    }

    @Override
    public void onClose() {
        HandlerList.unregisterAll(this);
        if (musicPlayer != null)
            musicPlayer.stop();
        if (updater != null)
            updater.cancel();
    }

    @Override
    public void next(Player player, Gui gui, Inventory inv) {
        counter++;
        if (updateGui) {
            updateGui = false;
            state = EnchantState.getEnchantState(player);
            inv.setItem(20, mysticWell);

            for (int index : indexes) {
                ItemStack glass = inv.getItem(index);
                String display;
                if (state == EnchantState.NOT_SET)
                    display = getString("mysticwell.not_set", player, ChatColor.GRAY);
                else
                    display = getString("mysticwell.set", player, ChatColor.GRAY);
                ItemStackUtils.setDisplay(glass, display);
                inv.setItem(index, glass);
            }

            ItemStack button = new ItemStack(state.getMaterial());
            int cost = EnchantUtils.getCost(mysticWell);
            int tier = Dungeons.getInstance().getEnchantManager().getTier(mysticWell);
            ChatColor chatColor;
            if (tier == 0) chatColor = ChatColor.GREEN;
            else if (tier == 1) chatColor = ChatColor.YELLOW;
            else if (tier == 2) chatColor = ChatColor.RED;
            else chatColor = ChatColor.LIGHT_PURPLE;
            LocaleBuilder builder = new LocaleBuilder(state.getKey()).player(player)
                    .replace("{cost}", Integer.toString(cost))
                    .replace("{gold}", Integer.toString(cost - money))
                    .replace("{color}", chatColor.toString());
            if (tier >= 0)
                builder.replace("{tier}", chatColor + RomanNumber.convert(tier + 1));
            ItemStackUtils.setDisplay(button, builder.build());
            inv.setItem(25, button);
        }

        if (musicPlayer != null) {
            if (musicPlayer.isPlaying()) {
                Material randomDye = dyes[RandomUtils.nextInt(0, dyes.length)];
                ItemStack dyeItem = new ItemStack(randomDye);
                ItemStackUtils.setDisplay(dyeItem, getString("mysticwell.enchanting", player, null));
                inv.setItem(20, dyeItem);
            } else {
                updateGui = true;
                musicPlayer = null;
            }
        }

        if (counter % 10 != 0) return;
        int index = (counter / 10) % indexes.length;
        int oldIndex = index - 1;
        if (oldIndex < 0)
            oldIndex = indexes.length - 1;

        ItemStack oldItem = inv.getItem(indexes[oldIndex]);
        ItemStack item = inv.getItem(indexes[index]);
        oldItem.setType(Material.GRAY_STAINED_GLASS_PANE);
        if (state != EnchantState.NOT_SET) {
            ItemStackUtils.setDisplay(oldItem, getString("mysticwell.set", player, ChatColor.GRAY));
            ItemStackUtils.setDisplay(item, getString("mysticwell.set", player, ChatColor.LIGHT_PURPLE));
            item.setType(Material.RED_STAINED_GLASS_PANE);
        } else {
            ItemStackUtils.setDisplay(oldItem, getString("mysticwell.not_set", player, ChatColor.GRAY));
            ItemStackUtils.setDisplay(item, getString("mysticwell.not_set", player, ChatColor.RED));
            item.setType(Material.PINK_STAINED_GLASS_PANE);
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!player.getInventory().equals(e.getClickedInventory())) return;
        if (e.getCurrentItem() == null) return;
        e.setCancelled(true);
        if (mysticWell != null) {
            player.sendMessage(getString("mysticwell.already_set", player, null));
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(Dungeons.getInstance().getPlugin(), () -> {
            try {
                Dungeons.getInstance().getStash().setItemStacks(player.getUniqueId(), "mystic_well", e.getCurrentItem());
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
            Bukkit.getScheduler().runTask(Dungeons.getInstance().getPlugin(), () -> {
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 0.5F);
                InventoryUtils.reduce(player.getInventory(), e.getCurrentItem());
                forceUpdate = true;
            });
        });
    }
}
