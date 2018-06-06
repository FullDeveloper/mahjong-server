package com.chess.mahjong.gameserver.commons.msg.response.startgame;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 18:47
 * Description:
 */
public class PrepareGameResponse extends ServerResponse {

    /**
     *
     * @param status
     * @param  avatarIndex 准备人的索引
     */
    public PrepareGameResponse(int status,int avatarIndex) {
        super(status, ConnectAPI.PrepareGame_MSG_RESPONSE);
        try {
            JSONObject json = new JSONObject();
            json.put("avatarIndex", avatarIndex);
            output.writeUTF(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
        }
        //entireMsg();
    }

}
