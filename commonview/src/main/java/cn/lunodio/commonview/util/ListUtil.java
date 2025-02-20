package cn.lunodio.commonview.util;

public class ListUtil {

    public static boolean isSafePosition(int position, int length) {
        return isSafePositionLower(position) && isSafePositionUpper(position, length);
    }

    public static boolean isSafePositionLower(int position) {
        return position >= 0;
    }

    public static boolean isSafePositionUpper(int position, int length) {
        return position < length;
    }

    public static boolean isInvalidPosition(int position, int length) {
        return position < 0 || position >= length;
    }

}
