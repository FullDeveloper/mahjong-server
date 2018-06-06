package com.chess.mahjong.gameserver.service;

import com.chess.mahjong.gameserver.pojo.RoomVO;
import com.chess.mahjong.persistent.daoimpl.RoomInfoDaoImpl;
import com.chess.mahjong.persistent.entity.RoomInfo;
import com.chess.mahjong.persistent.mapper.RoomInfoMapper;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/6 0006
 * Time: 20:26
 * Description:
 */
public class RoomInfoService {

    private RoomInfoMapper roomInfoMap;

    private static RoomInfoService gameService = new RoomInfoService();

    public static RoomInfoService getInstance(){
        return gameService;
    }

    public void initSetSession(SqlSessionFactory sqlSessionFactory){
        roomInfoMap = new RoomInfoDaoImpl(sqlSessionFactory);
    }

    /**
     * 创建roomInfo
     * @param roomVO
     * @return
     */
    public int createRoomInfo(RoomVO roomVO){
        //创建信息的同事创建其关联表
        RoomInfo room = new RoomInfo();
        room.setIsHong(roomVO.getHong()?"1":"0");
        room.setGameType(roomVO.getRoomType()+"");
        room.setMa(roomVO.getMa());
        room.setRoomId(roomVO.getRoomId());
        room.setSevenDouble(roomVO.getSevenDouble()?"1":"0");;
        room.setXiaYu(roomVO.getXiaYu());
        room.setZiMo(roomVO.getZiMo()==0?"0":"1");
        room.setName(roomVO.getName());
        room.setAddWordCard(roomVO.isAddWordCard()?"1":"0");
        room.setCreateTime(new Date());
        room.setCardNumb(roomVO.getRoundNumber()/8);
        //创建RoomInfo表
        int index = roomInfoMap.insertSelective(room);
        //roomVO.setId(room.getId());
        return index;
    }


}
