package com.chess.mahjong.gameserver.commons.message;

import com.chess.mahjong.gameserver.net.codec.MsgProtocol;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 17:28
 * Description:
 */
public class ServerResponse implements ResponseMsg {

    protected MsgBodyWrap output = MsgBodyWrap.newInstance4Out();

    private int msgCode;
    private int status;
    /**必须调用此方法设置消息号*/
    public ServerResponse(int status,int msgCode) {
        setStatus(status);
        setMsgCode(msgCode);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsgCode(int code) {
        msgCode = code;
    }

    public IoBuffer entireMsg() {
        byte[] body = output.toByteArray();
			/* 标志 byte 长度short */
        int length = MsgProtocol.flagSize+MsgProtocol.lengthSize+MsgProtocol.msgCodeSize+ body.length+4;
        //System.out.println("计算长度"+length);
        //初始化IoBuffer容量
        IoBuffer buf = IoBuffer.allocate(length);
        buf.put(MsgProtocol.defaultFlag);//flag
        buf.putInt(length);//lengh
        buf.putInt(msgCode);
        buf.putInt(status);
        buf.put(body);
        buf.flip();
        //System.out.println("buf实际长度---"+buf.capacity());
        return buf;
    }

    public void release() {

    }
}
