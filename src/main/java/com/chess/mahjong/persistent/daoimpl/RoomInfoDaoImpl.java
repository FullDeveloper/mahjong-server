package com.chess.mahjong.persistent.daoimpl;

import com.chess.mahjong.gameserver.service.RoomInfoService;
import com.chess.mahjong.persistent.entity.RoomInfo;
import com.chess.mahjong.persistent.entity.RoomInfoExample;
import com.chess.mahjong.persistent.mapper.RoomInfoMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/6 0006
 * Time: 20:30
 * Description:
 */
public class RoomInfoDaoImpl implements RoomInfoMapper {

    private SqlSessionFactory sqlSessionFactory;

    public RoomInfoDaoImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }


    public int countByExample(RoomInfoExample example) {
        return 0;
    }

    public int deleteByExample(RoomInfoExample example) {
        return 0;
    }

    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(RoomInfo record) {
        return 0;
    }

    public int insertSelective(RoomInfo record) {
        int flag = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            RoomInfoMapper mapper = sqlSession.getMapper(RoomInfoMapper.class);
            flag = mapper.insertSelective(record);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return flag;
    }

    public List<RoomInfo> selectByExample(RoomInfoExample example) {
        return null;
    }

    public RoomInfo selectByPrimaryKey(Integer id) {
        return null;
    }

    public int updateByExampleSelective(RoomInfo record, RoomInfoExample example) {
        return 0;
    }

    public int updateByExample(RoomInfo record, RoomInfoExample example) {
        return 0;
    }

    public int updateByPrimaryKeySelective(RoomInfo record) {
        return 0;
    }

    public int updateByPrimaryKey(RoomInfo record) {
        return 0;
    }
}
