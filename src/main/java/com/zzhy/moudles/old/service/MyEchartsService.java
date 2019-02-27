package com.zzhy.moudles.old.service;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.series.Series;
import com.zzhy.moudles.old.entity.EChartsOptionEntity;
import com.zzhy.moudles.old.entity.EChartsSqlEntity;

import java.util.List;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/9 13:53
 * @Version 1.0
 */
public interface MyEchartsService {
    /**
     * 创建面板
     * @param option
     * @param optionEntity
     * @param legendList
     * @param xAis
     * @param yAis
     * @return
     */
    Option createOption(Option option, EChartsOptionEntity optionEntity, List<Object> legendList, List<Object> xAis, List<Object> yAis);

    /**
     * 创建图形数据
     * @param sqlEntity
     * @param data
     * @return
     */
    Series createSeries(EChartsSqlEntity sqlEntity, List<Object> data);
}
