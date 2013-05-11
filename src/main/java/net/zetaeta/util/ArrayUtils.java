package net.zetaeta.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
    
    public static String join(Object[] arr, String joiner) {
        if (arr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<arr.length - 1; ++i) {
            sb.append(String.valueOf(arr[i]));
            sb.append(joiner);
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }
    
    public static <T> T[] removeNulls(T[] array) {
        List<T> notNull = new ArrayList<>();
        for (T t : array) {
            if (t != null) {
                notNull.add(t);
            }
        }
        return notNull.toArray((T[]) Array.newInstance(array.getClass().getComponentType(), notNull.size()));
    }

    /**
     * Removes the first index of an array of <T>. Convenience method for Arrays.copyOfRange(array, 1, array.length)
     * 
     * @param array Array of <T> to be modified
     * @return array without the first index.
     */
    public static <T> T[] removeFirstElement(T[] array) {
        if (array.length == 0) {
            return array;
        }
        return Arrays.copyOfRange(array, 1, array.length);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] reverse(T[] array) {
        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);
        int len = array.length - 1;
        for (int i=0; i<array.length; ++i) {
            newArray[i] = array[len-i];
        }
        return newArray;
    }
}
