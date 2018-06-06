package com.chess.mahjong.gameserver.commons.message;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * @Author: 周润斌
 * @Date: create in 下午 6:19 2018-03-01
 * @Description:
 * 此接口声明了响应消息编码函数，服务端生产的每个响应消息都必须实现此接口。
 * 这里声明的函数在protocolEncoder中会被自动调用，编码成byte stream发送到客户端。
 */
public interface ResponseMsg {

    /**
     * 设置消息号
     * @param code
     */
    void setMsgCode(int code);

    /**
     * 返回消息的整体封包
     * @return
     */
    IoBuffer entireMsg();

    /**
     * 释放资源(数据流,对象引用)
     */
    void release();
}
