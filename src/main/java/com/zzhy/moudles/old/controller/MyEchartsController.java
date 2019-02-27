package com.zzhy.moudles.old.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Symbol;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.LineData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.series.SeriesFactory;
import com.zzhy.common.util.R;
import com.zzhy.moudles.old.service.EchartsSqlService;
import com.zzhy.moudles.old.service.MyEchartsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/8 17:59
 * @Version 1.0
 */
@RestController
@Api(description = "ECharts接口")
public class MyEchartsController {

    @Autowired
    private MyEchartsService myEchartsService;

    @Autowired
    private EchartsSqlService sqlService;
    @ApiOperation("获取一个option")
    @GetMapping("option")
    public R option(){
        GsonOption option = new GsonOption();
        option.tooltip(Trigger.axis);
        option.legend("邮件营销", "联盟广告", "直接访问", "搜索引擎");
        option.toolbox().show(true);
        option.calculable(true);
        option.xAxis(new CategoryAxis().boundaryGap(false).data("周一", "周二", "周三", "周四", "周五", "周六", "周日"));

        option.yAxis(new ValueAxis());
        List<Series> seriesList = new ArrayList<>();
        seriesList.add(new Line().smooth(true).name("邮件营销").stack("总量").symbol(Symbol.none).data(120, 132, 301, 134, new LineData(90, Symbol.droplet, 5), 230, 210));
        seriesList.add(new Pie());
        SeriesFactory seriesFactory = new SeriesFactory();
        option.series(seriesList);
        return R.ok().put("data", JSONObject.parse(option.toPrettyString()));
    }

    @ApiOperation("获取一个option")
    @GetMapping("option1")
    public R option1(){
        List legendList = new ArrayList();
        List xAxis = new ArrayList();
        List data = new ArrayList();
        List data1 = new ArrayList();
        for (int i = 0; i <10 ; i++) {
            data.add(i);
            legendList.add(i);
            xAxis.add(i);
            data1.add((i+5));
        }
//        GsonOption option = (GsonOption) myEchartsService.createOption(null,"测试标题","子标题",legendList,xAxis);
//        Series series = myEchartsService.createSeries("line",null,"数据名称",data);
//        Series series1 = myEchartsService.createSeries("bar",null,"数据",data);
//        Series series2 = myEchartsService.createSeries("pie","500","数据",data);
//        option.series(series,series1,series2);
        return null;
    }

    @ApiOperation("测试sql")
    @PostMapping("executesql")
    public R executesql(String sql){
        return R.ok().put("data",sqlService.executeSQL_1(sql));
    }

    @ApiOperation("格式话json")
    @PostMapping("formatter")
    public R formatterJson(String json){
        Option option = GsonUtil.fromJSON(json);
        return R.ok().put("data",option.getSeries());
    }
}
