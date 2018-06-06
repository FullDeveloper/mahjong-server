package com.chess.mahjong.gameserver.commons.msg.response.createroom;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/6 0006
 * Time: 20:37
 * Description:
 */
public class CreateRoomResponse extends ServerResponse {

    /**
     * 必须调用此方法设置消息号
     *
     * @param
     */
    public CreateRoomResponse(int status,String obj) throws IOException {
        super(status, ConnectAPI.CREATEROOM_RESPONSE);
        if(status > 0){
            output.writeUTF(obj);
            System.out.println("roomId:"+obj);
            output.close();

        }
    }

}
