package com.chess.mahjong.gameserver.commons.initial;

import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 16:55
 * Description:
 */
public class AppCf {

    public static Integer startNum = 0;
    public static final Integer number = 1;//每次加载广告条数

    final static  String config_path = "total.properties";
    final static Properties properties = new Properties();

    public static Properties getProperties() {
        try {
            InputStream stream= Resources.getResourceAsStream(config_path);
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    public static void main(String[] args) throws IOException {
        Properties p  = getProperties();
        for (Map.Entry<Object, Object> string : p.entrySet()) {
            System.out.println(string.getKey()+":");
            System.out.println(string.getValue());
        }
    }

}
