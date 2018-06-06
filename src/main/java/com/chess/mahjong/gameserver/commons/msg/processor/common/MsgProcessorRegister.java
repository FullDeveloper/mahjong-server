package com.chess.mahjong.gameserver.commons.msg.processor.common;

import com.chess.mahjong.context.ConnectAPI;
import com.chess.mahjong.gameserver.commons.msg.processor.createroom.CreateRoomMsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.joinroom.JoinRoomMsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.login.LoginMsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.messagebox.MessageBoxMsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.outroom.OutRoomMsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.startgame.PrepareGameMSGProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 0:01
 * Description:
 */
public enum MsgProcessorRegister {

    /**登陆处理器*//**断线重连**/
    login(ConnectAPI.LOGIN_REQUEST,new LoginMsgProcessor()),
    /**创建 房间*/
    createRoom(ConnectAPI.CREATEROOM_REQUEST,new CreateRoomMsgProcessor()),
    /**游戏开始前准备*/
    prepareGame(ConnectAPI.PrepareGame_MSG_REQUEST,new PrepareGameMSGProcessor()),
    /**退出房间*/
    outRoom(ConnectAPI.OUT_ROOM_REQUEST,new OutRoomMsgProcessor()),
    /**语音消息*/
    messageBox(ConnectAPI.MessageBox_Request,new MessageBoxMsgProcessor()),
    /**进入游戏房间*/
    joinRoom(ConnectAPI.JOIN_ROOM_REQUEST,new JoinRoomMsgProcessor());
    private int msgCode;
    private MsgProcessor processor;

    /**
     * 不允许外部创建
     * @param msgCode
     * @param processor
     */
    private MsgProcessorRegister(int msgCode,MsgProcessor processor){
        this.msgCode = msgCode;
        this.processor = processor;
    }

    /**
     * 获取协议号
     * @return
     */
    public int getMsgCode(){
        return this.msgCode;
    }

    /**
     * 获取对应的协议解晰类对象
     * @return
     */
    public MsgProcessor getMsgProcessor(){
        return this.processor;
    }

}
