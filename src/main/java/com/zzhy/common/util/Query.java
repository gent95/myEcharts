/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and rowsations under
 * the License.
 */

package com.zzhy.common.util;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.0.0 2017-03-14
 */
public class Query<T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page1;
    /**
     * 当前页码
     */
    private int page = 1;
    /**
     * 每页条数
     */
    private int rows = 2;

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        if(params.get("page") != null){
            page = Integer.parseInt((params.get("page").toString()));
        }
        if(params.get("rows") != null){
            rows = Integer.parseInt(params.get("rows").toString());
        }

        this.put("offset", (page - 1) * rows);
        this.put("page", page);
        this.put("rows", rows);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String)params.get("sidx");
        String order = (String)params.get("order");
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页
        this.page1 = new Page<>(page, rows);

        //排序
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
            this.page1.setOrderByField(sidx);
            this.page1.setAsc("ASC".equalsIgnoreCase(order));
        }

    }

    public Page<T> getPage1() {
        return page1;
    }

    public int getPage() {
        return page;
    }

    public int getrows() {
        return rows;
    }
}
