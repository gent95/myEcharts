package com.zzhy.moudles.now.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zzhy.common.util.MapUtils;
import com.zzhy.moudles.now.entity.EchtOptionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface EchtOptionDao extends BaseMapper<EchtOptionEntity> {
    List<HashMap> selectComment();
}
