package com.chess.mahjong.persistent.mapper;

import com.chess.mahjong.persistent.entity.RoomInfo;
import com.chess.mahjong.persistent.entity.RoomInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int countByExample(RoomInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int deleteByExample(RoomInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int insert(RoomInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int insertSelective(RoomInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    List<RoomInfo> selectByExample(RoomInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    RoomInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") RoomInfo record, @Param("example") RoomInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") RoomInfo record, @Param("example") RoomInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RoomInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RoomInfo record);
}