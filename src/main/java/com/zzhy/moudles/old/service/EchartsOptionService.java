package com.zzhy.moudles.old.service;

import com.baomidou.mybatisplus.service.IService;
import com.zzhy.moudles.old.entity.EChartsOptionEntity;

import javax.validation.constraints.Max;
import java.util.HashMap;
import java.util.List;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:45
 * @Version 1.0
 */
public interface EchartsOptionService extends IService<EChartsOptionEntity> {
    /**
     * 获取面板json
     * @param optionEntity
     * @return 返回echarts option 对象json字符串
     */
    Object getOptionJson(EChartsOptionEntity optionEntity);

    /**
     * 重置option
     * @param optionEntity
     * @return
     */
    EChartsOptionEntity reset(EChartsOptionEntity optionEntity);

}
