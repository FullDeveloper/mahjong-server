package com.chess.mahjong.gameserver.commons.msg.response;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;
import com.chess.mahjong.gameserver.pojo.AvatarVO;
import com.chess.mahjong.util.JsonUtilTool;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 17:27
 * Description:
 */
public class LoginResponse  extends ServerResponse {

    public LoginResponse(int status, AvatarVO avatarVO) {
        super(status, ConnectAPI.LOGIN_RESPONSE);
        try {
            //output.writeBoolean(isSuccess);
            if(status > 0) {
                //System.out.println("avatarVO   =  "+JsonUtilTool.toJson(avatarVO));
                output.writeUTF(JsonUtilTool.toJson(avatarVO));
            }
            //entireMsg();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
        }
    }

}
