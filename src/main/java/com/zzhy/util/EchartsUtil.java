package com.zzhy.util;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzhy.common.util.MyStringUtil;

public class EchartsUtil {
    /**
     * 反序列化得到option对象
     * @param json
     * @return
     */
    public static Option genOption(String json) {
        GsonOption option = GsonUtil.fromJSON(MyStringUtil.clearBlank(json), GsonOption.class);
        return option;
    }

    /**
     * json对象的制定项
     * @param json
     * @param itemName
     * @return
     */
    public static String genItem(String json,String itemName){
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String item = jsonObject.get("title").getAsString();

        return item;
//        Class clz = Option.class;
//        Field[] fields = clz.getFields();
//        for (Field field:fields) {
//            if (StringUtils.equals(field.getName(),itemName)){
//                try {
//                    clz.getMethod(itemName).invoke();
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        genOption(json);
    }

    public static void main(String[] args) {

        String json = "{\n" +
                "    title: {\n" +
                "        text: '雨量流量关系图',\n" +
                "        subtext: '数据来自西安兰特水电测控技术有限公司',\n" +
                "        x: 'center'\n" +
                "    },\n" +
                "    tooltip: {\n" +
                "        trigger: 'axis',\n" +
                "        axisPointer: {\n" +
                "            animation: false\n" +
                "        }\n" +
                "    },\n" +
                "    legend: {\n" +
                "        data: ['流量', '降雨量'],\n" +
                "        x: 'left'\n" +
                "    },\n" +
                "    toolbox: {\n" +
                "        feature: {\n" +
                "            dataZoom: {\n" +
                "                yAxisIndex: 'none'\n" +
                "            },\n" +
                "            restore: {},\n" +
                "            saveAsImage: {}\n" +
                "        }\n" +
                "    },\n" +
                "    axisPointer: {\n" +
                "        link: {xAxisIndex: 'all'}\n" +
                "    },\n" +
                "    dataZoom: [\n" +
                "        {\n" +
                "            show: true,\n" +
                "            realtime: true,\n" +
                "            start: 30,\n" +
                "            end: 70,\n" +
                "            xAxisIndex: [0, 1]\n" +
                "        },\n" +
                "        {\n" +
                "            type: 'inside',\n" +
                "            realtime: true,\n" +
                "            start: 30,\n" +
                "            end: 70,\n" +
                "            xAxisIndex: [0, 1]\n" +
                "        }\n" +
                "    ],\n" +
                "    grid: {\n" +
                "        left: 50,\n" +
                "        right: 50,\n" +
                "        height: '35%'\n" +
                "    }," +
                "    xAxis: [\n" +
                "        {\n" +
                "            type: 'category',\n" +
                "            boundaryGap: false,\n" +
                "            axisLine: {onZero: true},\n" +
                "            data: []\n" +
                "        },\n" +
                "        {\n" +
                "            gridIndex: 1,\n" +
                "            type: 'category',\n" +
                "            boundaryGap: false,\n" +
                "            axisLine: {onZero: true},\n" +
                "            data: [],\n" +
                "            position: 'top'\n" +
                "        }\n" +
                "    ],\n" +
                "    yAxis: [\n" +
                "        {\n" +
                "            name: '流量(m^3/s)',\n" +
                "            type: 'value',\n" +
                "            max: 500\n" +
                "        },\n" +
                "        {\n" +
                "            gridIndex: 1,\n" +
                "            name: '降雨量(mm)',\n" +
                "            type: 'value',\n" +
                "            inverse: true\n" +
                "        }\n" +
                "    ],\n" +
                "    series: [\n" +
                "        {\n" +
                "            name: '流量',\n" +
                "            type: 'line',\n" +
                "            symbolSize: 8,\n" +
                "            hoverAnimation: false,\n" +
                "            data:null\n" +
                "        },\n" +
                "        {\n" +
                "            name: '降雨量',\n" +
                "            type: 'line',\n" +
                "            xAxisIndex: 1,\n" +
                "            yAxisIndex: 1,\n" +
                "            symbolSize: 8,\n" +
                "            hoverAnimation: false,\n" +
                "            data:null\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        json = "{\n" +
                "    xAxis: [{\n" +
                "        type: 'category',\n" +
                "        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']\n" +
                "    }],\n" +
                "    yAxis: [{\n" +
                "        type: 'value'\n" +
                "    }],\n" +
                "    series: [{\n" +
                "        data: [820, 932, 901, 934, 1290, 1330, 1320],\n" +
                "        type: 'line'\n" +
                "    }]\n" +
                "}";

        GsonOption gsonOption = (GsonOption) genOption(json);
        System.out.println(gsonOption.toPrettyString());

//        System.out.println(genItem(json,"title"));
    }
}
