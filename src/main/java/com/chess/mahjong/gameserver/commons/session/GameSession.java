package com.chess.mahjong.gameserver.commons.session;

import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.message.ResponseMsg;
import com.chess.mahjong.gameserver.context.GameServerContext;
import com.chess.mahjong.gameserver.manager.GameSessionManager;
import com.chess.mahjong.gameserver.sprite.GameObj;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class GameSession implements GameObj {

    /**
     * IoSession
     */
    private IoSession session;
    /**
     * 用户的服务器地址
     */
    private String address;

    private boolean isLogin=false;

    /**
     *
     */
    private Object role;
    /**
     * Mina 自带的AttributeKey 类来定义保存在IoSession 中的对象的键值，这样可以有效的防止键值重复
     */
    private static final AttributeKey KEY_PLAYER_SESSION = new AttributeKey(GameSession.class, "player.session");

    public GameSession(IoSession session){
        this.session = session;
        this.session.setAttribute(KEY_PLAYER_SESSION, this);
        SocketAddress socketaddress = session.getRemoteAddress();
        InetSocketAddress s = (InetSocketAddress) socketaddress;
        address = s.getAddress().getHostAddress();
        System.out.println("一名来自: " + address + "的用户");
    }

    /**
     * 得到一个GameSession的实例化对象
     * @param session
     * @return
     */
    public static GameSession getInstance(IoSession session) {
        Object playerObj = session.getAttribute(KEY_PLAYER_SESSION);
        session.getService().getManagedSessions();
        return (GameSession) playerObj;
    }

    /**
     * 发送消息给客户端
     * @param msg
     * @return
     * @throws InterruptedException
     */
    public WriteFuture sendMsg(ResponseMsg msg)  {
        if (session == null || !session.isConnected() || session.isClosing()) {
            //system.out.println("session == "+session+" session.isConnected ==  "+session.isConnected()+" session.isClosing =  "+session.isClosing());
            return null;
        }
        return session.write(msg);
    }

    @SuppressWarnings("unchecked")
    /**
     * 得到角色信息
     */
    public <T> T getRole(Class<T> t){
        return (T)this.role;
    }

    /**
     * 关闭SESSION
     */
    public void close(){
        if(session != null ) {
            session.close(false);// 2016-8-29短线时不关闭用户session，等
            Avatar avatar = getRole(Avatar.class);
            if(avatar != null){
                //GameSessionManager.getInstance().removeGameSession(avatar);
                GameServerContext.add_offLine_Character(avatar);
                GameServerContext.remove_onLine_Character(avatar);
                GameSessionManager.getInstance().sessionMap.remove("uuid_"+avatar.getUuId());
                avatar.avatarVO.setOnLine(false);
                //把用户数据保留半个小时
                //TimeUitl.delayDestroy(avatar,60*30*1000);
                /*if(avatar.avatarVO.getRoomId() != 0){
                    RoomLogic roomLogic =RoomManager.getInstance().getRoom(avatar.avatarVO.getRoomId());
                    if(roomLogic != null ){
                        if(avatar.getRoomVO().getPlayerList().size() >= 2){
                            //房间还有其他玩家，则向其他玩家发送离线玩家消息
                            for (Avatar ava :roomLogic.getPlayerList()) {
                                if(avatar.getUuId() != ava.getUuId()){
                                    //发送离线通知
                                    ava.getSession().sendMsg(new OffLineResponse(1,avatar.getUuId()+""));
                                    //同意解散房间人数 设置为0,有人掉线就取消解散房间
                                    roomLogic.setDissolveCount(1);
                                    roomLogic.setDissolve(true);
                                }
                            }
                        }
                    }
                }*/
            }
        }
    }

    /**
     *
     * @param isLogin
     */
    public  void setLogin(boolean isLogin){
        this.isLogin = isLogin;
    }

    /**
     * 是否登录
     * @return
     */
    public boolean isLogin(){
        return this.isLogin;
    }

    /**
     * 保存角色信息
     * @param obj
     */
    public void setRole(Object obj){
        this.role = obj;
    }

    public void destroyObj() {
        close();
    }
}
