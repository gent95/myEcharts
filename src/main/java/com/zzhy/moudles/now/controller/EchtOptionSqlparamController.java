package com.zzhy.moudles.now.controller;

import com.zzhy.common.util.MapUtils;
import com.zzhy.common.util.R;
import com.zzhy.moudles.now.entity.EchtOptionSqlparamEntity;
import com.zzhy.moudles.now.service.EchtOptionSqlparamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


/**
 * ${comments}
 *
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-12-05 14:42:35
 */
@RestController
@RequestMapping("now/echtoptionsqlparam")
@Api(value = "option与sql参数关系接口")
public class EchtOptionSqlparamController {
    @Autowired
    private EchtOptionSqlparamService sqlparamService;

    @ApiOperation("保存")
    @PostMapping("save")
    public R save(EchtOptionSqlparamEntity optionSqlparamEntity){
        if (null == optionSqlparamEntity.getId() || 0 ==optionSqlparamEntity.getId()){
            sqlparamService.insert(optionSqlparamEntity);
        }else {
            sqlparamService.updateById(optionSqlparamEntity);
        }
        return R.ok();
    }
    @ApiOperation("详情")
    @PostMapping("info")
    public R info(EchtOptionSqlparamEntity optionSqlparamEntity){
        List<EchtOptionSqlparamEntity> list= sqlparamService.selectByMap(new MapUtils().put("optid",optionSqlparamEntity.getOptid()));
        if (null != list && list.size() >0){
            return R.ok().put("data",list.get(0));
        }else {
            return R.error();
        }

    }
}
