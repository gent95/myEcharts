package com.zzhy.moudles.old.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统日志
 * Created by majt on 2018-07-03.
 */
@TableName("sys_log")
@Data
@NoArgsConstructor
public class SysLogEntity {
    @TableId
    private Long id;
    //用户名
    private String username;
    //用户操作
    private String operation;
    //请求方法
    private String method;
    //请求参数
    private String params;
    //执行时长(毫秒)
    private Long time;
    //IP地址
    private String ip;
    //创建时间
    private Date create_date;
}
