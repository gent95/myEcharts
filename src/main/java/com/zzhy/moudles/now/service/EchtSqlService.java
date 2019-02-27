package com.zzhy.moudles.now.service;

import com.baomidou.mybatisplus.service.IService;
import com.zzhy.common.util.MapUtils;
import com.zzhy.moudles.now.entity.EchtSqlEntity;

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
public interface EchtSqlService extends IService<EchtSqlEntity> {
    /**
     * 面板编号
     * @param optId
     * @return
     */
    List<EchtSqlEntity> selectByOptId(Integer optId);

    /**
     * 查询sqlelmt聚合类
     * @return
     */
    List<HashMap<String,Object>> selectSqlElmtVo(HashMap<String,Object> param);
    List<HashMap<String,Object>> selectSqlElmtVo1(HashMap<String,Object> param);
    /**
     * 查询数据数组
     * @param sql
     * @return
     */
    List<String> excu_sql(String sql);

    /**
     * 执行sql,并得到列对应的数据集合
     * @param sql
     * @return
     */
    LinkedHashMap<String,List> exec(String sql);

    /**
     * 获取查询的sql的列名
     * @return
     */
    List<MapUtils> getFields(Integer id);

    /**
     * 仅执行sql
     * @param sql
     * @return
     */
    List<LinkedHashMap<String,Object>> goSQL(String sql);
}

