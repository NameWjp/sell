<template>
  <div :class="className" :style="{ height:height, width:width }" />
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import resize from '../mixins/resize';

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart',
    },
    width: {
      type: String,
      default: '100%',
    },
    height: {
      type: String,
      default: '350px',
    },
    /**
     * 图表数据类型
     * [
     *  { name: 'name1', value: [1, 2, 3] },
     *  { name: 'name2', value: [4, 5, 6] }
     * ]
    */
    chartData: {
      type: Array,
      required: true,
    },
    /**
     * x轴数据类型
     * ['x1', 'x2', 'x3']
    */
    xAxisData: {
      type: Array,
      required: true,
    },
    yAxisName: {
      type: String,
    },
  },
  data() {
    return {
      chart: null,
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons');
      this.setOptions();
    },
    setOptions() {
      this.chart.setOption({
        xAxis: {
          data: this.xAxisData,
          boundaryGap: false,
          axisTick: {
            show: false,
          },
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
          },
          padding: [5, 10],
        },
        yAxis: {
          name: this.yAxisName,
          axisTick: {
            show: false,
          },
        },
        legend: {
          data: this.chartData.map(({ name }) => name),
        },
        series: this.chartData.map(({ name, value }) => {
          return {
            name,
            smooth: true,
            type: 'line',
            data: value,
            animationDuration: 1800,
            animationEasing: 'cubicInOut',
          };
        }),
      });
    },
  },
  watch: {
    chartData: {
      deep: true,
      handler() {
        this.setOptions();
      },
    },
  },
};
</script>
