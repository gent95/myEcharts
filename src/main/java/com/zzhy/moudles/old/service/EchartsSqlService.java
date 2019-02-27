package com.zzhy.moudles.old.service;

import com.baomidou.mybatisplus.service.IService;
import com.zzhy.moudles.old.entity.EChartsSqlEntity;

import java.util.List;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:45
 * @Version 1.0
 */
public interface EchartsSqlService extends IService<EChartsSqlEntity> {
    /**
     * 执行指定sql
     * @param sql
     * @return
     */
    List<Object> executeSQL(String sql);

    /**
     * 饼图专属方法sql
     * @param sql
     * @return
     */
    List<Object> executeSQL_1(String sql);
}
