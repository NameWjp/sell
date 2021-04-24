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
     *  { name: 'name1', value: 123 },
     *  { name: 'name2', value: 456 }
     * ]
    */
    chartData: {
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
          data: this.chartData.map(({ name }) => name),
          axisTick: {
            alignWithLabel: true,
          },
        },
        grid: {
          top: 30,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
          padding: [5, 10],
        },
        yAxis: {
          name: this.yAxisName,
          axisTick: {
            show: false,
          },
        },
        series: [
          {
            type: 'bar',
            data: this.chartData.map(({ value }) => value),
            animationDuration: 1800,
            animationEasing: 'cubicInOut',
            barWidth: '60%',
          },
        ],
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
