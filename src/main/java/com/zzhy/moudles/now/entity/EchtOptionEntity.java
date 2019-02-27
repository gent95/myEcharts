package com.zzhy.moudles.now.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 面板对象
 * 
 * @author majt
 * @email gentmjt@gmail.com
 * @date 2018-11-27 15:44:39
 */
@TableName("echt_option")
public class EchtOptionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	@TableId
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图例
	 */
	private String legend;
	/**
	 * 绘图网格
	 */
	private String grid;
	/**
	 * x轴
	 */
	private String xAxis;
	/**
	 * y轴
	 */
	private String yAxis;
	/**
	 * 极坐标系
	 */
	private String polar;
	/**
	 * 径向轴
	 */
	private String radiusAxis;
	/**
	 * 角度轴
	 */
	private String angleAxis;
	/**
	 * 雷达图坐标系
	 */
	private String radar;
	/**
	 * 缩放组件
	 */
	private String dataZoom;
	/**
	 * 视觉映射组件
	 */
	private String visualMap;
	/**
	 * 提示框组件
	 */
	private String tooltip;
	/**
	 * 坐标轴指示器
	 */
	private String axisPointer;
	/**
	 * 工具栏
	 */
	private String toolbox;
	/**
	 * 区域选择组件
	 */
	private String brush;
	/**
	 * 地理坐标系组件
	 */
	private String geo;
	/**
	 * 平行坐标系
	 */
	private String parallel;
	/**
	 * 平行坐标系坐标轴
	 */
	private String parallelAxis;
	/**
	 * 单轴
	 */
	private String singleAxis;
	/**
	 * 时间线
	 */
	private String timeLine;
	/**
	 * 原生图形元素组件
	 */
	private String graphic;
	/**
	 * 日历坐标系组件
	 */
	private String calendar;
	/**
	 * 数据集
	 */
	private String dataset;
	/**
	 * 无障碍
	 */
	private String aria;
	/**
	 * 系列列表
	 */
	private String series;
	/**
	 * 调色盘
	 */
	private String color;
	/**
	 * 背景色
	 */
	private String backgroundColor;
	/**
	 * 全局字体样式
	 */
	private String textStyle;
	/**
	 * 是否开启动画
	 */
	private String animation;
	/**
	 * 是否开启动画阈值
	 */
	private String animationThreshold;
	/**
	 * 初始动画时长
	 */
	private String animationDuration;
	/**
	 * 初始动画缓动效果
	 */
	private String animationEasing;
	/**
	 * 初始动画延迟
	 */
	private String animationDelay;
	/**
	 * 数据更新动画时长
	 */
	private String animationDuratioonUpdate;
	/**
	 * 数据跟新动画缓动效果
	 */
	private String animationEasingUpdate;
	/**
	 * 数据更新动画延迟
	 */
	private String animationDelayUpdate;
	/**
	 * 图形混合
	 */
	private String blendMode;
	/**
	 * 图形数量阈值
	 */
	private String hoverLayerThreshold;
	/**
	 * 是否使用UTC时间
	 */
	private String useUtc;

	private String hrefId;

	public String getHrefId() {
		return hrefId;
	}

	public void setHrefId(String hrefId) {
		this.hrefId = hrefId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getXAxis() {
		return xAxis;
	}

	public String getxAxis() {
		return xAxis;
	}

	public void setXAxis(String xAxis) {
		this.xAxis = xAxis;
	}

	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}

	public String getYAxis() {
		return yAxis;
	}

	public String getyAxis() {
		return yAxis;
	}

	public void setYAxis(String yAxis) {
		this.yAxis = yAxis;
	}

	public void setyAxis(String yAxis) {
		this.yAxis = yAxis;
	}

	public String getPolar() {
		return polar;
	}

	public void setPolar(String polar) {
		this.polar = polar;
	}

	public String getRadiusAxis() {
		return radiusAxis;
	}

	public void setRadiusAxis(String radiusAxis) {
		this.radiusAxis = radiusAxis;
	}

	public String getAngleAxis() {
		return angleAxis;
	}

	public void setAngleAxis(String angleAxis) {
		this.angleAxis = angleAxis;
	}

	public String getRadar() {
		return radar;
	}

	public void setRadar(String radar) {
		this.radar = radar;
	}

	public String getDataZoom() {
		return dataZoom;
	}

	public void setDataZoom(String dataZoom) {
		this.dataZoom = dataZoom;
	}

	public String getVisualMap() {
		return visualMap;
	}

	public void setVisualMap(String visualMap) {
		this.visualMap = visualMap;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getAxisPointer() {
		return axisPointer;
	}

	public void setAxisPointer(String axisPointer) {
		this.axisPointer = axisPointer;
	}

	public String getToolbox() {
		return toolbox;
	}

	public void setToolbox(String toolbox) {
		this.toolbox = toolbox;
	}

	public String getBrush() {
		return brush;
	}

	public void setBrush(String brush) {
		this.brush = brush;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getParallel() {
		return parallel;
	}

	public void setParallel(String parallel) {
		this.parallel = parallel;
	}

	public String getParallelAxis() {
		return parallelAxis;
	}

	public void setParallelAxis(String parallelAxis) {
		this.parallelAxis = parallelAxis;
	}

	public String getSingleAxis() {
		return singleAxis;
	}

	public void setSingleAxis(String singleAxis) {
		this.singleAxis = singleAxis;
	}

	public String getTimeLine() {
		return timeLine;
	}

	public void setTimeLine(String timeLine) {
		this.timeLine = timeLine;
	}

	public String getGraphic() {
		return graphic;
	}

	public void setGraphic(String graphic) {
		this.graphic = graphic;
	}

	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getAria() {
		return aria;
	}

	public void setAria(String aria) {
		this.aria = aria;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getTextStyle() {
		return textStyle;
	}

	public void setTextStyle(String textStyle) {
		this.textStyle = textStyle;
	}

	public String getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		this.animation = animation;
	}

	public String getAnimationThreshold() {
		return animationThreshold;
	}

	public void setAnimationThreshold(String animationThreshold) {
		this.animationThreshold = animationThreshold;
	}

	public String getAnimationDuration() {
		return animationDuration;
	}

	public void setAnimationDuration(String animationDuration) {
		this.animationDuration = animationDuration;
	}

	public String getAnimationEasing() {
		return animationEasing;
	}

	public void setAnimationEasing(String animationEasing) {
		this.animationEasing = animationEasing;
	}

	public String getAnimationDelay() {
		return animationDelay;
	}

	public void setAnimationDelay(String animationDelay) {
		this.animationDelay = animationDelay;
	}

	public String getAnimationDuratioonUpdate() {
		return animationDuratioonUpdate;
	}

	public void setAnimationDuratioonUpdate(String animationDuratioonUpdate) {
		this.animationDuratioonUpdate = animationDuratioonUpdate;
	}

	public String getAnimationEasingUpdate() {
		return animationEasingUpdate;
	}

	public void setAnimationEasingUpdate(String animationEasingUpdate) {
		this.animationEasingUpdate = animationEasingUpdate;
	}

	public String getAnimationDelayUpdate() {
		return animationDelayUpdate;
	}

	public void setAnimationDelayUpdate(String animationDelayUpdate) {
		this.animationDelayUpdate = animationDelayUpdate;
	}

	public String getBlendMode() {
		return blendMode;
	}

	public void setBlendMode(String blendMode) {
		this.blendMode = blendMode;
	}

	public String getHoverLayerThreshold() {
		return hoverLayerThreshold;
	}

	public void setHoverLayerThreshold(String hoverLayerThreshold) {
		this.hoverLayerThreshold = hoverLayerThreshold;
	}

	public String getUseUtc() {
		return useUtc;
	}

	public void setUseUtc(String useUtc) {
		this.useUtc = useUtc;
	}
}
