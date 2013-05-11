package net.zetaeta.bukkit.util;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class BukkitUtil {
    
    public static <T extends Entity> T nearest(Location location, Collection<T> entities) {
        double min = -1;
        T nearest = null;
        for (T t : entities) {
            double dist = t.getLocation().distance(location);
            if (min == -1 || dist < min) {
                min = dist;
                nearest = t;
            }
        }
        return nearest;
    }

}
