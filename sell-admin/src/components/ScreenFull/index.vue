<template>
  <div class="screenfull-svg">
    <svg-icon :icon-class="isFullscreen ? 'exit-fullscreen' : 'fullscreen'" @click="click" />
  </div>
</template>

<script>
import screenfull from 'screenfull';
export default {
  name: 'Screenfull',
  data() {
    return {
      isFullscreen: false,
    };
  },
  mounted() {
    this.init();
  },
  beforeDestroy() {
    this.destroy();
  },
  methods: {
    click() {
      if (!screenfull.enabled) {
        this.$message({
          message: '你的浏览器不支持此功能',
          type: 'warning',
        });
        return false;
      }
      screenfull.toggle();
    },
    change() {
      this.isFullscreen = screenfull.isFullscreen;
      this.$emit('change', this.isFullscreen);
    },
    init() {
      if (screenfull.enabled) {
        screenfull.on('change', this.change);
      }
    },
    destroy() {
      if (screenfull.enabled) {
        screenfull.off('change', this.change);
      }
    },
  },
};
</script>

<style scoped>
.screenfull-svg {
  font-size: 18px;
  display: inline-block;
  cursor: pointer;
  fill: #5a5e66;;
  width: 20px;
  height: 20px;
  vertical-align: 10px;
}
</style>
