package net.zetaeta.bukkit.util.configuration;

import java.lang.reflect.Field;

import org.bukkit.configuration.ConfigurationSection;

import net.zetaeta.util.ReflectionUtil;

public class ConfigurationAccess {
    public void load(ConfigurationSection config) {
        Class<?> clazz = getClass();
        Field[] fields = ReflectionUtil.getAllFields(clazz);
        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigOpt.class)) {
                field.setAccessible(true);
                ConfigOpt configOpt = field.getAnnotation(ConfigOpt.class);
                String confName = configOpt.value();
                Class<?> fieldType = field.getType();
                try {
                    if (fieldType == int.class) {
                        int value;
                        value = config.getInt(confName, configOpt.defInt());
                        field.setInt(this, value);
                    }
                    if (fieldType == long.class) {
                        long value;
                        value = config.getLong(confName, configOpt.defLong());
                        field.setLong(this, value);
                    }
                    if (fieldType == float.class) {
                        float value;
                        value = (float) config.getDouble(confName, configOpt.defFloat());
                        field.setFloat(this, value);
                    }
                    if (fieldType == double.class) {
                        double value;
                        value = config.getDouble(confName, configOpt.defDouble());
                        field.setDouble(this, value);
                    }
                    if (fieldType == boolean.class) {
                        boolean value;
                        value = config.getBoolean(confName, configOpt.defBoolean());
                        field.setBoolean(this, value);
                    }
                    if (fieldType == String.class) {
                        String value;
                        value = config.getString(confName, configOpt.defString());
                        field.set(this, value);
                    }
                }
                catch (IllegalAccessException e) {
                    
                }
            }
        }
    }
}
