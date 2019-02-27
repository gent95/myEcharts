package com.zzhy.moudles.now.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zzhy.moudles.now.entity.EchtSqlEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ${comments}
 * 
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-11-27 15:44:40
 */
@Mapper
public interface EchtSqlDao extends BaseMapper<EchtSqlEntity> {
	/**
	 * 根据面板编号查询
	 * @param optId
	 * @return
	 */
	List<EchtSqlEntity> selectByOptId(@Param("optId") Integer optId);

	/**
	 * 查询sqlElmt聚合类
	 * @param optId
	 * @return
	 */
	List<HashMap<String,Object>> selectSqlElmtVo(Integer optId);

	/**
	 * 查询集合数据类型sql
	 * @param sql
	 * @return
	 */
	List<LinkedHashMap<String,Object>> excu_sql(@Param("sql") String sql);

}
