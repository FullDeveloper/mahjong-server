package com.chess.mahjong.gameserver;

import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.pojo.AvatarVO;
import com.chess.mahjong.gameserver.pojo.RoomVO;
import com.chess.mahjong.gameserver.sprite.GameObj;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 17:56
 * Description:
 */
public class Avatar implements GameObj {

    public AvatarVO avatarVO;

    private RoomVO roomVO;

    /**
     * session
     */
    private GameSession session;

    public void setRoomVO(RoomVO roomVO) {
        this.roomVO = roomVO;
        if(avatarVO != null){
            avatarVO.setRoomId(roomVO.getRoomId());
        }
    }

    /**
     * 获取玩家session
     * @return
     */
    public void setSession(GameSession gameSession) {
        this.session = gameSession;
    }

    /**
     * 获取玩家session
     * @return
     */
    public GameSession getSession() {
        return session;
    }

    /**
     * 获取用户uuid
     * @return
     */
    public int getUuId(){
        return avatarVO.getAccount().getUuid();
    }

    public RoomVO getRoomVO() {
        return roomVO;
    }

    public void destroyObj() {

    }
}
