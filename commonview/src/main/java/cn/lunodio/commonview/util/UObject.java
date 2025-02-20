package cn.lunodio.commonview.util;

import java.util.ArrayList;
import java.util.List;

public class UObject {
    /**
     * 格式转换
     *
     * @param obj   源
     * @param clazz 目标类
     * @param <T>   泛型
     * @return 泛型
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
