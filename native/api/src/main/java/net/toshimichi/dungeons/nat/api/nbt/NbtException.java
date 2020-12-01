package net.toshimichi.dungeons.nat.api.nbt;

public class NbtException extends RuntimeException {
    public NbtException() {
    }

    public NbtException(String message) {
        super(message);
    }

    public NbtException(String message, Throwable cause) {
        super(message, cause);
    }

    public NbtException(Throwable cause) {
        super(cause);
    }

    public NbtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
