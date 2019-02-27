package com.zzhy.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * @Description: TODO
 * @Author; majt
 * @Email: gentmjt@gmail.com
 * @Date: 2018/11/9 10:48
 * @Version: 1.0
 */
public class MyStringUtil extends StringUtils {

    /**
     * 去掉字符串中的空格,换行符,缩进符
     * @param string
     * @return
     */
    public static String clearBlank(String string){
        if (isNotBlank(string)){
            string = replace(string," ","");
            string = replace(string,"\t","");
            string = replace(string,"\n","");
            return string;
        }else {
            return null;
        }
    }

    /**
     * 去掉字符串中的空格并截取指定长度
     * @param index 从0下标开始,截取指定长度字符串
     * @return
     */
    public static String splitStr(String string,int index){
        string = clearBlank(string);
        if (null != string){
            if (string.length() < index){
                index = string.length()-1;
            }
            return string.substring(0,index);
        }
       return null;
    }

    public static String createKey(String string){
        string = clearBlank(string);
        if (null != string){
            if (string.length() <= 15){
                return string;
            }else {
                string = string.substring(0,15)+string.substring(string.length()-16,string.length()-1);
            }
        }
        return string;
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
        String s = clearBlank("{\n" +
                "        data: ['流量', '降雨量'],\n" +
                "        x: 'left'\n" +
                "    }");
        System.out.println(s);
    }
}
