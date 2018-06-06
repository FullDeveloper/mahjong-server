package com.chess.mahjong.gameserver.service;

import com.chess.mahjong.persistent.daoimpl.NoticeTableDaoImpl;
import com.chess.mahjong.persistent.entity.NoticeTable;
import com.chess.mahjong.persistent.mapper.NoticeTableMapper;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 19:32
 * Description:
 */
public class NoticeTableService {

    private NoticeTableMapper noticeTableMapper;

    private static NoticeTableService noticeTableService = new NoticeTableService();

    public static NoticeTableService getInstance(){
        return noticeTableService;
    }

    public void initSetSession(SqlSessionFactory sqlSessionFactory){
        noticeTableMapper = new NoticeTableDaoImpl(sqlSessionFactory);
    }

    /**
     * 获取最近一次公告
     */
    public NoticeTable selectRecentlyObject(){
        NoticeTable noticeTable = null;
        try{
            noticeTable = noticeTableMapper.selectRecentlyObject();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return noticeTable;
    }

}
