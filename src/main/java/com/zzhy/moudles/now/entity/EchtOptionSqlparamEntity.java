package com.zzhy.moudles.now.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * ${comments}
 * 
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-12-05 14:42:35
 */
@TableName("echt_sqlparam")
public class EchtOptionSqlparamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	private Integer optid;
	/**
	 * $column.comments
	 */
	private String sqlparam;
	/**
	 * $column.comments
	 */
	@TableId
	private Integer id;

	/**
	 * 设置：${column.comments}
	 */
	public void setOptid(Integer optid) {
		this.optid = optid;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getOptid() {
		return optid;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setSqlparam(String sqlparam) {
		this.sqlparam = sqlparam;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getSqlparam() {
		return sqlparam;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getId() {
		return id;
	}
}
