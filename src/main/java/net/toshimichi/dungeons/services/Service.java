package net.toshimichi.dungeons.services;

public interface Service extends Runnable {

    default void start() {
    }

    default void stop() {
    }
}
