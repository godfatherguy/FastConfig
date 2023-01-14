package org.godfather.fastconfig.universal.messages;

import org.bukkit.ChatColor;
import org.godfather.fastconfig.universal.Utils;

import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public enum MessageFlag implements Consumer<MessageBuilder> {

    CENTERED(builder -> {
        if (builder.getType() == MessageType.ACTIONBAR)
            return builder.getMessage();
        return Utils.centeredMessage(builder.getMessage());
    }),
    BOLD(builder -> ChatColor.BOLD + builder.getMessage()),
    ITALIC(builder -> ChatColor.ITALIC + builder.getMessage()),
    UNDERLINED(builder -> ChatColor.UNDERLINE + builder.getMessage()),
    STRIKE(builder -> ChatColor.STRIKETHROUGH + builder.getMessage()),
    COLORED(builder -> ChatColor.translateAlternateColorCodes('&', builder.getMessage()));

    private final Function<MessageBuilder, String> function;

    MessageFlag(Function<MessageBuilder, String> function) {
        this.function = function;
    }

    @Override
    public void accept(MessageBuilder messageBuilder) {
        messageBuilder.setMessage(function.apply(messageBuilder));
    }
}
