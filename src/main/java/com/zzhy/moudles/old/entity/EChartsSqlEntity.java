package com.zzhy.moudles.old.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.github.abel533.echarts.code.SeriesType;
import lombok.Getter;
import lombok.Setter;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:37
 * @Version 1.0
 */
@Getter
@Setter
@TableName("ECHARTS_SQL")
public class EChartsSqlEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int opt_id;
    private String name;
    private String type;
    private String sql;
    private String status;
    private String create_time;
    private int sort;
    private String radius;
    private String sql_param;


    @TableField(exist = false)
    private SeriesType seriesType;
    @TableField(exist = false)
    private String pie_select;
    @TableField(exist = false)
    private int all_radius;
    @TableField(exist = false)
    private int in_radius;
    @TableField(exist = false)
    private int out_radius;
}
