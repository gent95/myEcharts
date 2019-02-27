package com.zzhy.moudles.old.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Series;
import com.google.gson.JsonSyntaxException;
import com.zzhy.moudles.old.dao.EchartsOptionDao;
import com.zzhy.moudles.old.entity.EChartsOptionEntity;
import com.zzhy.moudles.old.entity.EChartsSqlEntity;
import com.zzhy.moudles.old.service.EchartsOptionService;
import com.zzhy.moudles.old.service.EchartsSqlService;
import com.zzhy.moudles.old.service.MyEchartsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:46
 * @Version 1.0
 */
@Service("optionService")
@Slf4j
public class EchartsOptionServiceImpl extends ServiceImpl<EchartsOptionDao, EChartsOptionEntity> implements EchartsOptionService {
    @Autowired
    private EchartsSqlService sqlService;
    @Autowired
    private MyEchartsService echartsService;

    @Override
    public Object getOptionJson(EChartsOptionEntity optionEntity) {
        log.info("开始构造optionJson");
        GsonOption option = null;
        if (StringUtils.isNotBlank(optionEntity.getOption_json())) {
            log.info("将json字符串反序列化成Java对象");
            try {
                option = GsonUtil.fromJSON(optionEntity.getOption_json(), GsonOption.class);
            } catch (JsonSyntaxException e) {
                return optionEntity.getOption_json();
            }
        }

        List<Series> seriesList = new ArrayList<>();
        //获取面板上的所有数据对象
        List<EChartsSqlEntity> sqlEntities = sqlService.selectList(
                new EntityWrapper<EChartsSqlEntity>()
                        .eq("opt_id", optionEntity.getId())
                        .eq("status", "1")
                        .orderBy("sort", true));
        List<Object> legendList = new ArrayList<>();
        List<Object> xAisList = null;
        List<Object> yAisList = null;

        //迭代数据对象,对数据对象进行二次实例化,设置模板参数和动态数据
        for (EChartsSqlEntity sqlEntity : sqlEntities) {
            Series series = null;
            String sql;
            //判断是否传入动态参数
            log.info("动态参数字符串:" + optionEntity.getSqlParam());
            //将sql中的占位符替换为提取图表时传入的真实值
            sql = makeSql_1(sqlEntity, optionEntity.getSqlParam());

            //从数据库得到sql数据,并封装成series
            if (sqlEntity.getType().equals("pie")) {
                series = echartsService.createSeries(sqlEntity, sqlService.executeSQL_1(sql));
            } else {
                series = echartsService.createSeries(sqlEntity, sqlService.executeSQL(sql));
            }
            log.info("图形类型为:" + sqlEntity.getType());

            //判断图形类型,并设置数据

            if (sqlEntity.getType().equals("xAis")) {
                xAisList = sqlService.executeSQL(sqlEntity.getSql());
            } else if (sqlEntity.getType().equals("yAis")) {
                yAisList = sqlService.executeSQL(sqlEntity.getSql());
            } else if (optionEntity.getY_type() == 0) {
                yAisList = new ArrayList<>();
                yAisList.add("系统默认Y轴");
            }

            seriesList.add(series);
            legendList.add(sqlEntity.getName());
        }
        option = (GsonOption) echartsService.createOption(option, optionEntity, legendList, xAisList, yAisList);
        option.setSeries(seriesList);
        return JSONObject.parse(option.toPrettyString());
    }

    @Override
    public EChartsOptionEntity reset(EChartsOptionEntity optionEntity) {
        optionEntity = selectById(optionEntity);
        optionEntity.setOption_json(null);
        optionEntity.setOption_json(getOptionJson(optionEntity).toString());
        updateById(optionEntity);
        return optionEntity;
    }

    /**
     * sql动态参数替换(已废弃)
     *
     * @param sqlEntity
     * @param sqlParam
     * @return
     */
    @Deprecated
    private String makeSql(EChartsSqlEntity sqlEntity, String sqlParam) {
        StringBuffer sql = new StringBuffer("select * from (")
                .append(sqlEntity.getSql())
                .append(" )")
                .append(" where 1=1");
        sqlParam = StringUtils.replace(sqlParam, "$", " ");
        if (StringUtils.isNotBlank(sqlEntity.getSql_param())) {
            String[] sql_params = sqlEntity.getSql_param().split(",");
            String[] sqlParams = sqlParam.split("#");
            for (String param : sql_params) {
                for (String p : sqlParams) {
                    if (StringUtils.contains(p, param)) {
                        if (StringUtils.contains(p, param + ":")) {
                            sql.append(" and ").append(StringUtils.replace(p, ":", "='")).append("'");
                        } else if (StringUtils.contains(p, "between")) {
                            sql.append(" and ");
                            p = StringUtils.replace(p, "between", "between to_date('");
                            p = StringUtils.replace(p, "and", "','YYYY-MM-DD') and to_date('");
                            sql.append(p + "','YYYY-MM-DD')");

                        } else {
                        }
                    }
                }
            }
        }
        return sql.toString();
    }

    private String makeSql_1(EChartsSqlEntity sqlEntity, String sqlParam_str) {
        if (StringUtils.isBlank(sqlParam_str)) {
            if (StringUtils.isNotBlank(sqlEntity.getSql_param())) {
                sqlParam_str = sqlEntity.getSql_param();
            } else {
                return sqlEntity.getSql();
            }
        }
        String[] sqlParams = sqlParam_str.split("&");
        HashMap<String, String> paramMap = new HashMap<>();
        String sql = null;
        if (null != sqlEntity && null != sqlEntity.getSql()) {
            sql = sqlEntity.getSql();
        }

        if (StringUtils.isBlank(sqlParam_str) || !StringUtils.contains(sql, "@")) {
            return sql;
        }
        for (String sqlParam : sqlParams) {
            String[] param = sqlParam.split("=");
            paramMap.put(param[0], param[1]);
        }
        log.info("动态参数:", paramMap);
        for (String key : paramMap.keySet()) {
            sql = StringUtils.replace(sql, "@" + key, "\'" + paramMap.get(key) + "\'");
        }
        log.info("替换参数值得到真正执行的SQL:", sql);
        return sql;
    }

//    /**
//     * 测试用例
//     */
//    public static void main(String[] args) {
//        EChartsSqlEntity sqlEntity = new EChartsSqlEntity();
//        sqlEntity.setSql("select * from warning_info");
//        sqlEntity.setSql_param("a,b");
//        String sqlParam = "chadoctor:胡浪燕#birthday$between$1977-2-28$1977-12-31";
//        System.out.println(makeSql(sqlEntity,sqlParam));
//    }

//    public static void main(String[] args) {
//        EChartsSqlEntity sqlEntity = new EChartsSqlEntity();
//        sqlEntity.setSql("select * from warning_info where id = @id and a=@a and b=@b");
//        String sqlParam_str = "id=121&a=1&b=2&c=3";
//        EchartsOptionServiceImpl old = new EchartsOptionServiceImpl();
//        System.out.println(old.makeSql_1(sqlEntity,sqlParam_str));
//    }
}
