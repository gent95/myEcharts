package com.zzhy.moudles.now.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zzhy.common.util.EhCacheUtils;
import com.zzhy.common.util.MyStringUtil;
import com.zzhy.common.util.R;
import com.zzhy.moudles.now.entity.EchtSqlEntity;
import com.zzhy.moudles.now.service.EchtSqlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


/**
 * ${comments}
 *
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-11-27 15:44:40
 */
@RestController
@RequestMapping("now/echtsql")
@Api(value = "NowSql")
public class EchtSqlController {
    @Autowired
    private EchtSqlService sqlService1;

    @ApiOperation("根据optid查询")
    @PostMapping("list")
    public R list(Integer optId){
        return R.ok().put("rows",sqlService1.selectByOptId(optId));
    }

    @ApiOperation("根据optid查询")
    @PostMapping("list1")
    public R list1(Integer optId){
        return R.ok().put("rows",sqlService1.selectList(new EntityWrapper<EchtSqlEntity>().eq("ISDELETE",0).eq("optId",optId)));
    }

    @ApiOperation("添加")
    @PostMapping("insert")
    public R insert(EchtSqlEntity echtSqlEntity){
        EhCacheUtils.remove(MyStringUtil.createKey(echtSqlEntity.getCode()));
        if ( null == echtSqlEntity.getId() || 0 ==echtSqlEntity.getId()){
            if (sqlService1.insert(echtSqlEntity)){
                return R.ok("添加成功");
            }
        }else {
            if (sqlService1.updateById(echtSqlEntity)){
                return R.ok("修改成功");
            }
        }
        return R.error("操作失败");
    }

    @ApiOperation("修改")
    @PostMapping("update")
    public R update(EchtSqlEntity echtSqlEntity){
        if (sqlService1.updateById(echtSqlEntity)){
            return R.ok("保存成功");
        }else {
            return R.error("保存失败");
        }
    }

    @ApiOperation("获取data数据")
    @PostMapping("getData")
    public R getData(@RequestParam HashMap<String,Object> param){
        return R.ok().put("data",sqlService1.selectSqlElmtVo(param));
    }

    @ApiOperation("获取data1数据")
    @PostMapping("getData1")
    public R getData1(@RequestParam HashMap<String,Object> param){
        return R.ok().put("data",sqlService1.selectSqlElmtVo1(param));
    }

    @ApiOperation("执行sql")
    @PostMapping("sql")
    public R get(String sql){
        return R.ok().put("data",sqlService1.exec(sql));
    }

    @ApiOperation("获取sql的查询字段")
    @PostMapping("getFields")
    public R getFields(Integer id){
        return R.ok().put("data",sqlService1.getFields(id));
    }

    @ApiOperation("预览sql执行结果")
    @PostMapping("goSQL")
    public R goSQL(String sql){
        return R.ok().put("data",sqlService1.goSQL(sql));
    }
}
