package com.zzhy.moudles.old.entity.mynew;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Series;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyOption  extends GsonOption {
    private Title title;
    private Legend legend;
    private Toolbox toolbox;
    private List<Axis> xAxisList;
    private List<Axis> yAxisList;
    private List<Series> seriesList;

    public MyOption(Title title, Legend legend, Toolbox toolbox, List<Axis> xAxisList, List<Axis> yAxisList, List<Series> seriesList) {
        this.title = title;
        this.legend = legend;
        this.toolbox = toolbox;
        this.xAxisList = xAxisList;
        this.yAxisList = yAxisList;
        this.seriesList = seriesList;
    }

    public MyOption(String text,String subText,List<Object> legendList,List<List<Object>> xAxisDatas,List<List<Object>> yAxisDatas){
        this.title.text(text).subtext(subText);
        this.legend.orient(Orient.horizontal).top(30).left(X.left).data(legendList);
        this.toolbox.show(true).feature(Tool.mark
                , Tool.dataView, new MagicType(Magic.line, Magic.bar, Magic.stack, Magic.tiled),
                Tool.restore, Tool.saveAsImage).padding(20);
        if (xAxisDatas.size()>1){
            for (List<Object> xAxisData: xAxisDatas) {

            }
        }
    }


    public Axis createAxis(Axis axis){
        if (null == axis){
            return null;
        }

        if (null == axis.type()){
            axis.type(AxisType.value);
        }


        return axis;
    }
}
