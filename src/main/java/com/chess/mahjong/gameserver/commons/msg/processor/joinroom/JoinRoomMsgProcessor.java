package com.chess.mahjong.gameserver.commons.msg.processor.joinroom;

import com.chess.mahjong.context.ErrorCode;
import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.logic.RoomLogic;
import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import com.chess.mahjong.gameserver.commons.msg.processor.common.INotAuthProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.common.MsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.response.ErrorResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.manager.GameSessionManager;
import com.chess.mahjong.gameserver.manager.RoomManager;
import com.chess.mahjong.util.GlobalUtil;
import net.sf.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 22:53
 * Description:
 */
public class JoinRoomMsgProcessor extends MsgProcessor implements INotAuthProcessor {
    @Override
    public void process(GameSession gameSession, ClientRequest request) throws Exception {
        //检查是否登录
        if(GlobalUtil.checkIsLogin(gameSession)) {
            JSONObject json = JSONObject.fromObject(request.getString());
            int roomId = (int)json.get("roomId");
            //拿到将要加入的房间的逻辑处理对象
            RoomLogic roomLogic = RoomManager.getInstance().getRoom(roomId);
            if(roomLogic != null){
                //拿到当前用户信息
                Avatar avatar = gameSession.getRole(Avatar.class);
                //如果已经有了房间
                if(avatar.avatarVO.getRoomId() != 0){
                    //avatar.getSession().sendMsg(new ErrorResponse(ErrorCode.Error_000017));
                    // 在房间则 直接回到房间
                    //把session放入到GameSessionManager,并且移除以前的session
                    GameSessionManager.getInstance().putGameSessionInHashMap(gameSession,avatar.getUuId());
                    roomLogic.returnBackAction(avatar);
                    return;
                }
                //加入房间
                roomLogic.intoRoom(avatar);
            }else{
                gameSession.sendMsg(new ErrorResponse(ErrorCode.Error_000018));
            }
        }else{
            gameSession.destroyObj();
        }
    }
}
