package com.zzhy.moudles.now.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.abel533.echarts.json.GsonUtil;
import com.zzhy.common.util.R;
import com.zzhy.moudles.now.entity.EchtOptionEntity;
import com.zzhy.moudles.now.service.EchtOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * ${comments}
 *
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-11-27 15:44:39
 */
@RestController
@RequestMapping("now/echtoption")
@Api(value = "NowOption")
public class EchtOptionController {
    @Autowired
    private EchtOptionService optionService1;

    @ApiOperation("列表")
    @PostMapping("list")
    public R list(){
        return R.ok().put("rows",JSONObject.parse(GsonUtil.prettyFormat(optionService1.selectList(new EntityWrapper<EchtOptionEntity>().orderBy("id",false)))));
    }

    @ApiOperation("详情")
    @PostMapping("info")
    public R info(Integer id){
        if (null == id || id == 0){
            return R.error("对象不存在");
        }else {
            return R.ok().put("info", JSONObject.parse(GsonUtil.prettyFormat(optionService1.selectInfo(id))));
        }
    }

    @ApiOperation("详情1")
    @PostMapping("info1")
    public R info1(String hrefId){
        if (StringUtils.isBlank(hrefId)){
            return R.error("对象不存在");
        }else {
            Integer id = optionService1.selectOne(new EntityWrapper<EchtOptionEntity>().eq("hrefId",hrefId)).getId();
           return R.ok().put("id",id);
        }
    }

    @ApiOperation("名称导航列表")
    @PostMapping("titles")
    public R titleList(){
        return R.ok().put("rows",optionService1.findTitleList());
    }

    @ApiOperation("名称导航列表")
    @PostMapping("insert")
    public R insert(String json){
       if (optionService1.add(json)){
           return R.ok();
       }else {
           return R.error("保存失败");
       }
    }

    @ApiOperation("表字段名和注释")
    @PostMapping("comments")
    public R comment(){
        return R.ok().put("rows",optionService1.selectComment());
    }

    @ApiOperation("表字段名和注释1")
    @PostMapping("comments1")
    public R comment1(){
        return R.ok().put("rows",optionService1.findComment());
    }

    @ApiOperation("保存标题")
    @PostMapping("saveObject")
    public R insertObject(EchtOptionEntity echtOptionEntity){
        int tag = 0;
        if (null != echtOptionEntity.getHrefId()){
            tag = optionService1.selectCount(new EntityWrapper<EchtOptionEntity>().eq("hrefId",echtOptionEntity.getHrefId()));
        }
        if (tag != 0 && (null == echtOptionEntity.getId() || echtOptionEntity.getId() == 0)){
            if (optionService1.insert(echtOptionEntity)){
                return R.ok("保存成功");
            }
        }else {
            if (optionService1.updateById(echtOptionEntity)){
                return R.ok("修改成功");
            }
        }
        return R.error("保存失败");
    }

    @ApiOperation("查询指定列")
    @PostMapping("selectColumn")
    public R selectColumn(Integer id,String column){
        if (null == id || id == 0){
            return R.error();
        }else {
            return R.ok().put("rows",optionService1.selectOne(new EntityWrapper<EchtOptionEntity>().setSqlSelect(column).eq("id",id)));
        }
    }

    @ApiOperation("复用")
    @PostMapping("reuse")
    public R resure(Integer id){
        EchtOptionEntity optionEntity = optionService1.selectInfo(id);
        if (optionService1.insert(optionEntity)){
            return R.ok();
        }else {
            return R.error("操作失败");
        }
    }
}
