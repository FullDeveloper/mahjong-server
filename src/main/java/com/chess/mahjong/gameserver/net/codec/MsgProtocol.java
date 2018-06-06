package com.chess.mahjong.gameserver.net.codec;

/**
 * @Author: 周润斌
 * @Date: create in 下午 6:26 2018-03-01
 * @Description:
 */
public interface MsgProtocol {

    //默认flag值
    byte defaultFlag = 1;
    //最大长度
    int maxPackLength = 1024 * 5;
    //标识数占得 byte位数
    int flagSize = 1;
    //协议中 长度部分 占用的byte数 其值表示(协议号 + 内容) 的长度
    int lengthSize = 4;
    //消息号占用的byte数
    int msgCodeSize = 4;

}
