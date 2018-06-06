package com.chess.mahjong.gameserver.commons.msg.processor.startgame;

import com.chess.mahjong.context.ErrorCode;
import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.logic.RoomLogic;
import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import com.chess.mahjong.gameserver.commons.msg.processor.common.INotAuthProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.common.MsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.response.ErrorResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.manager.RoomManager;
import com.chess.mahjong.gameserver.pojo.RoomVO;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 18:35
 * Description:
 */
public class PrepareGameMSGProcessor extends MsgProcessor implements INotAuthProcessor {

    public void process(GameSession gameSession, ClientRequest request) throws Exception {
        //获取当前用户的房间对象
        RoomVO roomVo = gameSession.getRole(Avatar.class).getRoomVO();
        if(roomVo != null){
            //根据房间拿到对应的房间逻辑处理对象
            RoomLogic roomLogic = RoomManager.getInstance().getRoom(roomVo.getRoomId());
            if(roomLogic != null){
                Avatar avatar = gameSession.getRole(Avatar.class);
                roomLogic.readyGame(avatar);
            }
        }else{
            gameSession.sendMsg(new ErrorResponse(ErrorCode.Error_000005));
        }
    }
}
