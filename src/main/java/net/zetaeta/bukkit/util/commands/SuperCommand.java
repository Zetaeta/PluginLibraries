package net.zetaeta.bukkit.util.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zetaeta.bukkit.util.commands.local.ExecutorWrapper;
import net.zetaeta.bukkit.util.commands.local.LocalCommand;
import net.zetaeta.bukkit.util.commands.local.LocalCommandExecutor;
import net.zetaeta.bukkit.util.commands.local.Command;
import net.zetaeta.util.ArrayUtils;
import net.zetaeta.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;


public class SuperCommand extends DynamicCommandExecutor implements LocalCommand {
    
    private Map<String, LocalCommand> subCommands = new HashMap<>();
    private String[] usage, shortUsage;
    private String name;
    private String[] aliases;
    private String permission;
    private String description;
    private String permissionMessage;
     
    
    public SuperCommand(String commandName, String[] aliases) {
        this(commandName, aliases, null, null);
    }
    
    public SuperCommand(String commandName, String[] aliases, String description, String usage) {
        this(commandName, aliases, description, usage, null, null);
    }
    
    public SuperCommand(String commandName, String[] aliases, String description, String usage, String permission, String permissionMessage) {
        this(commandName, aliases, description, usage == null ? new String[0] : usage.split("\n"), permission, permissionMessage);
    }
    
    public SuperCommand(String commandName, String[] aliases, String description, String[] usage, String permission, String permissionMessage) {
        this(commandName, aliases, description, usage, new String[0], permission, permissionMessage);
    }
    
    public SuperCommand(String commandName, String[] aliases, String description, String[] usage, String[] shortUsage, String permission, String permissionMessage) {
        this.name = commandName;
        this.aliases = aliases;
        this.permission = permission;
        this.description = description;
        this.usage = usage;
        this.shortUsage = shortUsage;
        this.permissionMessage = permissionMessage;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        System.out.println("meoww");
        sender.sendMessage("meow");
        System.out.println(this);
        if (args.length == 0) {
            sender.sendMessage(usage);
            return true;
        }
        LocalCommand lc = subCommands.get(args[0]);
        if (lc != null) {
            return lc.execute(sender, label, ArrayUtils.removeFirstElement(args));
        }
        sender.sendMessage(usage);
        System.out.println(subCommands);
        return true;
    }
    
    public LocalCommand registerSubCommand(LocalCommand lc) {
        for (String s : lc.getAliases()) {
            subCommands.put(s, lc);
        }
        return lc;
    }
    
    public List<LocalCommand> registerSubCommands(LocalCommandExecutor lce) {
        Class<? extends LocalCommandExecutor> clazz = lce.getClass();
        List<LocalCommand> commands = new ArrayList<>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Command.class)) {
                commands.add(registerSubCommand(new ExecutorWrapper(this, lce, m)));
            }
        }
        return commands;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public LocalCommand getParent() {
        return null;
    }

    @Override
    public Collection<LocalCommand> getSubCommands() {
        return subCommands.values();
    }

    @Override
    public Set<String> getSubCommandAliases() {
        return subCommands.keySet();
    }

    @Override
    public String getPermission() {
        return permission;
    }
    
    public String getPermissionMessage() {
        return permissionMessage;
    }
    
    public String getDescription() {
        return description;
    }

    @Override
    public String[] getUsage() {
        return usage;
    }

    @Override
    public String[] getShortUsage() {
        return shortUsage;
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        return false;
    }

    @Override
    public void sendUsage(CommandSender target) {
        
    }

    @Override
    public LocalCommand getSubCommand(String alias) {
        return subCommands.get(alias);
    }

    @Override
    public LocalCommand getSubCommand(String[] aliases) {
        if (aliases.length == 0) {
            return null;
        }
        LocalCommand sub = subCommands.get(aliases[0]);
        if (aliases.length == 1 || sub == null) {
            return sub;
        }
        return sub.getSubCommand(ArrayUtils.removeFirstElement(aliases));
    }
}
