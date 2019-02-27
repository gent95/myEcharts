package com.zzhy.moudles.old.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.zzhy.common.util.DateUtil;
import com.zzhy.common.util.R;
import com.zzhy.moudles.old.entity.EChartsOptionEntity;
import com.zzhy.moudles.old.service.EchartsOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:49
 * @Version 1.0
 */
@RestController
@RequestMapping("option")
@Api(description = "option接口")
public class EchartsOptionController {
    @Autowired
    private EchartsOptionService optionService;

    @PostMapping("list")
    @ApiOperation("列表查询")
    public R list() {
        return R.ok().put("rows", optionService.selectList(null));
    }

    @PostMapping("optionJson")
    @ApiOperation("获取json")
    public R optionJson(EChartsOptionEntity optionEntity) {
        String sqlParam = optionEntity.getSqlParam();
        optionEntity = optionService.selectOne(new EntityWrapper<EChartsOptionEntity>().eq("id", optionEntity.getId()).and().eq("status", 1));
        optionEntity.setSqlParam(sqlParam);
        return R.ok().put("data", optionService.getOptionJson(optionEntity)).put("id", optionEntity.getId());
    }

    @GetMapping("optionScript")
    @ApiOperation("获取脚本")
    public R optionScript(EChartsOptionEntity optionEntity) {
        optionEntity = optionService.selectOne(new EntityWrapper<EChartsOptionEntity>().eq("id", optionEntity.getId()).and().eq("status", 1));
        return R.ok().put("data", optionEntity.getOption_script());
    }

    @PostMapping("info")
    @ApiOperation("详情")
    public R info(EChartsOptionEntity optionEntity) {
        optionEntity.setOption_json(optionService.getOptionJson(optionEntity).toString());
        return R.ok().put("data", optionEntity);
    }

    @PostMapping("insert")
    @ApiOperation("添加")
    public R insert(EChartsOptionEntity optionEntity) {
        if (null != optionEntity.getId()) {
            GsonUtil.fromJSON(optionEntity.getOption_json(), GsonOption.class);
            return R.ok().put("data", optionService.updateById(optionEntity));
        } else {
            optionEntity.setId(0);
            optionEntity.setCreate_time(DateUtil.format(new Date(), DateUtil.DATE_TIME_PATTERN));
            optionEntity.setOption_json(optionService.getOptionJson(optionEntity).toString());
            return R.ok().put("data", optionService.insert(optionEntity));
        }
    }

    @PostMapping("update")
    @ApiOperation("更新")
    public R update(EChartsOptionEntity optionEntity) {
        return R.ok().put("data", optionService.updateById(optionEntity));
    }

    @PostMapping("delete")
    @ApiOperation("删除")
    public R delete(EChartsOptionEntity optionEntity) {
        return R.ok().put("data", optionService.deleteById(optionEntity.getId()));
    }

    @PostMapping("reset")
    @ApiOperation("一键重置")
    public R reset(EChartsOptionEntity optionEntity) {
        return R.ok().put("data", optionService.reset(optionEntity));
    }
}
