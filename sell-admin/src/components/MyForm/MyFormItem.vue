<template>
  <el-col v-bind="colSpan">
    <el-form-item :prop="prop" :label="label">
      <slot />
    </el-form-item>
  </el-col>
</template>

<script>
import { QL, SL, LQL } from '@/utils/form-layout';
import { TYPE } from './constant';

const defaultSpan = {
  xs: 24,
  sm: 20,
  md: 10,
  lg: 8,
  xl: 6,
};

export default {
  name: 'MyFormItem',
  props: {
    label: {
      type: String,
      default: '',
    },
    prop: {
      type: String,
    },
    span: {
      type: Number,
    },
  },
  data() {
    return {
    };
  },
  computed: {
    _MyForm() {
      let parent = this.$parent;
      while (parent) {
        if (parent.$options._componentTag !== 'my-form') {
          parent = parent.$parent;
        } else {
          return parent;
        }
      }
      return false;
    },
    colSpan() {
      if (this.span) {
        return { span: this.span };
      }
      if (this._MyForm.type === TYPE.query) {
        return QL.span;
      }
      if (this._MyForm.type === TYPE.submit) {
        return SL.span;
      }
      if (this._MyForm.type === TYPE.largeQuery) {
        return LQL.span;
      }
      return defaultSpan;
    },
  },
  components: {},
};
</script>

<style scoped lang="scss">
</style>
