package com.zzhy.moudles.now.service;

import com.baomidou.mybatisplus.service.IService;
import com.zzhy.moudles.now.entity.EchtSqlElmtEntity;

/**
 * ${comments}
 *
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-11-30 10:13:56
 */

public interface EchtSqlElmtService extends IService<EchtSqlElmtEntity> {

    /**
     * 根据元素编号查询
     * @param echtSqlElmtEntity
     * @return
     */
    EchtSqlElmtEntity selectByElmtId(EchtSqlElmtEntity echtSqlElmtEntity);
}

