package com.chess.mahjong.gameserver.commons.initial;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 16:54
 * Description:
 */
public class Params {

    static Properties properties = AppCf.getProperties();
    //新玩家初始房卡数量
    public static final  Integer initialRoomCard = Integer.valueOf(properties.get("initialRoomCard").toString());
    //新玩家初始房卡数量
    public static final  Integer initialPrizeCount = Integer.valueOf(properties.get("initialPrizeCount").toString());

}
