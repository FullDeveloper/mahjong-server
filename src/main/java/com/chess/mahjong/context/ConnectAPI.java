package com.chess.mahjong.context;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 0:02
 * Description:
 */
public class ConnectAPI {

    public static int LOGIN_REQUEST = 0x000001;
    public static int LOGIN_RESPONSE = 0x000002;
    //断线重新加入房间
    public static int BACK_LOGIN_RESPONSE = 0x001002;


    //游戏错误码返回
    public static int ERROR_RESPONSE = 0xffff09;
    //通知前端进行断线操作
    public static int BREAK_LINE_RESPONSE = 0x211211;

    //后台广告链接通知/后台充卡链接通知  公用request  不公用response
    public static int HOST_SEND_REQUEST = 0x158888;
    public static int HOST_SEND_RESPONSE = 0x157777;
    //创建房间
    public static int CREATEROOM_REQUEST = 0x00009;
    public static int CREATEROOM_RESPONSE = 0x00010;

    //准备游戏
    public static int  PrepareGame_MSG_REQUEST = 0x333333;
    public static int PrepareGame_MSG_RESPONSE = 0x444444;

    //退出房间
    public static int OUT_ROOM_REQUEST  = 0x000013;
    public static int OUT_ROOM_RESPONSE  = 0x000014;

    //固定语音盒子协议
    public static int MessageBox_Request = 203;
    public static int MessageBox_Notice = 204;

    //加入房间请求
    public static int JOIN_ROOM_REQUEST  = 0x000003;
    public static int JOIN_ROOM_RESPONSE  = 0x000004;
    //加入房间通知
    public static int JOIN_ROOM_NOTICE = 0x10a004;

    //断线玩家重连之后 其他三家人接收信息
    public static int OTHER_BACK_LOGIN_RESPONSE = 0x001111;

}
