package com.chess.mahjong.gameserver.manager;

import com.chess.mahjong.context.ErrorCode;
import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.msg.response.ErrorResponse;
import com.chess.mahjong.gameserver.commons.msg.response.login.BreakLineResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.context.GameServerContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 18:05
 * Description:
 */
public class GameSessionManager {


    private static GameSessionManager gameSessionManager;

    public Map<String,GameSession> sessionMap = new HashMap<String,GameSession>();

    public static int topOnlineAccountCount = 0;
    /**
     *
     * @return
     */
    public static GameSessionManager getInstance(){
        if(gameSessionManager == null){
            gameSessionManager = new GameSessionManager();
        }
        return gameSessionManager;
    }

    /**
     * 存放GAMESESSION
     * @param gameSession
     * @return
     */
    public boolean putGameSessionInHashMap(GameSession gameSession, int useId){
        //Avatar avatar = gameSession.getRole(Avatar.class);
        //检查map集合中是否已经保存了当前用户的session
        boolean result = checkSessionIsHave(useId);
        //System.out.println(" result ==> "+result);
        if(result){
            //System.out.println("这个用户已登录了,更新session");
            try {
                //通知该客户端已在别的设备登陆
                sessionMap.get("uuid_"+useId).sendMsg(new ErrorResponse(ErrorCode.Error_000022));
                //通知该客户端进行断线操作
                sessionMap.get("uuid_"+useId).sendMsg(new BreakLineResponse(1));
                Thread.sleep(1000);
                //关闭该session
                sessionMap.get("uuid_"+useId).close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Avatar avatar = gameSession.getRole(Avatar.class);
            //将用户添加到在线列表中
            GameServerContext.add_onLine_Character(avatar);
            //将用户从掉线map中删除
            GameServerContext.remove_offLine_Character(avatar);
            //1.
            //TimeUitl.stopAndDestroyTimer(avatar);
            //重新给当前用户绑定session
            sessionMap.put("uuid_"+useId,gameSession);
            //如果玩家在房间中 则需要给其他玩家发送在线消息

           /* if(avatar.getRoomVO() != null){
                RoomLogic roomLogic = RoomManager.getInstance().getRoom(avatar.getRoomVO().getRoomId());
                if(roomLogic != null){
                    List<Avatar> playerList = RoomManager.getInstance().getRoom(avatar.getRoomVO().getRoomId()).getPlayerList();
                    for (int i = 0; i < playerList.size(); i++) {
                        if(playerList.get(i).getUuId() != avatar.getUuId()){
                            //给其他三个玩家返回重连用户信息
                            playerList.get(i).getSession().sendMsg(new OtherBackLoginResonse(1, avatar.getUuId()+""));
                        }
                    }
        			*//*if(sessionMap.size() > topOnlineAccountCount){
        				topOnlineAccountCount = sessionMap.size();
        			}*//*
                }
            }*/

        }else{
            //System.out.println("denglu");
            //用户不存在 直接建立会话
            sessionMap.put("uuid_"+useId,gameSession);
            //设置会话数量
            if(sessionMap.size() > topOnlineAccountCount){
                topOnlineAccountCount = sessionMap.size();
            }
        }
        return !result;
    }

    /**
     * 检测用户session是否存在
     * @param uuid
     * @return
     */
    private boolean checkSessionIsHave(int uuid){
        //可以用来判断是否在线****等功能
        GameSession gameSession = sessionMap.get("uuid_"+uuid);
        if(gameSession != null){
            return true;
        }
        return false;
    }

}
