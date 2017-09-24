<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/js/plugins/echart/echarts-all.js"></script>
</head>
<body>
<div id="main" style="height:700px; width: 850px;"></div>
<script>
    var myChart = echarts.init(document.getElementById('main'));
    option = {
        title : {
            text: '销售报表',
            subtext: '<s:property value="groupType"/>',
            x:'center'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['销售总金额'],
            x:'left'
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : <s:property escape="false" value="#groupList"/>
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'销售总金额',
                type:'bar',
                data:<s:property escape="false" value="#amountList"/>,
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    // 为echarts对象加载数据
    myChart.setOption(option);
</script>
</body>
</html>
