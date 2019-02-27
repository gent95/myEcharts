package com.zzhy.moudles.old.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zzhy.moudles.old.dao.SysLogDao;
import com.zzhy.moudles.old.entity.SysLogEntity;
import com.zzhy.moudles.old.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * Created by majt on 2018-07-03.
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao,SysLogEntity> implements SysLogService {
}
