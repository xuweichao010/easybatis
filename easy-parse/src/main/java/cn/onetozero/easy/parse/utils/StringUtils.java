package cn.onetozero.easy.parse.utils;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/14
 * 描述：字符串工具类
 */
public class StringUtils {
    public static boolean hasText(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean hasText(Object value) {
        if (value != null) {
            return hasText(String.valueOf(value));
        }
        return false;
    }
}
