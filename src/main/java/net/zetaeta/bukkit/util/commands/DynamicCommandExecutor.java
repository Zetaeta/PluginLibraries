package net.zetaeta.bukkit.util.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * 
 * 
 * @author Zetaeta
 *
 */
public abstract class DynamicCommandExecutor implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args ) {
        System.out.println("DynamicCommandExecutor.onCommand");
        return false;
    }
    
}
