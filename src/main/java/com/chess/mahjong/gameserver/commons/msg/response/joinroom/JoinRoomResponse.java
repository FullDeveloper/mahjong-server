package com.chess.mahjong.gameserver.commons.msg.response.joinroom;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;
import com.chess.mahjong.gameserver.pojo.RoomVO;
import com.chess.mahjong.util.JsonUtilTool;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 23:03
 * Description:
 */
public class JoinRoomResponse  extends ServerResponse {

    public JoinRoomResponse(int status,RoomVO roomVO) {
        super(status, ConnectAPI.JOIN_ROOM_RESPONSE);
        try {
            if (status > 0) {
                output.writeUTF(JsonUtilTool.toJson(roomVO));
            } else {
                output.writeUTF(roomVO.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
        }
    }

}
