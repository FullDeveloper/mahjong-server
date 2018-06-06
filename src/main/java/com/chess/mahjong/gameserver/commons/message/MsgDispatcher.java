package com.chess.mahjong.gameserver.commons.message;

import com.chess.mahjong.gameserver.commons.msg.processor.common.INotAuthProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.common.MsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.common.MsgProcessorRegister;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/3 0003
 * Time: 23:52
 * Description: 消息分发器，根据消息号，找到相应的消息处理器
 */
public class MsgDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(MsgDispatcher.class);

    private Map<Integer, MsgProcessor> processorsMap = new HashMap<Integer, MsgProcessor>();

    /**
     * 通过协议号得到MsgProcessor
     * @param msgCode
     * @return
     */
    public MsgProcessor getMsgProcessor(int msgCode){
        return processorsMap.get(msgCode);
    }

    public MsgDispatcher(){
        for(MsgProcessorRegister register :MsgProcessorRegister.values()){
            processorsMap.put(register.getMsgCode(), register.getMsgProcessor());
        }
        logger.info("初始化 消息处理器成功。。。");
    }

    /**
     * 派发消息协议
     * @param gameSession
     * @param clientRequest
     */
    public void dispatchMsg(GameSession gameSession, ClientRequest clientRequest) {

        int msgCode = clientRequest.getMsgCode();
        if(msgCode == 1000){//客户端请求断开链接
            //gameSession.close();
        }
        //if(msgCode%2==0){//请求协议号必须是奇数
        //	return;
        //}
        MsgProcessor processor = getMsgProcessor(msgCode);
        if(gameSession.isLogin() || processor instanceof INotAuthProcessor){
            processor.handle(gameSession, clientRequest);
        }

    }

}
