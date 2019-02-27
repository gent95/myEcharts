package com.zzhy.moudles.now.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;


@TableName("echt_sql_elmt")
public class EchtSqlElmtEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * sql编号
	 */
	private Integer sqlid;
	/**
	 * 面板编号
	 */
	private Integer optid;
	/**
	 * 图表类型
	 */
	private String type;
	/**
	 * 图表顺序
	 */
	private Integer sort;
	/**
	 * $column.comments
	 */
	@TableId
	private Integer id;

	private String elmtId;

	private String field;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getElmtId() {
		return elmtId;
	}

	public void setElmtId(String elmtId) {
		this.elmtId = elmtId;
	}

	/**
	 * 设置：sql编号
	 */
	public void setSqlid(Integer sqlid) {
		this.sqlid = sqlid;
	}
	/**
	 * 获取：sql编号
	 */
	public Integer getSqlid() {
		return sqlid;
	}
	/**
	 * 设置：面板编号
	 */
	public void setOptid(Integer optid) {
		this.optid = optid;
	}
	/**
	 * 获取：面板编号
	 */
	public Integer getOptid() {
		return optid;
	}
	/**
	 * 设置：图表类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：图表类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：图表顺序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：图表顺序
	 */
	public Integer getSort() {
		return sort;
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
