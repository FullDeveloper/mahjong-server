package com.chess.mahjong.gameserver.context;

import com.chess.mahjong.gameserver.Avatar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 18:01
 * Description:
 */
public class GameServerContext {

    /**
     * 所有在线的玩家
     */
    private static Map<Integer,Avatar> ALL_ONLINE_PLAYER = new ConcurrentHashMap<Integer, Avatar>();
    /**
     * 所有掉线的玩家
     */
    private static Map<Integer,Avatar> ALL_OFFLINE_PLAYER = new ConcurrentHashMap<Integer, Avatar>();
    /**
     * 把用户添加到在线hashMap中
     * Character character
     * @param avatar
     */
    public static  void add_onLine_Character(Avatar avatar){
        ALL_ONLINE_PLAYER.put(avatar.getUuId(), avatar);
    }

    /**
     * 把用户从在线hashmap中删除
     * @param avatar
     */
    public static  void remove_onLine_Character(Avatar avatar){
        ALL_ONLINE_PLAYER.remove(avatar.getUuId());
    }

    /**
     * 把用户从掉线hashmap中删除
     * @param avatar
     */
    public static  void remove_offLine_Character(Avatar avatar){
        ALL_OFFLINE_PLAYER.remove(avatar.getUuId());
    }

    /**
     * 把用户添加到掉线hashMap中,同时移除在线map中
     * Character character
     * @param avatar
     */
    public static  void add_offLine_Character(Avatar avatar){
        ALL_OFFLINE_PLAYER.put(avatar.getUuId(), avatar);
        ALL_ONLINE_PLAYER.remove(avatar.getUuId());
    }

}
