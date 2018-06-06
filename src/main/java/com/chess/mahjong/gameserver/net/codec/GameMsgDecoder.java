package com.chess.mahjong.gameserver.net.codec;

import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: 周润斌
 * @Date: create in 下午 6:16 2018-03-01
 * @Description:
 * 消息解码器。将连续的字节按照协议规范分割成完整的消息包，并包装成ClientRequest。
 */
public class GameMsgDecoder extends CumulativeProtocolDecoder {

    private static final Logger logger = LoggerFactory.getLogger(GameMsgDecoder.class);

    /**
     * flag(1 byte)+length(4 byte,后边内容的长度)+protocol code(4 byte)+content
     * length的长度包括  ：消息号+ 内容
     * @param ioSession
     * @param ioBuffer
     * @param protocolDecoderOutput
     * @return
     * @throws Exception
     */
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        synchronized (ioBuffer){
            //数据不完整
            //Remaining: 返回limit-position,返回缓冲器中的剩余字节
            if(ioBuffer.remaining() < (MsgProtocol.flagSize+MsgProtocol.lengthSize+MsgProtocol.msgCodeSize)){
                logger.info("数据包长度不足");
                return false;
            }
            System.out.println(ioBuffer.toString());
            //mark: 取当前的position的快照标记mark
            ioBuffer.mark();//0
            byte flag = ioBuffer.get(); // flag 备用  读取1个字节 position从0开始 往后移2个 = 1
            if(flag == 1){
                int length = ioBuffer.getInt(); //读取长度字段 读取4个字节 此时position=5
                if(length <= 0 || length > MsgProtocol.maxPackLength){
                    logger.info("数据包长度异常! 长度为:"+length);
                    return false;
                }
                //remaining()是返回limit-position的值
                int surplus = ioBuffer.remaining(); //剩余 limit-position
                if(surplus >= length){
                    int preLimit = ioBuffer.limit(); //记录下当前的limit值
                    /**
                     * 这行代码有一个bug，
                     * 读取协议内容时，如果第一个字节不是1，则越过此字节继续往后的读，直到读到1，
                     * 然而在设置limit时没有考虑到越过去的flag之前的字节，从而导致设置的limit比本应设置的位置小。
                     * 所以导致，iobuffer中当前position到设置的limit的长度小于我们要读取的length。
                     * 结果导致抛出BufferUnderflowException
                     */
                    //iobuffer.limit(MsgProtocol.flagSize+MsgProtocol.lengthSize+length);
                    //如果position>limit, position = limit,如果mark>limit, 重置mark
                    ioBuffer.limit(ioBuffer.position()+length);
                    byte[] body = new byte[length];
                    ioBuffer.get(body);
                    ioBuffer.limit(preLimit);
                    ClientRequest message = new ClientRequest(body);
                    protocolDecoderOutput.write(message);
                    return true;
                }else{
                    logger.info("数据包尚不完整！");
                    return false;
                }
            }else{
                logger.info("flag错误");
                return false;
            }
        }

    }

    public void finishDecode(IoSession session, ProtocolDecoderOutput out)
            throws Exception {
    }

    public void dispose(IoSession session) throws Exception {
    }

}
