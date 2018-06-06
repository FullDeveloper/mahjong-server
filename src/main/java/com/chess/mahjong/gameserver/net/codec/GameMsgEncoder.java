package com.chess.mahjong.gameserver.net.codec;

import com.chess.mahjong.gameserver.commons.message.ResponseMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: 周润斌
 * @Date: create in 下午 6:16 2018-03-01
 * @Description:
 */
public class GameMsgEncoder extends ProtocolEncoderAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GameMsgEncoder.class);

    /**
     * 此处实现对ResponseMsg的编码工作,并将它写入输出流中
     * @param ioSession
     * @param o
     * @param protocolEncoderOutput
     * @throws Exception
     */
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        ResponseMsg value = (ResponseMsg) o;
        IoBuffer io = value.entireMsg();
        logger.info("处理的消息:" + value.entireMsg()+";总长度为:"+io.getLong());
        protocolEncoderOutput.write(value.entireMsg());
        protocolEncoderOutput.flush();
        value.release();
    }
}
