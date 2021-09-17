package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDao {

    //select查询
    List<?> selectAllSql(@Param("selSql")String sql);
    //update修改
    Integer updateSql(@Param("updSql") String sql);
    //insert新增
    Integer insertSql(@Param("insSql")String sql);
    //delete删除
    Integer deleteSql(@Param("delSql")String sql);

    //获取总数
    Integer selectConutSql(@Param("conSql")String sql);
}
