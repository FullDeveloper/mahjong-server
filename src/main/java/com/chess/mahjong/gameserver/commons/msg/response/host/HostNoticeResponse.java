package com.chess.mahjong.gameserver.commons.msg.response.host;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 19:38
 * Description:
 */
public class HostNoticeResponse extends ServerResponse {

    public HostNoticeResponse(int status, String notice) {
        super(status, ConnectAPI.HOST_SEND_RESPONSE);
        if(status >0){
            try {
                output.writeUTF(notice);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                output.close();
            }
        }
        // entireMsg();
    }

}
