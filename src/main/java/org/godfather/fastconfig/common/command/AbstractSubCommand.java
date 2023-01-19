package org.godfather.fastconfig.common.command;

public abstract class AbstractSubCommand implements SubCommand {

    protected final Command command;
    protected final String name;

    public AbstractSubCommand(Command command, String name) {
        this.command = command;
        this.name = name;
    }

    @Override
    public final Command getCommand() {
        return command;
    }

    @Override
    public final String getName() {
        return name;
    }
}
