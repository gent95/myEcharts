package com.zzhy.moudles.now.controller;

import com.zzhy.common.util.R;
import com.zzhy.moudles.now.entity.EchtSqlElmtEntity;
import com.zzhy.moudles.now.service.EchtSqlElmtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;



/**
 * ${comments}
 *
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-11-30 10:13:56
 */
@RestController
@RequestMapping("now/echtsqlelmt")
public class EchtSqlElmtController {
    @Autowired
    private EchtSqlElmtService sqlElmtService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @PostMapping("/list")
    public R list(){
        return R.ok();
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @PostMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        EchtSqlElmtEntity echtSqlElmt = sqlElmtService.selectById(id);
        return R.ok().put("echtSqlElmt", echtSqlElmt);
    }

    /**
     * 保存
     */
    @ApiOperation("添加")
    @PostMapping("/save")
    public R save(EchtSqlElmtEntity echtSqlElmt){
        sqlElmtService.insert(echtSqlElmt);
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    public R update(EchtSqlElmtEntity echtSqlElmt){
        sqlElmtService.updateAllColumnById(echtSqlElmt);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    public R delete(Integer[] ids){
        sqlElmtService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @ApiOperation("根据elmtId查询")
    @PostMapping("/info/elmtid")
    public R infoByElmtId(EchtSqlElmtEntity echtSqlElmtEntity){
        return R.ok().put("info",sqlElmtService.selectByElmtId(echtSqlElmtEntity));
    }

}
