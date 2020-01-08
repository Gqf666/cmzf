<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<!-- 准备一个DOM -->
<div id="main" style="width: 600px;height: 400px"></div>
<script type="text/javascript">
    //初始化 goEasy
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-8d58496859a8400a9dcbccaacf7d3bb6", //替换为您的应用appkey
    });
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        //标题名称
        title: {
            text: '驰名法州用户注册趋势图'
        },
        //工具提示
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["今天","一周","一个月","一年"]
        },
        yAxis: {},
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option)
    $.ajax({
       url:"${pageContext.request.contextPath}/user/selectRegetisCount",
       type: "get",
       datatype:"json",
        success:function (data) {
            myChart.setOption({
                series: [{
                    name: '男',
                    ///type :'line' 折现形式 bar 柱状图 pie 饼状图
                    type: 'bar',
                    data: data.man
                },{
                    name: '女',
                    ///type :'line' 折现形式 bar 柱状图 pie 饼状图
                    type: 'bar',
                    data: data.woman
                }
                ]
            })
        }
    })
    goEasy.subscribe({
        channel: "echarts1", //替换为您自己的channel
        onMessage: function (message) {
            var parse = JSON.parse(message.content);
            myChart.setOption({
                series: [{
                    name: '男',
                    ///type :'line' 折现形式 bar 柱状图 pie 饼状图
                    type: 'bar',
                    data: parse.man
                },{
                    name: '女',
                    ///type :'line' 折现形式 bar 柱状图 pie 饼状图
                    type: 'bar',
                    data: parse.woman
                }
                ]
            })
        }
    });

</script>