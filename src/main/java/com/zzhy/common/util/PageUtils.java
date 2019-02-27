

package com.zzhy.common.util;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 分页工具类
 * Created by majt on 2018-06-20.
 */
public class PageUtils implements Serializable {
    //每页记录数
    private int rows = Constant.ROWS;
    //当前页数
    private int page = Constant.PAGE;

    public PageUtils get(HashMap<String, Object> params) {
        //分页参数
        if (null != params.get("rows")) {
            rows = Integer.parseInt(params.get("rows").toString());
        }
        if (null != params.get("page")) {
            page = Integer.parseInt((params.get("page").toString()));
        }
        this.page = page * rows;
        return new PageUtils(this.rows, this.page);
    }

    public PageUtils(int rows, int page) {
        this.rows = rows;
        this.page = page;
    }

    public int getRows() {
        return this.rows;
    }

    public int getPage() {
        return this.page;
    }

    public PageUtils() {
    }
}

