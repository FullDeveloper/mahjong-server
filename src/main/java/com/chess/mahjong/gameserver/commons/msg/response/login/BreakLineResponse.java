package com.chess.mahjong.gameserver.commons.msg.response.login;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 19:17
 * Description: 通知前端 返回到登录界面
 */
public class BreakLineResponse extends ServerResponse {

    public BreakLineResponse(int status) {
        super(status, ConnectAPI.BREAK_LINE_RESPONSE);
        try {
            if(status > 0) {
                output.writeUTF("1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
        }
    }

}
