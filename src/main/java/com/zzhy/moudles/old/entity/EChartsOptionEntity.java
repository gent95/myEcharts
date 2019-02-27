package com.zzhy.moudles.old.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/10 11:11
 * @Version 1.0
 */
@Getter
@Setter
@TableName("ECHARTS_OPTION")
public class EChartsOptionEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String remark;
    private String status;
    private String create_time;
    private String option_json;
    private String option_script;
    private int y_type;

    @TableField(exist = false)
    private String sqlParam;
}
