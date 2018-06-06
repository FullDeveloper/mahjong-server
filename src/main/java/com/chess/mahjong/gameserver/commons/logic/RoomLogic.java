package com.chess.mahjong.gameserver.commons.logic;

import com.chess.mahjong.context.ErrorCode;
import com.chess.mahjong.gameserver.Avatar;
import com.chess.mahjong.gameserver.commons.msg.response.ErrorResponse;
import com.chess.mahjong.gameserver.commons.msg.response.joinroom.JoinRoomNotice;
import com.chess.mahjong.gameserver.commons.msg.response.joinroom.JoinRoomResponse;
import com.chess.mahjong.gameserver.commons.msg.response.login.BackLoginResponse;
import com.chess.mahjong.gameserver.commons.msg.response.login.OtherBackLoginResponse;
import com.chess.mahjong.gameserver.commons.msg.response.outroom.OutRoomResponse;
import com.chess.mahjong.gameserver.commons.msg.response.startgame.PrepareGameResponse;
import com.chess.mahjong.gameserver.commons.session.GameSession;
import com.chess.mahjong.gameserver.context.GameServerContext;
import com.chess.mahjong.gameserver.manager.RoomManager;
import com.chess.mahjong.gameserver.pojo.AvatarVO;
import com.chess.mahjong.gameserver.pojo.RoomVO;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/6 0006
 * Time: 20:22
 * Description:
 */
public class RoomLogic {

    private List<Avatar> playerList;
    private boolean isBegin = false;
    private  Avatar createAvatar;
    private RoomVO roomVO;
    private PlayCardsLogic playCardsLogic;

    /**
     * 是否已经解散房间
     */
    private  boolean hasDissolve = false;

    /**
     * 房间使用次数
     */
    private int count=0;

    public RoomLogic(RoomVO roomVO){
        this.roomVO = roomVO;
        if(roomVO != null){
            count = roomVO.getRoundNumber();
        }
    }

    /**
     * 创建房间
     * @param avatar
     */
    public void CreateRoom(Avatar avatar){
        createAvatar = avatar;
        roomVO.setPlayerList(new ArrayList<AvatarVO>());
        //avatar.avatarVO.setIsReady(true);10-11注释 在游戏开始之后就已经重置准备属性为false
        playerList = new ArrayList<Avatar>();
        avatar.avatarVO.setMain(true);
        avatar.setRoomVO(roomVO);
        playerList.add(avatar);
        roomVO.getPlayerList().add(avatar.avatarVO);
    }
    /**
     * 游戏准备
     * @param
     * @throws IOException
     */
    public void readyGame(Avatar avatar) throws IOException {
        //如果当前局数等于游戏局数 或者玩牌逻辑中单局结束标记为true 并且 当前局数不等于游戏局数
        //只有单局结束之后调用准备接口才有用
        if(count == roomVO.getRoundNumber() || playCardsLogic.singleOver && count != roomVO.getRoundNumber()){
            if(count <= 0){
                //房间次数已经用完 通知玩家游戏结束
                for(Avatar ava: playerList){
                    ava.getSession().sendMsg(new ErrorResponse(ErrorCode.Error_000010));
                }
            }else{
                //设置该玩家为准备状态
                avatar.avatarVO.setReady(true);
                int avatarIndex = playerList.indexOf(avatar);
                //成功则返回 通知所有玩家该玩家已经准备
                for (Avatar ava : playerList) {
                    ava.getSession().sendMsg(new PrepareGameResponse(1,avatarIndex));
                }
                //检查是否可以开始游戏
                checkCanBeStartGame();
            }
        }
    }
    /**
     * 检测是否可以开始游戏
     * @throws IOException
     */
    public void checkCanBeStartGame() {
        if(playerList.size() == 4){
            //房间里面4个人且都准备好了则开始游戏
            boolean flag = true;
            for (Avatar avatar : playerList) {
                if(!avatar.avatarVO.isReady()){
                    //还有人没有准备
                    flag = false;
                    break;
                }
            }
            if(flag){
                isBegin = true;
                //开始游戏
                startGameRound();
            }
        }
    }

