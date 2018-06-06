package com.chess.mahjong.gameserver.pojo;

import com.chess.mahjong.persistent.entity.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 17:34
 * Description:
 */
public class AvatarVO {

    /**
     * 用户基本信息
     */
    private Account account;
    /**
     * 房间号
     */
    private int roomId;
    /**
     * 是否准备
     */
    private boolean isReady = false;
    /**
     * 是否是庄家
     */
    private boolean isMain = false;
    /**
     * 是否在线
     */
    private boolean isOnLine = false;
    /**
     * 当前分数，起始分1000
     */
    private int scores = 1000;
    /**
     * 打了的牌的字符串  1,2,3,4,5,6,1,3,5 格式
     */
    private List<Integer> chupais = new ArrayList<Integer>();
    /**
     * 普通牌张数
     */
    private int commonCards;
    /**
     * 摸牌出牌状态
     * 摸了牌/碰/杠 true  出牌了false
     * 为true 表示该出牌了    为false表示不该出牌
     */
    private boolean hasMopaiChupai = false;
    /**
     * 划水麻将  胡牌的类型(1:普通小胡(点炮/自摸)    2:大胡(点炮/自摸))
     * 放弃操作，摸牌，出牌，都需要重置
     */
    private int huType = 0;
    /**
     * 牌数组
     * /碰 1  杠2  胡3  吃4
     */
    private int[][] paiArray;
    /**
     * 存储整局牌的 杠，胡以及得分情况的对象，游戏结束时直接返回对象
     */
    private HuReturnObjectVO  huReturnObjectVO;

    private String IP;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isOnLine() {
        return isOnLine;
    }

    public void setOnLine(boolean onLine) {
        isOnLine = onLine;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public List<Integer> getChupais() {
        return chupais;
    }

    public void setChupais(List<Integer> chupais) {
        this.chupais = chupais;
    }

    public int getCommonCards() {
        return commonCards;
    }

    public void setCommonCards(int commonCards) {
        this.commonCards = commonCards;
    }

    public boolean isHasMopaiChupai() {
        return hasMopaiChupai;
    }

    public void setHasMopaiChupai(boolean hasMopaiChupai) {
        this.hasMopaiChupai = hasMopaiChupai;
    }

    public int getHuType() {
        return huType;
    }

    public void setHuType(int huType) {
        this.huType = huType;
    }

    public int[][] getPaiArray() {
        return paiArray;
    }

    public void setPaiArray(int[][] paiArray) {
        this.paiArray = paiArray;
    }

    public HuReturnObjectVO getHuReturnObjectVO() {
        return huReturnObjectVO;
    }

    public void setHuReturnObjectVO(HuReturnObjectVO huReturnObjectVO) {
        this.huReturnObjectVO = huReturnObjectVO;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
