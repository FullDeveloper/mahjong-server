package com.chess.mahjong.gameserver.commons.msg.response.messagebox;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 21:27
 * Description:
 */
public class MessageBoxResponse extends ServerResponse {

    /**
     * 必须调用此方法设置消息号
     *
     *
     * @param msgCode
     */
    public MessageBoxResponse(String msgCode) {
        super(1, ConnectAPI.MessageBox_Notice);
        try {
            output.writeUTF(msgCode);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            output.close();
        }
    }

}
