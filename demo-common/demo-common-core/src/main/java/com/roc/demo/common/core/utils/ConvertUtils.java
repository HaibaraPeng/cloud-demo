package com.roc.demo.common.core.utils;

/**
 * @Description 类型转换器 工具包
 * @Author dongp
 * @Date 2022/6/27 0027 16:23
 */
public class ConvertUtils {

    /**
     * 转换为String数组<br>
     *
     * @param str 被转换的值
     * @return 结果
     */
    public static String[] toStrArray(String str) {
        return toStrArray(",", str);
    }

    /**
     * 转换为String数组<br>
     *
     * @param split 分隔符
     * @param split 被转换的值
     * @return 结果
     */
    public static String[] toStrArray(String split, String str) {
        return str.split(split);
    }
}
