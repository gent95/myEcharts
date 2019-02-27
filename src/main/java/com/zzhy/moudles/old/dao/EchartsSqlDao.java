package com.zzhy.moudles.old.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zzhy.moudles.old.entity.EChartsSqlEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:43
 * @Version 1.0
 */
@Mapper
public interface EchartsSqlDao extends BaseMapper<EChartsSqlEntity> {
    List<Map<String,Object>> executeSql(@Param("sql") String sql);

    List<Object> executeSql_1(@Param("sql") String sql);
}
