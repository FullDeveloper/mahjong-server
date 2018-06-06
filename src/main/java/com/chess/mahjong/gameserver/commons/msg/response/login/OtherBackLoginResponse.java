package com.chess.mahjong.gameserver.commons.msg.response.login;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.message.ServerResponse;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 22:56
 * Description:
 */
public class OtherBackLoginResponse extends ServerResponse {

    public OtherBackLoginResponse(int status, String uuid) {
        super(status, ConnectAPI.OTHER_BACK_LOGIN_RESPONSE);
        try {
            if(status>0){
                output.writeUTF(uuid);
            }
            else{
                //output.writeUTF(roomVO.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
        }
    }

}
