package org.godfather.fastconfig.universal.messages;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.function.Function;

public class MessageBuilder {

    private final MessageType type;
    private final EnumSet<MessageFlag> flags = EnumSet.noneOf(MessageFlag.class);
    private String message;
    private Function<Player, String> function = null;

    public MessageBuilder(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageBuilder(MessageType type, Function<Player, String> function) {
        this.type = type;
        this.function = function;
    }

    public MessageType getType() {
        return type;
    }

    public MessageBuilder addFlags(MessageFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    public void send(Player player) {
        if (function != null)
            message = function.apply(player);

        flags.forEach(flag -> flag.accept(this));
        type.accept(player, message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
