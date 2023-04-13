<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>分类管理</title>

<div class="workingArea">
    <h1 class="label label-info" >查看后台统计</h1>
    <br>
    <br>

    <form action="admin_statistics_day_list" method="post" style="float: left">
        <div class="daySearchDiv" style="text-align: left;">
            <wui-date
                    format="yyyy-mm"
                    placeholder="请选择月份（默认当前年月）"
                    id="date3"
                    name="date"
                    btns="{'ok':'确定','now':'此刻'}"
                    ng-model="date"
            >
            </wui-date>

            <button  type="submit" class="searchButton" style="background-color: #b2dba1;width: 60px;height: 27px">统计</button>
        </div>
    </form>

    <form action="admin_statistics_month_list" method="post">
        <div class="monthSearchDiv" style="text-align: right;">
            <input type="text" name="date" placeholder="请填写年份（默认当前年份）">
            <button  type="submit" class="searchButton" style="background-color: #b2dba1;width: 60px;height: 27px">统计</button>
        </div>
    </form>

    <br>

    <div id="visualization" style="width: 1450px; height: 565px;text-align: center"></div>

    <script type="text/javascript">
      var myChart = echarts.init(document.getElementById('visualization'));

      var option = {
        backgroundColor: '#b2dba1',

        title: {
          text: '数 据 统 计',
          subtext: '${subtext}',
          x: 'center'
        },

        legend: {
          // orient 设置布局方式，默认水平布局，可选值：'horizontal'（水平） ¦ 'vertical'（垂直）
          orient: 'horizontal',
          // x 设置水平安放位置，默认全图居中，可选值：'center' ¦ 'left' ¦ 'right' ¦ {number}（x坐标，单位px）
          x: 'left',
          // y 设置垂直安放位置，默认全图顶端，可选值：'top' ¦ 'bottom' ¦ 'center' ¦ {number}（y坐标，单位px）
          y: 'top',
          data: ['访问量','销售量','收入']
        },

        //  图表距边框的距离,可选值：'百分比'¦ {number}（单位px）
        grid: {
          top: '16%',   // 等价于 y: '16%'
          left: '3%',
          right: '8%',
          bottom: '3%',
          width: 'auto',
          height: 'auto',
          containLabel: true
        },

        // 提示框
        tooltip: {
          trigger: 'axis'
        },

        //工具框，可以选择
        toolbox: {
          feature: {
            saveAsImage: {} //下载工具
          }
        },

        xAxis: {
          name: '${xname}',
          type: 'category',
          axisLine: {
            lineStyle: {
              // 设置x轴颜色
              color: '#912CEE'
            }
          },
          // 设置X轴数据旋转倾斜
          axisLabel: {
            rotate: 30, // 旋转角度
            interval: 0  //设置X轴数据间隔几个显示一个，为0表示都显示
          },
          // boundaryGap值为false的时候，折线第一个点在y轴上
          boundaryGap: false,
          data: ${timeList}
        },

        yAxis: {
          name: '数值',
          type: 'value',
          min:0, // 设置y轴刻度的最小值
          max:${max},  // 设置y轴刻度的最大值
          splitNumber:${splitNumber},  // 设置y轴刻度间隔个数
          axisLine: {
            lineStyle: {
              // 设置y轴颜色
              color: '#FFF0F5'
            }
          },
        },

        series: [
          {
            name: '访问量',
            data: ${visitList},
            type: 'line',
            symbol: 'none',
            smooth: 0.5,
          },

          {
            name: '销售量',
            data: ${saleList},
            type: 'line',
            symbol: 'none',
            smooth: 0.5,
          },

          {
            name: '收入',
            data: ${incomeList},
            type: 'line',
            symbol: 'none',
            smooth: 0.5,
          }
        ],

        color: ['#00EE00', '#FF9F7F','#FFD700']
      };

      myChart.setOption(option);
    </script>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>