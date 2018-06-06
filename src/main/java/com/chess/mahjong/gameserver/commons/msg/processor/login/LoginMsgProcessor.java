package com.chess.mahjong.gameserver.commons.msg.processor.login;

import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.initial.Params;
import com.chess.mahjong.gameserver.commons.message.ClientRequest;
import com.chess.mahjong.gameserver.commons.msg.processor.common.INotAuthProcessor;
import com.chess.mahjong.gameserver.commons.msg.processor.common.MsgProcessor;
import com.chess.mahjong.gameserver.commons.msg.response.LoginResponse;
import com.chess.mahjong.gameserver.commons.msg.response.host.HostNoticeResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.context.GameServerContext;
import com.chess.mahjong.gameserver.manager.GameSessionManager;
import com.chess.mahjong.gameserver.pojo.AvatarVO;
import com.chess.mahjong.gameserver.pojo.LoginVO;
import com.chess.mahjong.gameserver.service.AccountService;
import com.chess.mahjong.gameserver.service.NoticeTableService;
import com.chess.mahjong.persistent.entity.Account;
import com.chess.mahjong.persistent.entity.NoticeTable;
import com.chess.mahjong.util.JsonUtilTool;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 13:35
 * Description:
 */
public class LoginMsgProcessor extends MsgProcessor implements INotAuthProcessor {

    public void process(GameSession gameSession, ClientRequest request) throws Exception {
        String message = request.getString();
        //将消息转换成登陆vo对象
        LoginVO loginVO = JsonUtilTool.fromJson(message,LoginVO.class);
        //查看数据库中是否存在该用户
        Account account = AccountService.getInstance().selectAccount(loginVO.getOpenId());
        if(account == null){
            //创建新用户并登录
            account = new Account();
            account.setOpenId(loginVO.getOpenId());
            account.setUuid(AccountService.getInstance().selectMaxId()+100000);
            account.setRoomCard(Params.initialRoomCard);
            account.setHeadIcon(loginVO.getHeadIcon());
            account.setNickname(loginVO.getNickname());
            account.setCity(loginVO.getCity());
            account.setProvince(loginVO.getProvince());
            account.setSex(loginVO.getSex());
            account.setUnionid(loginVO.getUnionid());
            account.setPrizeCount(Params.initialPrizeCount);
            account.setCreateTime(new Date());
            account.setActualCard(Params.initialRoomCard);
            account.setTotalCard(Params.initialRoomCard);
            account.setStatus("0");
            account.setIsGame("0");
            int flag = AccountService.getInstance().createAccount(account);
            //int flag = 1;
            //判断用户是否插入成功
            if(flag == 0){
                gameSession.sendMsg(new LoginResponse(0,null));
            }else{
                //成功的操作
                Avatar tempAva = new Avatar();
                AvatarVO tempAvaVo = new AvatarVO();
                tempAvaVo.setAccount(account);
                tempAvaVo.setIP(loginVO.getIP());
                tempAva.avatarVO = tempAvaVo;
                //将登陆和在线状态设置为true,将该用户存放到在线列表的map集合中并且通知前端已经登陆
                loginAction(gameSession,tempAva);
                //把session放入到GameSessionManager
                GameSessionManager.getInstance().putGameSessionInHashMap(gameSession,tempAva.getUuId());
                //公告发送给玩家
                Thread.sleep(3000);
                NoticeTable notice = null;
                try {
                    notice = NoticeTableService.getInstance().selectRecentlyObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String content = notice.getContent();
                gameSession.sendMsg(new HostNoticeResponse(1, content));
            }
        }else{
            //如果玩家是掉线的，则直接从缓存(GameServerContext)中取掉线玩家的信息
            //判断用户是否已经进行断线处理(如果前端断线时间过短，后台则可能还未来得及把用户信息放入到离线map里面，就已经登录了，所以取出来就会是空)
        }

    }

    /**
     * 登录操作
     * @param gameSession
     * @param avatar
     */
    public void loginAction(GameSession gameSession,Avatar avatar){
        gameSession.setRole(avatar);
        gameSession.setLogin(true);
        avatar.setSession(gameSession);
        avatar.avatarVO.setOnLine(true);
        //加入到在线玩家map中
        GameServerContext.add_onLine_Character(avatar);
        //通知前端已经登陆
        gameSession.sendMsg(new LoginResponse(1,avatar.avatarVO));
    }



}
