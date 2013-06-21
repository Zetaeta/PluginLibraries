package net.zetaeta.bukkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Properties;
import java.util.StringTokenizer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class BukkitUtil {
    
    public static <T extends Entity> T nearest(Location location, Collection<T> entities) {
        double min = -1;
        T nearest = null;
        for (T t : entities) {
            double dist = t.getLocation().distance(location);
            if (min < 0 || dist < min) {
                min = dist;
                nearest = t;
            }
        }
        return nearest;
    }
    
    public static void forceUnload(String worldName, boolean save) throws WorldUnloadFailure {
        forceUnload(Bukkit.getWorld(worldName), save);
    }
    
    public static void forceUnload(World world, boolean save) throws WorldUnloadFailure {
        try {
            Field craftWorldWorld = world.getClass().getDeclaredField("world");
            craftWorldWorld.setAccessible(true);
            String error = null;
            do {
                Object worldServer = craftWorldWorld.get(world);
                if (worldServer == null) {
                    error = "CraftWorld.world == null";
                    break;
                }
                Field worldServerDimension = worldServer.getClass().getField("dimension");
                if (worldServerDimension == null) {
                    error = "WorldServer.dimension == null";
                    break;
                }
                worldServerDimension.setAccessible(true);
                worldServerDimension.setInt(worldServer, 2);
                if (!Bukkit.unloadWorld(world, save)) {
                    error = "Bukkit.unloadWorld() failed!";
                    break;
                }
                return;
            } while (false);
            throw new WorldUnloadFailure("Failed to unload world " + world.getName() + ": " + error);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new WorldUnloadFailure("Failed to unload world " + world.getName(), e);
        }
    }

    public static class WorldUnloadFailure extends Exception {
        public WorldUnloadFailure() {
        }
        
        public WorldUnloadFailure(String s) {
            super(s);
        }
        
        public WorldUnloadFailure(Throwable t) {
            super(t);
        }
        
        public WorldUnloadFailure(String s, Throwable t) {
            super(s, t);
        }
    }
    
    
    public static void setDefaultWorld(String worldName) throws IOException {
        Properties serverProps = new Properties();
        File propsFile = new File("server.properties");
        try (FileInputStream fin = new FileInputStream(propsFile); FileOutputStream fos = new FileOutputStream(propsFile)) {
            serverProps.load(fin);
            serverProps.setProperty("level-name", worldName);
            serverProps.store(fos, null);
        }
    }
    
    public static void restartServer(final String command, final int exitStatus) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if ("%exitstatus%".equals(command)) {
                    System.exit(exitStatus);
                }
                else {
                    String[] commandArr = command.split(" ");
                    ProcessBuilder pb = new ProcessBuilder(commandArr);
                    pb.redirectOutput(Redirect.INHERIT);
                    pb.redirectInput(Redirect.INHERIT);
                    pb.redirectError(Redirect.INHERIT);
                    try {
                        pb.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }  
        });
        Bukkit.shutdown();
    }
}
