<template>
  <el-form ref="form" :rules="rules" :disabled="disabled" :model="model" :label-width="labelWidth">
    <el-row :gutter="rowGutter">
      <slot />
      <template v-if="showControl">
        <my-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleResetForm">重置</el-button>
        </my-form-item>
      </template>
    </el-row>
    <el-row :gutter="rowGutter" :class="{ 'form-control': type === TYPE.submit }">
      <slot name="control" />
    </el-row>
  </el-form>
</template>

<script>
import { QL, SL, LQL } from '@/utils/form-layout';
import { TYPE } from './constant';
import MyFormItem from './MyFormItem';

const defaultGutter = 40;

export default {
  name: 'MyForm',
  props: {
    model: {
      type: Object,
    },
    type: {
      type: String,
      default: TYPE.query,
    },
    rules: {
      type: Object,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    gutter: {
      type: Number,
    },
    labelWidth: {
      type: String,
      default: '100px',
    },
  },
  data() {
    this.TYPE = TYPE;
    return {};
  },
  computed: {
    showControl() {
      return this.type === TYPE.query || this.type === TYPE.largeQuery;
    },
    rowGutter() {
      if (this.gutter) {
        return this.gutter;
      }
      if (this.type === TYPE.query) {
        return QL.gutter;
      }
      if (this.type === TYPE.submit) {
        return SL.gutter;
      }
      if (this.type === TYPE.largeQuery) {
        return LQL.gutter;
      }
      return defaultGutter;
    },
  },
  methods: {
    validate(func) {
      let status;
      this.$refs.form.validate((valid) => {
        status = valid;
        if (typeof func === 'function') {
          func(valid);
        }
      });
      return status;
    },
    resetFields() {
      return this.$refs.form.resetFields();
    },
    handleQuery() {
      this.$emit('query', this.model);
    },
    handleResetForm() {
      this.resetFields();
      this.handleQuery();
    },
  },
  components: {
    MyFormItem,
  },
};
</script>

<style scoped lang="scss">
</style>
