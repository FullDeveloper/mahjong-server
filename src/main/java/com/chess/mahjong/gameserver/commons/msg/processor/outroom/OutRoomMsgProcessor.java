package com.chess.mahjong.gameserver.commons.msg.processor.outroom;

import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.logic.RoomLogic;
import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import com.chess.mahjong.gameserver.commons.msg.processor.common.INotAuthProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.common.MsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.response.outroom.OutRoomResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.manager.RoomManager;
import com.chess.mahjong.util.GlobalUtil;
import net.sf.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/7 0007
 * Time: 19:30
 * Description:
 */
public class OutRoomMsgProcessor extends MsgProcessor implements
        INotAuthProcessor {

    public void process(GameSession gameSession, ClientRequest request) throws Exception {
        //检查是否登录
        if(GlobalUtil.checkIsLogin(gameSession)) {
            JSONObject json = JSONObject.fromObject(request.getString());
            int roomId = (Integer) json.get("roomId");
            Avatar avatar = gameSession.getRole(Avatar.class);
            //判断是否存在房间
            if (avatar != null && roomId != 0) {
                RoomLogic roomLogic = RoomManager.getInstance().getRoom(roomId);
                if (roomLogic != null) {
                    //退出房间
                    roomLogic.exitRoom(avatar);
                } else {
                    //system.out.println("房间号有误");
                    JSONObject js = new JSONObject();
                    js.put("accountName", "");
                    js.put("status_code", "1");
                    js.put("error", "房间号有误");
//					accountName:”名字”//退出房间玩家的名字(为空则表示是通知的自己)
//					status_code:”0”//”0”退出成功，”1” 退出失败
//					mess：”消息”
                    gameSession.sendMsg(new OutRoomResponse(0, json.toString()));
                }
            }
        }
    }
}
