package com.zzhy.moudles.old.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zzhy.common.util.DateUtil;
import com.zzhy.common.util.EhCacheUtils;
import com.zzhy.common.util.MyStringUtil;
import com.zzhy.common.util.R;
import com.zzhy.moudles.old.entity.EChartsSqlEntity;
import com.zzhy.moudles.old.service.EchartsSqlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:50
 * @Version 1.0
 */
@RestController
@RequestMapping("sql")
@Api(description = "sql接口")
public class EchartsSqlController {

    @Autowired
    private EchartsSqlService sqlService;

    @PostMapping("list")
    @ApiOperation("列表")
    public R list(EChartsSqlEntity sqlEntity) {
        return R.ok().put("rows", sqlService.selectList(new EntityWrapper<EChartsSqlEntity>().eq("opt_id", sqlEntity.getOpt_id())));
    }

    @PostMapping("info")
    @ApiOperation("详情")
    public R info(EChartsSqlEntity sqlEntity) {
        sqlEntity = sqlService.selectById(sqlEntity.getId());
        sqlEntity.setPie_select(StringUtils.contains(sqlEntity.getRadius(), ",") ? "empty" : "full");
        if (sqlEntity.getType().equals("pie")) {
            if (StringUtils.isNotBlank(sqlEntity.getRadius()) && !StringUtils.contains(sqlEntity.getRadius(), ",")) {
                sqlEntity.setAll_radius(Integer.parseInt(sqlEntity.getRadius()));
            } else {
                sqlEntity.setIn_radius(Integer.parseInt(sqlEntity.getRadius().split(",")[0]));
                sqlEntity.setOut_radius(Integer.parseInt(sqlEntity.getRadius().split(",")[1]));
            }
        }
        return R.ok().put("data", sqlEntity);
    }

    @PostMapping("update")
    @ApiOperation("更新")
    public R update(EChartsSqlEntity sqlEntity) {
        String sqlCache = MyStringUtil.createKey(sqlEntity.getSql());
        EhCacheUtils.remove(sqlCache);
        return R.ok().put("data", sqlService.updateById(sqlEntity));
    }

    @PostMapping("insert")
    @ApiOperation("添加")
    public R insert(EChartsSqlEntity sqlEntity) {
        if (StringUtils.isBlank(sqlEntity.getSql()) || "null".equals(sqlEntity.getSql())) {
            return R.error("sql不能为空");
        }
        if (null != sqlEntity.getId()) {
            return R.ok().put("data", sqlService.updateById(sqlEntity));
        } else {
            sqlEntity.setCreate_time(DateUtil.format(new Date(), DateUtil.DATE_TIME_PATTERN));
            return R.ok().put("data", sqlService.insert(sqlEntity));
        }
    }


    @PostMapping("delete")
    @ApiOperation("删除")
    public R delete(EChartsSqlEntity sqlEntity) {
        String sqlCache = MyStringUtil.createKey(sqlEntity.getSql());
        EhCacheUtils.remove(sqlCache);
        return R.ok().put("data", sqlService.deleteById(sqlEntity.getId()));
    }

    @PostMapping("execute")
    @ApiOperation("执行sql")
    public R executeSql(String sql) {
        return R.ok().put("data", JSONObject.toJSON(sqlService.executeSQL(sql)).toString());
    }
}
