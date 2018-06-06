package com.chess.mahjong.gameserver.commons.msg.response.outroom;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 19:38
 * Description:
 */
public class OutRoomResponse extends ServerResponse {

    public OutRoomResponse(int status,String str) {
        super(status, ConnectAPI.OUT_ROOM_RESPONSE);
        if(status>0){
            try {
                output.writeUTF(str);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                output.close();
            }
        }
        //entireMsg();
    }

}
