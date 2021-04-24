<template>
  <el-button v-bind="{ loading: isLoading, ...otherProps }" @click="handleClick" v-on="otherHander">
    <slot>确认</slot>
  </el-button>
</template>

<script>
/* eslint-disable vue/require-prop-types */
export default {
  name: 'LoadingBtn',
  props: [
    'size', 'type', 'plain', 'round',
    'circle', 'loading', 'disabled',
    'icon', 'autofocus', 'nativeType',
  ],
  data() {
    return {
      isLoading: this.loading,
    };
  },
  computed: {
    otherProps() {
      const { loading, ...props } = this.$props;
      return props;
    },
    otherHander() {
      const { click, ...handle } = this.$listeners;
      return handle;
    },
  },
  methods: {
    async handleClick(...params) {
      const { click: func } = this.$listeners;
      if (typeof func === 'function') {
        const ret = func(...params);
        if (ret && ret.then) {
          this.isLoading = true;
          try {
            await ret;
          } catch (e) {}
          this.isLoading = false;
        }
      }
    },
  },
  watch: {
    loading(status) {
      this.isLoading = status;
    },
  },
};
</script>
