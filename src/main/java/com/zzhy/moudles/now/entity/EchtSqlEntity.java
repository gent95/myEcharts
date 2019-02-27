package com.zzhy.moudles.now.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * sql对象
 * 
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-11-27 15:44:40
 */
@TableName(value="echt_sql")
public class EchtSqlEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Integer id;
	/**
	 * $column.comments
	 */
	private String describe;
	/**
	 * $column.comments
	 */
	private String code;
	/**
	 * $column.comments
	 */
	private Integer isDelete;
	/**
	 * $column.comments
	 */
	private String param;

	private Integer optId;

	public Integer getOptId() {
		return optId;
	}

	public void setOptId(Integer optId) {
		this.optId = optId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
