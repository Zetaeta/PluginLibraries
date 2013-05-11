package net.zetaeta.bukkit.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import net.zetaeta.util.ArrayUtils;

import org.junit.Test;

public class UtilTest {

    @Test
    public void test() {
        String[] strings = {"hello", "world", "from", "zetaeta"};
        String[] reverseStrings = ArrayUtils.reverse(strings);
        assertEquals(strings[0], reverseStrings[3]);
        System.out.println(Arrays.toString(strings));
        System.out.println(Arrays.toString(reverseStrings));
    }

}
