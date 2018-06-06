package com.chess.mahjong.util;

import com.chess.mahjong.context.ErrorCode;
import com.chess.mahjong.gameserver.commons.msg.response.ErrorResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 19:31
 * Description:
 */
public class GlobalUtil {

    public static boolean checkIsLogin(GameSession session){
        if(session.isLogin() == false){
            System.out.println("账户未登录或已经掉线!");
            try {
                session.sendMsg(new ErrorResponse(ErrorCode.Error_000002));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

}
