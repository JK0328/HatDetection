<template>
  <div class="driverdetection-container layout-pd">
    <!-- <h2 class="page-title">驾驶人头盔检测分析</h2> -->
    <el-row :gutter="15" class="driverdetection-row">
      <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
        <div class="driverdetection-card">
          <div class="driverdetection-card-title">各年龄段不佩戴头盔分布</div>
          <div ref="ageChart" class="chart"></div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
        <div class="driverdetection-card">
          <div class="driverdetection-card-title">男女性别不戴头盔情况</div>
          <div ref="genderChart" class="chart"></div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="15" class="driverdetection-row">
      <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
        <div class="driverdetection-card">
          <div class="driverdetection-card-title">每天不戴头盔数目</div>
          <div ref="dayChart" class="chart"></div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12">
        <div class="driverdetection-card">
          <div class="driverdetection-card-title">不同地区不戴头盔数目</div>
          <div ref="regionChart" class="chart"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import { useDriverDetectionApi } from '/@/api/driverdetection';

const { getNoHelmetByAge, getNoHelmetByGender, getNoHelmetByDay, getNoHelmetByRegion } = useDriverDetectionApi();

const ageChart = ref();
const genderChart = ref();
const dayChart = ref();
const regionChart = ref();

const mainColor = '#2E7D32'; // 深绿色
const subColor = '#C8E6C9'; // 绿色边框/背景
const barColor = {
  type: 'linear', x: 0, y: 0, x2: 0, y2: 1,
  colorStops: [
    { offset: 0, color: 'rgba(76,175,80,0.7)' },
    { offset: 1, color: 'rgba(76,175,80,0.1)' }
  ]
};

const renderChart = (el: HTMLElement, option: any) => {
  const chart = echarts.init(el);
  chart.setOption(option);
  window.addEventListener('resize', () => chart.resize());
};

onMounted(async () => {
  // 年龄段
  const ageRes = await getNoHelmetByAge();
  renderChart(ageChart.value, {
    title: { text: '', left: 'center', textStyle: { color: mainColor, fontWeight: 'bold' } },
    grid: { top: 40, right: 20, bottom: 30, left: 40 },
    xAxis: { type: 'category', data: ageRes.data?.map((i: any) => i.ageGroup) || [], axisLabel: { color: mainColor } },
    yAxis: { type: 'value', axisLabel: { color: mainColor }, splitLine: { lineStyle: { color: subColor } } },
    series: [{ data: ageRes.data?.map((i: any) => i.count) || [], type: 'bar', itemStyle: { color: barColor, borderRadius: [6,6,0,0] } }],
    tooltip: {},
  });
  // 性别
  const genderRes = await getNoHelmetByGender();
  renderChart(genderChart.value, {
    title: { text: '', left: 'center', textStyle: { color: mainColor, fontWeight: 'bold' } },
    grid: { top: 40, right: 20, bottom: 30, left: 40 },
    xAxis: { type: 'category', data: genderRes.data?.map((i: any) => i.gender) || [], axisLabel: { color: mainColor } },
    yAxis: { type: 'value', axisLabel: { color: mainColor }, splitLine: { lineStyle: { color: subColor } } },
    series: [{ data: genderRes.data?.map((i: any) => i.count) || [], type: 'bar', itemStyle: { color: barColor, borderRadius: [6,6,0,0] } }],
    tooltip: {},
  });
  // 天数
  const dayRes = await getNoHelmetByDay();
  renderChart(dayChart.value, {
    title: { text: '', left: 'center', textStyle: { color: mainColor, fontWeight: 'bold' } },
    grid: { top: 40, right: 20, bottom: 30, left: 40 },
    xAxis: { type: 'category', data: dayRes.data?.map((i: any) => i.date) || [], axisLabel: { color: mainColor } },
    yAxis: { type: 'value', axisLabel: { color: mainColor }, splitLine: { lineStyle: { color: subColor } } },
    series: [{ data: dayRes.data?.map((i: any) => i.count) || [], type: 'bar', itemStyle: { color: barColor, borderRadius: [6,6,0,0] } }],
    tooltip: {},
  });
  // 地区
  const regionRes = await getNoHelmetByRegion();
  renderChart(regionChart.value, {
    title: { text: '', left: 'center', textStyle: { color: mainColor, fontWeight: 'bold' } },
    grid: { top: 40, right: 20, bottom: 30, left: 40 },
    xAxis: { type: 'category', data: regionRes.data?.map((i: any) => i.region) || [], axisLabel: { color: mainColor } },
    yAxis: { type: 'value', axisLabel: { color: mainColor }, splitLine: { lineStyle: { color: subColor } } },
    series: [{ data: regionRes.data?.map((i: any) => i.count) || [], type: 'bar', itemStyle: { color: barColor, borderRadius: [6,6,0,0] } }],
    tooltip: {},
  });
});
</script>

<style scoped lang="scss">
.driverdetection-container {
  overflow: hidden;
  padding: 24px;
  background: #f8faf9;
}
.driverdetection-row {
  margin-bottom: 18px;
}
.driverdetection-card {
  width: 100%;
  height: 400px;
  border-radius: 8px;
  background: #fff;
  border: 1px solid #C8E6C9;
  box-shadow: 0 2px 12px rgba(76, 175, 80, 0.08);
  padding: 18px 18px 0 18px;
  color: #2E7D32;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  transition: box-shadow 0.3s;
  &:hover {
    box-shadow: 0 4px 24px rgba(76, 175, 80, 0.18);
  }
}
.driverdetection-card-title {
  font-size: 16px;
  font-weight: bold;
  color: #2E7D32;
  margin-bottom: 10px;
  letter-spacing: 0.5px;
}
.chart {
  width: 100%;
  height: 320px;
}
</style>
