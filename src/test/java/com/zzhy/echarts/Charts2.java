package com.zzhy.echarts;

import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;

/**
 * @Describe
 * @Author majt
 * @Date 2018/10/15 10:38
 * @Version 1.0
 */
public class Charts2 {
    public static void main(String[] args) {
        String s = "{\"yAxis\":[{\"type\":\"value\"}],\"xAxis\":[{\"data\":[\"309\",\"346\",\"309\",\"346\",\"346\",\"346\",\"346\",\"346\",\"346\",\"309\"],\"type\":\"category\",\"boundaryGap\":true}],\"calculable\":true,\"legend\":{\"orient\":\"horizontal\",\"data\":[\"测试折线\",\"测试柱状图\",\"测试饼图\",\"x轴\",\"测试饼图2\"],\"top\":40},\"series\":[{\"data\":[\"1\",\"3\",\"1\",\"1\",\"4\",\"3\",\"1\",\"2\",\"1\",\"1\"],\"name\":\"测试折线\",\"itemStyle\":{\"normal\":{\"areaStyle\":{\"type\":\"default\"}}},\"type\":\"line\",\"smooth\":true},{\"data\":[\"309\",\"346\",\"309\",\"346\",\"346\",\"346\",\"346\",\"346\",\"346\",\"309\"],\"name\":\"测试柱状图\",\"type\":\"bar\"},{\"clockWise\":false,\"data\":[{\"name\":\"北京\",\"value\":\"231\"},{\"name\":\"医院\",\"value\":\"232\"},{\"name\":\"行\",\"value\":\"233\"},{\"name\":\"右侧\",\"value\":\"234\"},{\"name\":\"111\",\"value\":\"235\"},{\"name\":\"000000\",\"value\":\"236\"},{\"name\":\"体温\",\"value\":\"237\"},{\"name\":\"主动脉\",\"value\":\"239\"},{\"name\":\"ceshi \",\"value\":\"241\"},{\"name\":\"病案\",\"value\":\"1\"},{\"name\":\"病情\",\"value\":\"2\"},{\"name\":\"111\",\"value\":\"222\"},{\"name\":\"222\",\"value\":\"223\"},{\"name\":\"333\",\"value\":\"224\"},{\"name\":\"222\",\"value\":\"225\"},{\"name\":\"北京\",\"value\":\"229\"},{\"name\":\"大学\",\"value\":\"230\"}],\"name\":\"测试饼图\",\"tooltip\":{\"formatter\":\"{a} <br/>{b}: {c} ({d}%)\",\"trigger\":\"item\"},\"itemStyle\":{\"normal\":{\"label\":{\"show\":false},\"labelLine\":{\"show\":false}}},\"z\":100,\"radius\":\"60\",\"type\":\"pie\"},null,{\"clockWise\":false,\"data\":[{\"name\":\"北京\",\"value\":\"231\"},{\"name\":\"医院\",\"value\":\"232\"},{\"name\":\"行\",\"value\":\"233\"},{\"name\":\"右侧\",\"value\":\"234\"},{\"name\":\"111\",\"value\":\"235\"},{\"name\":\"000000\",\"value\":\"236\"},{\"name\":\"体温\",\"value\":\"237\"},{\"name\":\"主动脉\",\"value\":\"239\"},{\"name\":\"ceshi \",\"value\":\"241\"},{\"name\":\"病案\",\"value\":\"1\"},{\"name\":\"病情\",\"value\":\"2\"},{\"name\":\"111\",\"value\":\"222\"},{\"name\":\"222\",\"value\":\"223\"},{\"name\":\"333\",\"value\":\"224\"},{\"name\":\"222\",\"value\":\"225\"},{\"name\":\"北京\",\"value\":\"229\"},{\"name\":\"大学\",\"value\":\"230\"}],\"name\":\"测试饼图2\",\"tooltip\":{\"formatter\":\"{a} <br/>{b}: {c} ({d}%)\",\"trigger\":\"item\"},\"itemStyle\":{\"normal\":{\"label\":{\"show\":false},\"labelLine\":{\"show\":false}}},\"z\":100,\"radius\":[\"70\",\"100\"],\"type\":\"pie\"}],\"toolbox\":{\"padding\":20,\"feature\":{\"saveAsImage\":{\"show\":true,\"title\":\"保存为图片\",\"type\":\"png\",\"lang\":[\"点击保存\"]},\"restore\":{\"show\":true,\"title\":\"还原\"},\"magicType\":{\"show\":true,\"title\":{\"bar\":\"柱形图切换\",\"stack\":\"堆积\",\"tiled\":\"平铺\",\"line\":\"折线图切换\"},\"type\":[\"line\",\"bar\",\"stack\",\"tiled\"]},\"dataView\":{\"show\":true,\"readOnly\":false,\"title\":\"数据视图\",\"lang\":[\"数据视图\",\"关闭\",\"刷新\"]},\"mark\":{\"lineStyle\":{\"color\":\"#1e90ff\",\"width\":2,\"type\":\"dashed\"},\"show\":true,\"title\":{\"markUndo\":\"删除辅助线\",\"markClear\":\"清空辅助线\",\"mark\":\"辅助线开关\"}}},\"show\":true},\"tooltip\":{\"trigger\":\"axis\"},\"title\":{\"subtext\":\"测试数据-描述\",\"x\":\"center\",\"text\":\"测试数据\"}}";
        GsonOption option = GsonUtil.fromJSON(s,GsonOption.class);
        System.out.println(option.toPrettyString());
    }
}
