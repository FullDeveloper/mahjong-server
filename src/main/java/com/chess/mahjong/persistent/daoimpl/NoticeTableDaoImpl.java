package com.chess.mahjong.persistent.daoimpl;

import com.chess.mahjong.persistent.entity.NoticeTable;
import com.chess.mahjong.persistent.entity.NoticeTableExample;
import com.chess.mahjong.persistent.mapper.NoticeTableMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 19:33
 * Description:
 */
public class NoticeTableDaoImpl implements NoticeTableMapper {

    private SqlSessionFactory sqlSessionFactory;

    public NoticeTableDaoImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }



    public int countByExample(NoticeTableExample example) {
        return 0;
    }

    public int deleteByExample(NoticeTableExample example) {
        return 0;
    }

    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(NoticeTable record) {
        return 0;
    }

    public int insertSelective(NoticeTable record) {
        return 0;
    }

    public List<NoticeTable> selectByExample(NoticeTableExample example) {
        return null;
    }

    public NoticeTable selectByPrimaryKey(Integer id) {
        return null;
    }

    public int updateByExampleSelective(NoticeTable record, NoticeTableExample example) {
        return 0;
    }

    public int updateByExample(NoticeTable record, NoticeTableExample example) {
        return 0;
    }

    public int updateByPrimaryKeySelective(NoticeTable record) {
        return 0;
    }

    public int updateByPrimaryKey(NoticeTable record) {
        return 0;
    }

    public NoticeTable selectRecentlyObject() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        NoticeTable noticeTable = null;
        try {
            NoticeTableMapper noticeTableMapper = sqlSession.getMapper(NoticeTableMapper.class);
            noticeTable =  noticeTableMapper.selectRecentlyObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return noticeTable;
    }
}
