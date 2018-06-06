package com.chess.mahjong.gameserver.commons.msg.response;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 19:12
 * Description:
 */
public class ErrorResponse extends ServerResponse {

    /**
     * 必须调用此方法设置消息号
     *
     * @param message
     */
    public ErrorResponse(String message) throws IOException {
        super(1, ConnectAPI.ERROR_RESPONSE);
        output.writeUTF(message);
        output.close();
        // entireMsg();
    }

}
