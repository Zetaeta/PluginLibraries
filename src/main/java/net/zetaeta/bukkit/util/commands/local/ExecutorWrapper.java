package net.zetaeta.bukkit.util.commands.local;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import net.zetaeta.bukkit.util.PermissionUtil;
import net.zetaeta.bukkit.util.commands.CommandArguments;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExecutorWrapper extends AbstractLocalCommandExecutor {
    protected LocalCommandExecutor executor;
    protected Method executorMethod;
    protected Command annotation;
    protected boolean useCmdArgs;
//    protected String[] boolFlags;
//    protected String[] valueFlags;
//    protected boolean checkPermissions;
//    protected boolean playersOnly;
    
    public ExecutorWrapper(LocalCommand parent, LocalCommandExecutor executor, Method executorMethod) {
        super(parent);
        this.executor = executor;
        this.executorMethod = executorMethod;
        annotation = executorMethod.getAnnotation(Command.class);
        usage = annotation.usage();
        shortUsage = annotation.shortUsage();
        aliases = Arrays.copyOf(annotation.aliases(), annotation.aliases().length + 1);
        aliases[aliases.length - 1] = annotation.value();
        System.out.println("ExecutorWrapper: " + Arrays.toString(aliases));
        useCmdArgs = annotation.useCommandArguments();
        if (annotation.inheritPermission()) {
            permission = parent.getPermission() + annotation.permission();
        }
        else {
            permission = annotation.permission();
        }
        Class<?>[] params = executorMethod.getParameterTypes();
        if (!useCmdArgs && params.length == 2 && params[1] == CommandArguments.class) {
            useCmdArgs = true;
        }
    }
    
    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (trySubCommand(sender, alias, args)) {
            return true;
        }
        if (annotation.checkPermissions() && !PermissionUtil.checkPermission(sender, permission, true, true)) {
            return true;
        }
        if (annotation.playersOnly() && !(sender instanceof Player)) {
            sender.sendMessage("�cThis command can only be run by a player!");
            return true;
        }
        boolean success;
        try {
            if (useCmdArgs) {
                success = (Boolean) executorMethod.invoke(executor, sender, CommandArguments.processArguments(alias, args, annotation.boolFlags(), annotation.valueFlags()));
            }
            else {
                success = (Boolean) executorMethod.invoke(executor, sender, alias, args);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            sender.sendMessage("�cAn internal error occurred while executing this command.");
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sender.sendMessage("�cAn internal error occurred while executing this command.");
            return true;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            e.getCause().printStackTrace();
            sender.sendMessage("�cAn internal error occurred while executing this command.");
            return true;
        }
        return success;
    }

    @Override
    public void sendUsage(CommandSender target) {
        target.sendMessage(usage);
    }
}
