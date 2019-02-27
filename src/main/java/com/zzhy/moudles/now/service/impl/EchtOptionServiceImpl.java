package com.zzhy.moudles.now.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.abel533.echarts.Option;
import com.google.gson.Gson;
import com.zzhy.moudles.now.dao.EchtOptionDao;
import com.zzhy.moudles.now.entity.EchtOptionEntity;
import com.zzhy.moudles.now.service.EchtOptionService;
import com.zzhy.util.EchartsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author majt
 */
@Service("optionService1")
@Slf4j
public class EchtOptionServiceImpl extends ServiceImpl<EchtOptionDao, EchtOptionEntity> implements EchtOptionService {

    @Override
    public List<EchtOptionEntity> findTitleList() {
        return selectList(new EntityWrapper<EchtOptionEntity>().setSqlSelect("id", "title"));
    }

    @Override
    public boolean add(String json) {
        EchtOptionEntity echtOptionEntity = genEchtOptionEntity(json);
        if (null != echtOptionEntity) {
            return insert(echtOptionEntity);
        } else {
            return false;
        }
    }

    @Override
    public EchtOptionEntity selectInfo(Integer id) {
        return selectOne(new EntityWrapper<EchtOptionEntity>().eq("id",id));
    }

    @Override
    public Map selectComment() {
        List<HashMap> comments = findComment();
        Map commentMaps = new HashMap();
        for (HashMap m:comments) {
            commentMaps.put(m.get("col"),m.get("com"));
        }
        return commentMaps;
    }

    @Override
    public List<HashMap> findComment(){
        return baseMapper.selectComment();
    }

    private EchtOptionEntity genEchtOptionEntity(String json) {
        String[] noContains = new String[]{"serialVersionUID","hrefId"};
        Class<?> clz, clz1;
        EchtOptionEntity echtOptionEntity = null;
        try {
            //得到pojo类的类对象
            clz1 = Class.forName("com.zzhy.moudles.now.entity.EchtOptionEntity");
            //根据类实例化一个pojo对象
            echtOptionEntity = (EchtOptionEntity) clz1.newInstance();
            //得到Option类的类对象
            clz = Class.forName("com.github.abel533.echarts.Option");
            //根据json字符串实例化一个option对象
            Option option = EchartsUtil.genOption(json);
            //获取pojo类的所有字段
            Field[] fields = clz.getDeclaredFields();
            //循环将option对象的属性值赋值给pojo对象对应的属性
            for (Field field : fields) {
                String fieldName = field.getName();
                //判断当前字段是否在option对象中存在
                if (!Arrays.asList(noContains).contains(fieldName)) {
                    Method method = clz.getMethod(fieldName);
                    Gson gson = new Gson();
                    //判断当前字段对应的取值方法在对象中是否存在
                    if (null != method.invoke(option)) {
                        //通过get方法获取到的属性值
                        String value = gson.toJson(method.invoke(option));
                        //获取option对象所有属性
                        Field[] fields1 = clz1.getDeclaredFields();
                        for (Field field1 : fields1) {
                            //判断pojo属性是否与option对象属性相等
                            if (StringUtils.equals(fieldName, field1.getName())) {
                                log.debug(fieldName+"----"+field1.getName());
                                //得到当前属性对应的set访问器
                                Method method1 = clz1.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1),String.class);
                               log.info(field1.getName()+"<===>"+method1.getName());
                               //赋值
                                method1.invoke(echtOptionEntity, value);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return echtOptionEntity;
    }
}
