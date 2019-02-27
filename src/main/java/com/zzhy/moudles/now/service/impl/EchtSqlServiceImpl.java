package com.zzhy.moudles.now.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zzhy.common.exception.RRException;
import com.zzhy.common.util.EhCacheUtils;
import com.zzhy.common.util.MapUtils;
import com.zzhy.common.util.MyStringUtil;
import com.zzhy.moudles.now.dao.EchtSqlDao;
import com.zzhy.moudles.now.entity.EchtOptionSqlparamEntity;
import com.zzhy.moudles.now.entity.EchtSqlEntity;
import com.zzhy.moudles.now.service.EchtOptionSqlparamService;
import com.zzhy.moudles.now.service.EchtSqlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service("sqlService1")
@Slf4j
public class EchtSqlServiceImpl extends ServiceImpl<EchtSqlDao, EchtSqlEntity> implements EchtSqlService {
    public final Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
    @Autowired
    private EchtOptionSqlparamService sqlparamService;

    @Override
    public List<EchtSqlEntity> selectByOptId(Integer optId) {
        return baseMapper.selectByOptId(optId);
    }

    @Override
    public List<HashMap<String, Object>> selectSqlElmtVo(HashMap<String, Object> param) {
        List<HashMap<String, Object>> list = baseMapper.selectSqlElmtVo(Integer.parseInt(param.get("optId").toString()));
        for (HashMap<String, Object> map : list) {
            String code;
            if (null == map.get("code")) {
                continue;
            } else {
                code = map.get("code").toString();
                if (null != map.get("param")) {
                    param.put("sqlParam", map.get("param"));
                    code = genSql(code, param);
                }
            }

            if (StringUtils.contains(code, "$name") && StringUtils.contains(code, "$value")) {
                map.put("data", replace$(baseMapper.excu_sql(code)));
            } else if (StringUtils.contains(code, "$value")) {
                map.put("data", excu_sql(code));
            } else {
                throw new RRException("SQL格式有误");
            }
        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectSqlElmtVo1(HashMap<String, Object> param) {
        //查询面板对应的sql信息
        List<HashMap<String, Object>> list = baseMapper.selectSqlElmtVo(Integer.parseInt(param.get("optId").toString()));
        //获取面板对应的参数
        EchtOptionSqlparamEntity sqlparamEntity = sqlparamService.selectOne(new EntityWrapper<EchtOptionSqlparamEntity>().eq("optid", param.get("optId")));
        for (HashMap<String, Object> map : list) {
            String code;
            //如果sql为空则跳过此条记录
            if (null == map.get("code")) {
                continue;
            } else {
                code = map.get("code").toString();
                //如果参数列表不为空则去将参数与sql占位符去匹配
                if (null != sqlparamEntity && null != sqlparamEntity.getSqlparam()) {
                    //将预定义的参数列表添加到map中
                    param.put("sqlParam", sqlparamEntity.getSqlparam());
                    //构造sql
                    code = genSql(code, param);
                    //将sql数据添加到对应的option对象中
                    map.put("data", exec(code));
                }
            }
        }
        return list;
    }

    @Override
    public List<String> excu_sql(String sql) {
        List<String> list = new ArrayList<>();
        try {
            for (LinkedHashMap<String, Object> map : replace$(baseMapper.excu_sql(sql))) {
                for (String key : map.keySet()) {
                    list.add(map.get(key).toString());
                }
            }
        } catch (Exception e) {
            throw new RRException("SQL语法异常");
        }
        return list;
    }

    @Override
    public LinkedHashMap<String, List> exec(String sql) {
        LinkedHashMap<String, List> result = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> data;

        //将数据维护到缓存
        if (null == EhCacheUtils.get(MyStringUtil.createKey(sql))){
            data = baseMapper.excu_sql(sql);
        }else {
            data = (List<LinkedHashMap<String, Object>>) EhCacheUtils.get(MyStringUtil.createKey(sql));
        }

        //将sql查询出的字段添加到set集合中
        Set<String> fields = new HashSet<>();
        for (LinkedHashMap<String, Object> d : data) {
            fields = d.keySet();
        }


        for (int i = 0; i < fields.size(); i++) {
            String field = fields.toArray()[i].toString();
            List one = null;
            if (i == 0) { //巡环开始时初始化一个用于存放字段数据的集合
                one = new ArrayList();
            } else {
                //判断当前字段是老字段还是新字段
                if (!field.equals(fields.toArray()[i - 1].toString())) {
                    one = new ArrayList();
                }
            }
            //填充数据到list集合中
            for (LinkedHashMap<String, Object> map : data) {
                if (one != null){
                    one.add(map.get(field));
                }
            }
            result.put(field.toLowerCase(), one);
        }
        return result;
    }

    @Override
    public List<MapUtils> getFields(Integer id) {
        String sql = selectByMap(new MapUtils().put("id", id)).get(0).getCode();
        List<MapUtils> result = new ArrayList<>();
        String fieldStr = sql.substring(sql.indexOf("select") + 6, sql.indexOf("from"));
        Matcher matcher = pattern.matcher(fieldStr);
        while(matcher.find()){
            fieldStr = matcher.replaceAll("");
        }

        for (String field : fieldStr.split(",")) {
            String f = field.trim();
            if (StringUtils.contains(f," ")){
                String ff = f.substring(f.lastIndexOf(" "));
                result.add(new MapUtils().put("value", ff).put("text", ff));
            }else {
                result.add(new MapUtils().put("value", f).put("text", f));
            }
        }
        log.debug("sql输出字段:"+result);
        return result;
    }

    @Override
    public List<LinkedHashMap<String, Object>> goSQL(String sql) {
        log.debug("开始执行"+sql);
        return baseMapper.excu_sql(sql);
    }


    /**
     * 构造sql
     *
     * @param sql
     * @param param
     * @return
     */
    private String genSql(String sql, HashMap<String, Object> param) {
        //根据","得到预定义参数
        String[] ps = param.get("sqlParam").toString().split(",");
        for (String p : ps) {
            //判断sql中是否包含预定义参数,如果不包含则忽略
            if (StringUtils.contains(sql,":"+p)){
                //判断是否传入有效参数值,如果未传入则证明在调用时候url参数缺失
                if (null != param.get(p)) {
                    //替换真实值到预定sql中
                    sql = sql.replace(":" + p, param.get(p).toString());
                } else {
                    throw new RRException("传入参数不足,[" + p + "]与预定义参数不符");
                }
            }
        }
        log.info("构造的SQL是:\n" + sql);
        return sql;
    }

    private List<LinkedHashMap<String, Object>> replace$(List<LinkedHashMap<String, Object>> list) {
        List<LinkedHashMap<String, Object>> dataList = new ArrayList<>();
        for (LinkedHashMap<String, Object> map : list) {
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            for (String key : map.keySet()) {
                if (key.substring(0, 1).equals("$")) {
                    String key1 = StringUtils.remove(key, "$");
                    data.put(key1, map.get(key));
                    dataList.add(data);
                }
            }
        }
        return dataList;
    }

}
