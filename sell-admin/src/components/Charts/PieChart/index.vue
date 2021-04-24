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
      default: '300px',
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
    title: {
      type: String,
      default: '明细',
    },
    radius: {
      type: Array,
      default: () => [15, 95],
    },
    roseType: {
      type: Boolean,
      default: false,
    },
    unit: {
      type: String,
      default: '',
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
        tooltip: {
          trigger: 'item',
          formatter: `{a} <br/>{b} : {c}${this.unit} ({d}%)`,
        },
        legend: {
          left: 'center',
          bottom: '10',
          data: this.chartData.map(({ name }) => name),
        },
        series: [
          {
            name: this.title,
            type: 'pie',
            roseType: this.roseType ? 'radius' : false,
            radius: this.radius,
            center: ['50%', '38%'],
            data: this.chartData,
            animationEasing: 'cubicInOut',
            animationDuration: 1800,
            label: {
              formatter: ({ name, value }) => {
                return `${name}(${value}${this.unit})`;
              },
            },
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
