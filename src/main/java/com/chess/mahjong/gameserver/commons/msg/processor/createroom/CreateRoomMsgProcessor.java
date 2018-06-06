package com.chess.mahjong.gameserver.commons.msg.processor.createroom;

import com.chess.mahjong.context.ErrorCode;
import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import com.chess.mahjong.gameserver.commons.msg.processor.common.INotAuthProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.common.MsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.response.ErrorResponse;
import com.chess.mahjong.gameserver.commons.msg.response.createroom.CreateRoomResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.manager.RoomManager;
import com.chess.mahjong.gameserver.pojo.AvatarVO;
import com.chess.mahjong.gameserver.pojo.RoomVO;
import com.chess.mahjong.util.JsonUtilTool;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/6 0006
 * Time: 20:10
 * Description:
 */
public class CreateRoomMsgProcessor extends MsgProcessor implements INotAuthProcessor {

    public void process(GameSession gameSession, ClientRequest request) throws Exception {
        String message = request.getString();
        RoomVO roomVO = (RoomVO) JsonUtilTool.fromJson(message, RoomVO.class);
        if(gameSession.isLogin()){
            Avatar avatar = gameSession.getRole(Avatar.class);
            AvatarVO avatarVo = avatar.avatarVO;
            if(avatarVo.getAccount().getRoomCard() >= roomVO.getRoundNumber()/8){
                if(avatarVo.getRoomId() == 0){
                    RoomManager.getInstance().createRoom(avatar,roomVO);
                    gameSession.sendMsg(new CreateRoomResponse(1,roomVO.getRoomId()+""));
                }else{
                    gameSession.sendMsg(new CreateRoomResponse(1,avatarVo.getRoomId()+""));
                }
            }else{
                //房卡不足
                gameSession.sendMsg(new ErrorResponse(ErrorCode.Error_000014));
            }
        }else{
            //二期优化注释
        }
    }

}
