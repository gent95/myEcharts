package com.zzhy.moudles.now.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zzhy.common.util.MapUtils;
import com.zzhy.moudles.now.dao.EchtSqlElmtDao;
import com.zzhy.moudles.now.entity.EchtSqlElmtEntity;
import com.zzhy.moudles.now.service.EchtSqlElmtService;
import org.springframework.stereotype.Service;


@Service("sqlElmtService")
public class EchtSqlElmtServiceImpl extends ServiceImpl<EchtSqlElmtDao, EchtSqlElmtEntity> implements EchtSqlElmtService {
    @Override
    public EchtSqlElmtEntity selectByElmtId(EchtSqlElmtEntity echtSqlElmtEntity) {
        return selectByMap(new MapUtils().put("elmtId",echtSqlElmtEntity.getElmtId())).get(0);
    }
}
