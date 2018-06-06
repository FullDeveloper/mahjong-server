package com.chess.mahjong.gameserver.commons.msg.processor.messagebox;

import com.chess.mahjong.context.ErrorCode;
import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.logic.RoomLogic;
import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import com.chess.mahjong.gameserver.commons.msg.processor.common.INotAuthProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.common.MsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.response.ErrorResponse;
import com.chess.mahjong.gameserver.commons.msg.response.messagebox.MessageBoxResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.manager.RoomManager;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 21:24
 * Description:
 */
public class MessageBoxMsgProcessor extends MsgProcessor implements INotAuthProcessor {
    @Override
    public void process(GameSession gameSession, ClientRequest request) throws Exception {
        String code = request.getString();
        //得到该房间逻辑对象
        RoomLogic roomLogic = RoomManager.getInstance().getRoom(gameSession.getRole(Avatar.class).getRoomVO().getRoomId());
        if(roomLogic != null){
            //得到自己的uuid
            int selfUuid = gameSession.getRole(Avatar.class).getUuId();
            //获取玩家列表
            List<Avatar> playerList = roomLogic.getPlayerList();
            if(playerList != null){
                for(int i=0;i<playerList.size();i++){
                    //给房间内其他用户发送对应的语音
                    if(playerList.get(i).getUuId() != selfUuid){
                        playerList.get(i).getSession().sendMsg(new MessageBoxResponse(code));
                    }
                }
            }
        }else{
            gameSession.sendMsg(new ErrorResponse(ErrorCode.Error_000005));
        }
    }
}
