package net.toshimichi.dungeons.exceptions;

public class IllegalCommandUsageException extends RuntimeException {

    private final String[] messages;

    public IllegalCommandUsageException(String... messages) {
        this.messages = messages;
    }

    public String[] getMessages() {
        return messages;
    }
}
