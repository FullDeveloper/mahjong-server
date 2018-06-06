package com.chess.mahjong.gameserver.commons.msg.processor.common;

import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/3 0003
 * Time: 23:58
 * Description:
 */
public abstract class MsgProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MsgProcessor.class);

    public void handle(GameSession gameSession, ClientRequest request){
        try {
            process(gameSession,request);
        } catch (Exception e) {
            logger.error("消息处理出错，msg code:"+request.getMsgCode());
            e.printStackTrace();
        }
    }

    public abstract void process(GameSession gameSession,ClientRequest request)throws Exception;


}