    /**
     * 退出房间
     * @param avatar
     */
    public void exitRoom(Avatar avatar){
        JSONObject json = new JSONObject();
//		accountName:”名字”//退出房间玩家的名字(为空则表示是通知的自己)
//		status_code:”0”//”0”退出成功，”1” 退出失败
//		mess：”消息”
//      type："0"  0退出房间    1解散房间
        json.put("accountName", avatar.avatarVO.getAccount().getNickname());
        json.put("status_code", "0");
        json.put("uuid", avatar.getUuId());
        if(avatar.avatarVO.isMain()){
            //群主退出房间就是解散房间
            json.put("type", "1");
            exitRoomDetail(json);
        }
        else{
            json.put("type", "0");
            //退出房间。通知房间里面的其他玩家
            exitRoomDetail(avatar, json);

        	/*for (int i= 0 ; i < playerList.size(); i++) {
        		//通知房间里面的其他玩家
        		playerList.get(i).getSession().sendMsg(new OutRoomResponse(1, json.toString()));
        	}*/
//        	avatar.avatarVO.setRoomId(0);
//        	avatar.setRoomVO(new RoomVO());
//        	playerList.remove(avatar);
//        	roomVO.getPlayerList().remove(avatar.avatarVO);
            //如果该房间里面的人数只有一个人且不是房主时，解散房间（不可能出现这样的情况）
        	/*if(playerList.size() == 1 && !playerList.get(0).avatarVO.isMain() ){
	        	  json.put("type", "1");
	          	  for (int i= 0 ; i < playerList.size(); i++) {
	          			  playerList.get(i).getSession().sendMsg(new OutRoomResponse(1, json.toString()));
	          			  roomVO.getPlayerList().remove(playerList.get(i).avatarVO);
	          			  playerList.get(i).setRoomVO(new RoomVO());
	          			  playerList.get(i).avatarVO.setRoomId(0);
	        		}
	          	  //销毁房间
	          	  RoomManager.getInstance().destroyRoom(roomVO);
	        	  playerList.clear();
	        	  roomVO.setRoomId(0);
	        	  roomVO = null;
        	}*/
        }
    }

    /**
     * 房主外的玩家退出房间，详细清除单个数据
     * @param avatar
     */
    public void exitRoomDetail(Avatar avatar ,JSONObject json){

        for (int i= 0 ; i < playerList.size(); i++) {
            //通知房间里面的其他玩家
            playerList.get(i).getSession().sendMsg(new OutRoomResponse(1, json.toString()));
        }
        roomVO.getPlayerList().remove(avatar.avatarVO);
        playerList.remove(avatar);
        //playCardsLogic.getPlayerList().remove(avatar);//只有打牌逻辑为空的时候才有退出房间一说，其他都是解散房间
        //isBegin = false;
        AvatarVO avatarVO;
        GameSession gamesession;
        avatarVO = new AvatarVO();
        avatarVO.setIP(avatar.avatarVO.getIP());
        avatarVO.setAccount(avatar.avatarVO.getAccount());
        gamesession = avatar.getSession();
        avatar = new Avatar();
        avatar.avatarVO = avatarVO;
        gamesession.setRole(avatar);
        gamesession.setLogin(true);
        avatar.setSession(gamesession);
        avatar.avatarVO.setOnLine(true);
        GameServerContext.add_onLine_Character(avatar);
        RoomManager.getInstance().removeUuidAndRoomId(avatar.avatarVO.getAccount().getUuid(), roomVO.getRoomId());
        //
    }

