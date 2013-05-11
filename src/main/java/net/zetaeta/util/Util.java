package net.zetaeta.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Provides utility methods for my plugins.
 * 
 * @author Zetaeta
 *
 */
public class Util {
    
    /**
     * Adds the basic colours (0 - f) to a String by replacing "&<colour digit>" with �<colour digit>
     * 
     * @param string String to colourise
     * @return Colourised string.
     */
    public static String addBasicColour(String string) {
        string = string.replaceAll("(�([a-fA-F0-9]))", "�$2");

        string = string.replaceAll("(&([a-fA-F0-9]))", "�$2");

        return string;
      }
    
    
    public static boolean booleanValue(int i) {
        return i == 0 ? false : true;
    }
    
    public static boolean booleanValue(long l) {
        return l == 0 ? false : true;
    }
    
    public static boolean booleanValue(short s) {
        return s == 0 ? false : true;
    }
    
    public static boolean booleanValue(byte b) {
        return b == 0 ? false : true;
    }
    
    public static void notNull(String message, Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }
}
