package com.zzhy.moudles.old.impl;

import com.github.abel533.echarts.*;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.*;
import com.github.abel533.echarts.style.ItemStyle;
import com.zzhy.common.exception.RRException;
import com.zzhy.moudles.old.entity.EChartsOptionEntity;
import com.zzhy.moudles.old.entity.EChartsSqlEntity;
import com.zzhy.moudles.old.service.MyEchartsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/9 13:53
 * @Version 1.0
 */
@Service("echartsService")
@Slf4j
public class MyEchartsServiceImpl implements MyEchartsService {
    @Override
    public Option createOption(Option option, EChartsOptionEntity optionEntity, List<Object> legendList, List<Object> xAis, List<Object> yAis) {
        //创建一个option
        if (null == option) {
            option = new GsonOption();
            log.info("新建一个option");
        }
        //创建标题对象
        Title title1;
        if (null == option.getTitle()) {
            title1 = new Title();
            title1.text(optionEntity.getName()).subtext(optionEntity.getRemark()).x(X.center);
            log.info("新建title");
        } else {
            title1 = option.getTitle();
            title1.text(optionEntity.getName()).subtext(optionEntity.getRemark());
        }
        option.title(title1);

        if (null == option.getTooltip()) {
            option.tooltip().trigger(Trigger.axis);
            log.info("新建tooltip");
        }

        //创建图例对象
        Legend legend;
        if (null == option.getLegend()) {
            legend = new Legend();
            legend.orient(Orient.horizontal).top(30).left(X.left).data(legendList == null ? null : legendList);
            legend.bottom();
            log.info("新建legend");
        } else {
            legend = option.getLegend();
            legend.setData(legendList);
        }
        option.legend(legend);

        //创建工具盒子
        if (null == option.getToolbox()) {
            option.toolbox().show(true).feature(Tool.mark
                    , Tool.dataView, new MagicType(Magic.line, Magic.bar, Magic.stack, Magic.tiled),
                    Tool.restore, Tool.saveAsImage).padding(20);
            log.info("新建toolbox");
        }

        if (null == option.getCalculable()) {
            option.calculable(true);
            log.info("新建calculable");
        }

        //创建x轴
        List categoryAxisList = new ArrayList();
        CategoryAxis categoryAxisX = new CategoryAxis();
        if (null == xAis || xAis.size() < 1) {
            categoryAxisX.show(false);
        } else {
            if (null == option.getxAxis()) {
                categoryAxisX.boundaryGap(true).show(true).position(Y.bottom).setData(xAis);
            } else {
                categoryAxisX = (CategoryAxis) option.getxAxis().get(0);
                categoryAxisX.show(true);
                categoryAxisX.setData(xAis);
            }
        }

        categoryAxisList.add(0, categoryAxisX);
        option.setxAxis(categoryAxisList);

        if (null != xAis && xAis.size() > 50) {
            option.setDataZoom(null);
            DataZoom slider = new DataZoom();
            slider.show(true).realtime(true).start(30).end(70);
            DataZoom inside = new DataZoom();
            inside.type(DataZoomType.inside).realtime(true).start(30).end(70);
            option.dataZoom(slider, inside);
        }

        //创建y轴
        CategoryAxis categoryAxisY = new CategoryAxis();
        List y = new ArrayList();
        if (null == yAis) {
            categoryAxisY.show(false);
            y.add(0, categoryAxisY);
        } else {
            if (null == option.getyAxis() || yAis.size() == 1) {
                y.add(0, new ValueAxis());
            } else {
                categoryAxisY.setData(yAis);
                y.add(0, categoryAxisY);
            }
        }
        option.yAxis(y);
        return option;
    }

    @Override
    public Series createSeries(EChartsSqlEntity sqlEntity, List<Object> data) {
        if (sqlEntity.getType().equals("bar")) {
            Bar bar = SeriesFactory.newBar(sqlEntity.getName());
            bar.setData(data);
            log.info("新建bar");
            return bar;
        } else if (sqlEntity.getType().equals("line")) {
            Line line = SeriesFactory.newLine(sqlEntity.getName()).smooth(true);
            line.itemStyle().normal().areaStyle().typeDefault();
            line.setData(data);
            log.info("新建line");
            return line;
        } else if (sqlEntity.getType().equals("pie")) {
            ItemStyle dataStyle = new ItemStyle();
            dataStyle.normal().label(new Label().show(true)).labelLine().show(true);
            Pie pie = SeriesFactory.newPie(sqlEntity.getName()).clockWise(false).radius(35, 70);
            pie.itemStyle(dataStyle).setData(data);
            pie.center();
            if (StringUtils.contains(sqlEntity.getRadius(), ",")) {
                pie.setRadius(sqlEntity.getRadius().split(","));
            } else {
                pie.radius(sqlEntity.getRadius());
            }
            pie.z(100);
            Tooltip tooltip = new Tooltip();
            tooltip.trigger(Trigger.item);
            tooltip.formatter("{a} <br/>{b}: {c} ({d}%)");
            pie.tooltip(tooltip);
            log.info("新建pie");
            return pie;
        } else {
            new RRException("未指定图形类型");
            return null;
        }
    }


    private Series createSeries1(EChartsSqlEntity sqlEntity,List<Object> data){
        Series series = null;
        switch (sqlEntity.getSeriesType()){
            case bar:
               series = SeriesFactory.newBar(sqlEntity.getName());
                break;
            case pie:
                series = SeriesFactory.newPie(sqlEntity.getName());
                break;
            case line:
                series = SeriesFactory.newLine(sqlEntity.getName());
                break;
            case scatter:
                series = SeriesFactory.newScatter(sqlEntity.getName());
                break;
            case effectScatter:
                series = SeriesFactory.newEffectScatter(sqlEntity.getName());
                break;
            case radar:
                series = new RadarSeries().name(sqlEntity.getName());
                break;
            case treemap:
                series = SeriesFactory.newTreemap(sqlEntity.getName());
                break;
            case graph:
                series = SeriesFactory.newGraph(sqlEntity.getName());
                break;
            case map:
                series = SeriesFactory.newMap(sqlEntity.getName());
                break;
            case funnel:
                series = SeriesFactory.newFunnel(sqlEntity.getName());
                break;
            case gauge:
                series = SeriesFactory.newGauge(sqlEntity.getName());
                break;
            case lines:
                series = SeriesFactory.newLines(sqlEntity.getName());
                break;
            case sankey:
                series = SeriesFactory.newSankey(sqlEntity.getName());
                break;
            case boxplot:
                series = SeriesFactory.newBoxplot(sqlEntity.getName());
                break;
            case heatmap:
                series = SeriesFactory.newHeatmap(sqlEntity.getName());
                break;
            case parallel:
                series = SeriesFactory.newParallel(sqlEntity.getName());
                break;
            case candlestick:
                series = SeriesFactory.newCandlestick(sqlEntity.getName());
                break;
        }
        return series;
    }
}
