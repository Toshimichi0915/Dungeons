package net.toshimichi.dungeons.commands;

public class CommandException extends RuntimeException {

    private final String[] messages;

    public CommandException(String... messages) {
        this.messages = messages;
    }

    public String[] getMessages() {
        return messages;
    }
}
