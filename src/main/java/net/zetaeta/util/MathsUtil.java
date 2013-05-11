package net.zetaeta.util;

public class MathsUtil {

    public static int roundUpBy(int by, int number) {
        if (number > 0) {
            return by * ((number / by) + 1);
        }
        else {
            return by * (number / by);
        }
    }
    
    public static int roundUpAndDivide(int by, int number) {
        if (number > 0) {
            return number / by + 1;
        }
        else {
            return number / by;
        }
    }
    
    public static int roundDownBy(int by, int number) {
        if (number > 0) {
            return by * ((number / by));
        }
        else {
            return by * (number / by) - 1;
        }
    }
    
    public static int roundDownAndDivide(int by, int number) {
        if (number > 0) {
            return number / by;
        }
        else {
            return number / by - 1;
        }
    }
}
