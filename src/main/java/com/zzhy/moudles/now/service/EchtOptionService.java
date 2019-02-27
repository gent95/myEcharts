package com.zzhy.moudles.now.service;

import com.baomidou.mybatisplus.service.IService;
import com.zzhy.moudles.now.entity.EchtOptionEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-11-27 15:44:39
 */
public interface EchtOptionService extends IService<EchtOptionEntity> {

    /**
     * 查询图标标题
     * @return
     */
    List<EchtOptionEntity> findTitleList();

    /**
     * 添加
     * @param json
     */
    boolean add(String json);

    /**
     * 查询详情
     * @param id
     * @return
     */
    EchtOptionEntity selectInfo(Integer id);

    /**
     * 查询表字段的字段名和注释
     * @return
     */
    Map selectComment();

    /**
     * 查询表字段的字段名和注释
     * @return
     */
    List<HashMap> findComment();
}

