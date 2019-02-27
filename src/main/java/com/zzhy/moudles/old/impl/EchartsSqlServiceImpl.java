package com.zzhy.moudles.old.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zzhy.common.exception.RRException;
import com.zzhy.common.util.EhCacheUtils;
import com.zzhy.common.util.MyStringUtil;
import com.zzhy.moudles.old.dao.EchartsSqlDao;
import com.zzhy.moudles.old.entity.EChartsSqlEntity;
import com.zzhy.moudles.old.service.EchartsSqlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:48
 * @Version 1.0
 */
@Service("sqlService")
@Slf4j
public class EchartsSqlServiceImpl extends ServiceImpl<EchartsSqlDao, EChartsSqlEntity> implements EchartsSqlService {
    @Override
    public List<Object> executeSQL(String sql) {
        if (StringUtils.isBlank(sql)) {
            throw new RRException("sql不能为空");
        }

        List<Map<String, Object>> list;
        String sqlCache = MyStringUtil.createKey(sql);
        if (null != EhCacheUtils.get(sqlCache)) {
            list = (List<Map<String, Object>>) EhCacheUtils.get(sqlCache);
            log.info("获取缓存中的图表数据==>{key:" + sqlCache + ",value:" + list);
        } else {
            try {
                list = baseMapper.executeSql(sql);
                log.debug("查询结果:"+JSON.toJSONString(list));
            } catch (Exception e) {
                return null;
//                throw new RRException("SQL语法有误!");
            }
            EhCacheUtils.put(sqlCache, list);
            log.info("将图表数据维护到缓存==>{key:" + sqlCache + ",value:" + list);
        }

        List<Object> data = new ArrayList<>();
        if (null != list && list.size() > 0) {
            try {
                for (Map<String, Object> map : list) {
                    if (null != map.get("VALUE") || null != map.get("value")) {
                        data.add(map.get("VALUE")==null?map.get("value"):map.get("VALUE"));
                    } else {
                        throw new RRException("SQL中为别名为VALUE的列");
                    }
                }
            } catch (ClassCastException e) {
                return executeSQL_1(sql);
            }
        }
        return data;
    }

    @Override
    public List<Object> executeSQL_1(String sql) {
        List<Object> list;
        String sqlCache = MyStringUtil.createKey(sql);
        if (EhCacheUtils.get(sqlCache) != null) {
            list = (List<Object>) EhCacheUtils.get(sqlCache);
            log.info("获取缓存中的{json对象}数据\n{key:" + sqlCache + ",value:" + list);
        } else {
            list = baseMapper.executeSql_1(sql);
            EhCacheUtils.put(sqlCache, list);
            log.info("将{json对象}数据维护到缓存\n{key:" + sqlCache + ",value:" + list);
        }
        return list;
    }
}
