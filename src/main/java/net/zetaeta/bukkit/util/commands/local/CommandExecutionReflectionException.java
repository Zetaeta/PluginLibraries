package net.zetaeta.bukkit.util.commands.local;

public class CommandExecutionReflectionException extends Exception {
    private Exception cause;
    
    public CommandExecutionReflectionException(Exception source) {
        super(source);
        cause = source;
    }
    
    public Exception getSource() {
        return cause;
    }
}
