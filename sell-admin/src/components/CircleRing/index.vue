<template>
  <div class="circle-wrapper">
    <svg
      :width="2 * radius"
      :height="2 * radius"
      :viewBox="`0 0 ${2 * radius} ${2 * radius}`"
      version="1.1"
      xmlns="http://www.w3.org/2000/svg"
    >
      <circle
        :style="{
          stroke: borderColor,
          strokeWidth: borderWidth,
          transform: 'scale(.9) rotate(-90deg)'
        }"
        :r="radius"
        :cx="radius"
        :cy="radius"
        class="circle"
        fill="transparent"
        :stroke-dasharray="dashArray"
        stroke-dashoffset="0"
      />
      <!-- stroke-dasharray 定义画圆环的距离-->
      <!-- stroke-dashoffset 定义画圆环的偏移量(在 stroke-dasharray 的基础上回退多长距离)-->
      <circle
        v-for="(item, index) in ringConfig"
        :key="index"
        :style="{
          stroke: item.stroke,
          strokeWidth: borderWidth,
          transform: `scale(.9) rotate(${getRotate(item)})`
        }"
        :r="radius"
        :cx="radius"
        :cy="radius"
        class="circle"
        fill="transparent"
        :stroke-dasharray="dashArray"
        :stroke-dashoffset="getDashoffset(item)"
      />
    </svg>
    <span class="text">
      <slot />
    </span>
  </div>
</template>

<script>

export default {
  props: {
    /**
     * 格式:
     * [
     *  {
     *    rotate: 旋转角度，默认从圆的上顶点开始(Number),
     *    stroke: 颜色(String),
     *    percent: ring占整个圆的多少(小数)
     *  }
     * ]
     */
    ringConfig: {
      type: Array,
      default: () => [],
    },
    radius: {
      type: Number,
      default: 60,
    },
    borderColor: {
      type: String,
      default: '#fff',
    },
    borderWidth: {
      type: String,
      default: '2px',
    },
  },
  data() {
    return {};
  },
  computed: {
    dashArray() {
      return 2 * Math.PI * this.radius;
    },
  },
  methods: {
    getDashoffset({ percent }) {
      return (1 - percent) * this.dashArray;
    },
    getRotate({ rotate }) {
      return `${-90 + rotate}deg`;
    },
  },
  components: {},
};
</script>

<style scoped lang="scss">
.circle-wrapper {
  position: relative;
  .circle {
    transform-origin: center;
  }
  .text {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
}
</style>
