<template>
  <el-select
    :value="value"
    filterable
    clearable
    :multiple="multiple"
    placeholder="请选择"
    style="width: 100%"
    :disabled="disabled"
    @change="handleChange"
  >
    <el-option v-if="query" label="全部" :value="null" />
    <el-option
      v-for="item in options"
      :key="item.code"
      :label="item[labelKey]"
      :value="item.code"
    />
  </el-select>
</template>

<script>
import { getAllCategoryList } from '@/api/category';

export default {
  props: {
    value: [Number, String, Array],
    labelKey: {
      type: String,
      default: 'name',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    query: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      options: [],
    };
  },
  mounted() {
    this.queryList();
  },
  methods: {
    async queryList() {
      const { data: list } = await getAllCategoryList();
      this.options = list;
    },
    handleChange(newValue) {
      this.$emit('input', newValue);
      const row = this.options.find(item => item.code === newValue);
      this.$emit('change', row || {});
    },
  },
};
</script>

<style scoped lang="scss">
</style>
