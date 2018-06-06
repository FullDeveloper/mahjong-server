package com.chess.mahjong.util;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 17:38
 * Description:
 */
public class StringUtil {

    /**
     * 判断字符串是为空
     *
     * @Title: isEmpty
     * @Description: TODO
     * @param @param str
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }

        return false;
    }


    /**
     * 判断字符串非空
     *
     * @Title: isNotEmpty
     * @Description: TODO
     * @param @param str
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