    /**
     * 房主退出房间，及解散房间，详细清除数据,销毁房间逻辑
     * @param
     */
    public void exitRoomDetail(JSONObject json){
        AvatarVO avatarVO;
        GameSession gamesession;
        for (Avatar avat : playerList) {
            //playCardsLogic.getPlayerList().remove(avat);//房主退出房间，打牌逻辑还未形成
			/*avatarVO = new AvatarVO();
			avatarVO.setAccount(avat.avatarVO.getAccount());
			gamesession = avat.getSession();
			avatarVO.setIP(avat.avatarVO.getIP());
			gamesession.sendMsg(new OutRoomResponse(1, json.toString()));
			avat = new Avatar();
			avat.avatarVO = avatarVO;
			gamesession.setRole(avat);
			gamesession.setLogin(true);
			avat.setSession(gamesession);
			avat.avatarVO.setIsOnLine(true);
			GameServerContext.add_onLine_Character(avat);*/
            isBegin = false;
            avatarVO = new AvatarVO();
            avatarVO.setAccount(avat.avatarVO.getAccount());
            avatarVO.setIP(avat.avatarVO.getIP());
            avatarVO.setOnLine(avat.avatarVO.isOnLine());
            gamesession = avat.getSession();
            avat = new Avatar();
            avat.avatarVO = avatarVO;
            gamesession.setRole(avat);
            avat.setSession(gamesession);
            if(avat.avatarVO.isOnLine()){
                gamesession.setLogin(true);
                avat.getSession().sendMsg(new OutRoomResponse(1, json.toString()));
                GameServerContext.add_onLine_Character(avat);
            }
            else{
                //不在线则 更新
                GameServerContext.add_offLine_Character(avat);
            }
            //删除玩家和该房间建立的关联
            RoomManager.getInstance().removeUuidAndRoomId(avat.avatarVO.getAccount().getUuid(), roomVO.getRoomId());
        }
        hasDissolve = true;
        playCardsLogic = null;//9-22新增
        RoomManager.getInstance().destroyRoom(roomVO);
    }

    /**
     * 断线重连，如果房间还未被解散的时候，则返回整个房间信息
     * @param avatar
     */
    public void returnBackAction(Avatar avatar){
        if(playCardsLogic == null){
            //只是在房间，游戏尚未开始,打牌逻辑为空
            for (int i = 0; i < playerList.size(); i++) {
                if(playerList.get(i).getUuId() != avatar.getUuId()){
                    //给其他三个玩家返回重连用户信息
                    playerList.get(i).getSession().sendMsg(new OtherBackLoginResponse(1, avatar.getUuId()+""));
                }
            }
            avatar.getSession().sendMsg(new BackLoginResponse(1, roomVO));
        }
        else{
            //playCardsLogic.returnBackAction(avatar);
        }
    }

    /**
     * 开始一回合新的游戏
     */
    private void startGameRound() {

    }

    public List<Avatar> getPlayerList() {
        return playerList;
    }

    /**
     * 加入放假
     * @param avatar
     */
    public boolean intoRoom(Avatar avatar) {
        synchronized(roomVO){
            if(playerList.size() == 4){
                try {
                    avatar.getSession().sendMsg(new ErrorResponse(ErrorCode.Error_000011));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }else{
                avatar.avatarVO.setMain(false);
                avatar.avatarVO.setRoomId(roomVO.getRoomId());//房间号也放入avatarvo中
                avatar.setRoomVO(roomVO);
                noticeJoinMess(avatar);//通知房间里面的其他几个玩家
                //将该用户添加到玩家集合中
                playerList.add(avatar);
                //添加到房间的玩家对象中
                roomVO.getPlayerList().add(avatar.avatarVO);
                //添加到房间和用户的缓存中
                RoomManager.getInstance().addUuidAndRoomId(avatar.avatarVO.getAccount().getUuid(), roomVO.getRoomId());
                avatar.getSession().sendMsg(new JoinRoomResponse(1, roomVO));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
    }

    /**
     * 当有人加入房间且总人数不够4个时，对其他玩家进行通知
     */
    private void noticeJoinMess(Avatar avatar){
        AvatarVO avatarVo = avatar.avatarVO;
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).getSession().sendMsg(new JoinRoomNotice(1,avatarVo));
        }
    }

}
