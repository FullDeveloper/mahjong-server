package com.chess.mahjong.gameserver.commons.msg.response.joinroom;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;
import com.chess.mahjong.gameserver.pojo.AvatarVO;
import com.chess.mahjong.util.JsonUtilTool;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 23:05
 * Description:
 */
public class JoinRoomNotice extends ServerResponse {

    /**
     * 必须调用此方法设置消息号
     *
     * @param status
     * @param avatarVO
     */
    public JoinRoomNotice(int status, AvatarVO avatarVO) {
        super(status, ConnectAPI.JOIN_ROOM_NOTICE);
        if(status > 0){
            try {
                output.writeUTF(JsonUtilTool.toJson(avatarVO));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                output.close();
            }
        }
    }

}
