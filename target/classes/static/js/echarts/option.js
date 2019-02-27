var option = {
    title: {
        text: '雨量流量关系图',
        subtext: '数据来自西安兰特水电测控技术有限公司',
        x: 'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            animation: false
        }
    },
    legend: {
        data: ['流量', '降雨量'],
        x: 'left'
    },
    toolbox: {
        feature: {
            dataZoom: {
                yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
        }
    },
    axisPointer: {
        link: {xAxisIndex: 'all'}
    },
    dataZoom: [
        {
            show: true,
            realtime: true,
            start: 30,
            end: 70,
            xAxisIndex: [0, 1]
        },
        {
            type: 'inside',
            realtime: true,
            start: 30,
            end: 70,
            xAxisIndex: [0, 1]
        }
    ],
    grid: {
        left: 50,
        right: 50,
        top: '55%',
        height: '35%'
    },
    xAxis: [
        {
            type: 'category',
            boundaryGap: false,
            axisLine: {onZero: true},
            data: []
        },
        {
            gridIndex: 1,
            type: 'category',
            boundaryGap: false,
            axisLine: {onZero: true},
            data: [],
            position: 'top'
        }
    ],
    yAxis: [
        {
            name: '流量(m^3/s)',
            type: 'value',
            max: 500
        },
        {
            gridIndex: 1,
            name: '降雨量(mm)',
            type: 'value',
            inverse: true
        }
    ],
    series: [
        {
            name: '流量',
            type: 'line',
            symbolSize: 8,
            hoverAnimation: false,
            data:null
        },
        {
            name: '降雨量',
            type: 'line',
            xAxisIndex: 1,
            yAxisIndex: 1,
            symbolSize: 8,
            hoverAnimation: false,
            data:null
        }
    ]
}