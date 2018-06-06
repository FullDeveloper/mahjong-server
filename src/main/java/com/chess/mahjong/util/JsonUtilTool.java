package com.chess.mahjong.util;

import com.chess.mahjong.gameserver.pojo.AvatarVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.lang.reflect.Array;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 13:42
 * Description:
 */
public class JsonUtilTool {

    /**
     * JSON字符串转换成对象
     *
     * @param jsonString
     *            需要转换的字符串
     * @param type
     *            需要转换的对象类型
     * @return 对象
     */
    public static <T> T fromJson(String jsonString, Class<T> type) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return (T) JSONObject.toBean(jsonObject, type);
    }

    /**
     * 对象转换成JSON字符串
     *
     * @param obj
     *            需要转换的对象
     * @return 对象的string字符
     */
    public static String toJson(Object obj) {
        if(obj instanceof Array){
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            JSONArray json = JSONArray.fromObject(obj, jsonConfig);
            return json.toString();
        }else {
            JSONObject jSONObject = JSONObject.fromObject(obj);
            return jSONObject.toString();
        }
    }
}
