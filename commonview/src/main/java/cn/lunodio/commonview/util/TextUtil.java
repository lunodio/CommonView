package cn.lunodio.commonview.util;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextUtil {

    /**
     * @param string string
     * @return 如果字符串为空或长度小于等于0返回true
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() <= 0;
    }

//    public static boolean isConSpeCharacters(String string) {
//        //不包含特殊字符
//        return string.replaceAll("[0-9]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() != 0;
//    }
//
//    /**
//     * @param str phoneNumber
//     * @return 是否大陆手机
//     */
//    public static boolean isChinaPhoneLegal(String str) {
//        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
//        Pattern p = Pattern.compile(regExp);
//        Matcher m = p.matcher(str);
//        return m.matches();
//    }
//
//    /**
//     * 例：str:123456789 interval:3 separator:","
//     * 返回：123,456,789
//     *
//     * @param str       原始字符串
//     * @param interval  每隔多少个字符分隔
//     * @param separator 分隔使用的字符串
//     * @return 分割后的字符串
//     */
//    public static String splitStringFromEnd(String str, int interval, String separator) {
//        final StringBuilder sb = new StringBuilder(str);
//        for (int i = sb.length() - interval; i > 0; i -= interval) {
//            sb.insert(i, separator);
//        }
//        return sb.toString();
//    }
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static List<String> string2List(String str) {
        if (isEmpty(str)) return null;
        return Arrays.stream(str.split(",")).
                map(String::trim).collect(Collectors.toList());
    }

    public static List<String> string2List(String str, String regex) {
        if (isEmpty(str)) return null;
        return Arrays.stream(str.split(regex)).
                map(String::trim).collect(Collectors.toList());
    }

    public static String list2String(List<String> strings) {
        if (strings == null) return "";
        return String.join(",", strings);
    }

    public static String list2String(List<String> strings, String delimiter) {
        if (strings == null) return "";
        return String.join(delimiter, strings);
    }

    public static String clearSpace(String str) {
        return str.replace(" ", "");
    }

    public static boolean isBlank(String str) {
        if (isEmpty(str)) return true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isDigitsOnly(CharSequence str) {
        final int len = str.length();
        for (int cp, i = 0; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(str, i);
            if (!Character.isDigit(cp)) {
                return false;
            }
        }
        return true;
    }


    public static boolean isConSpeCharacters(String string) {
        //不包含特殊字符
        return string.replaceAll("[0-9]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() != 0;
    }

    /**
     * @param str phoneNumber
     * @return 是否大陆手机
     */
    public static boolean isChinaPhoneLegal(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 例：str:123456789 interval:3 separator:","
     * 返回：123,456,789
     *
     * @param str       原始字符串
     * @param interval  每隔多少个字符分隔
     * @param separator 分隔使用的字符串
     * @return 分割后的字符串
     */
    public static String splitStringFromEnd(String str, int interval, String separator) {
        final StringBuilder sb = new StringBuilder(str);
        for (int i = sb.length() - interval; i > 0; i -= interval) {
            sb.insert(i, separator);
        }
        return sb.toString();
    }
}
