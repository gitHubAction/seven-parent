package com.seven.mybatis.config.typehandler;

/**
 *
 * @author zhangshihao01
 * @date 2024/9/25 17:28
 */
public class BaseEnumUtils {
    private BaseEnumUtils() {
    }

    public static <E extends Enum<?> & BaseIntEnum> E valueOf(Class<E> enumClass, int value) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (Integer.parseInt(e.getValue()) == value) {
                return e;
            }
        }
        return null;
    }

    public static <E extends Enum<?> & BaseStringEnum> E valueOf(Class<E> enumClass, String value) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

}
