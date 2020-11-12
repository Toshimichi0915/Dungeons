package net.toshimichi.dungeons.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class BalanceChangeEvent extends PlayerEvent {

    private static final HandlerList handlerList = new HandlerList();

    private final int from;
    private final int to;

    public BalanceChangeEvent(Player who, int from, int to) {
        super(who);
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
