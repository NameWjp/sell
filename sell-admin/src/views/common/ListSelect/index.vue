<template>
  <el-select
    :value="value"
    filterable
    clearable
    placeholder="请选择"
    style="width: 100%"
    :disabled="disabled"
    @change="handleChange"
  >
    <el-option v-if="query" label="全部" :value="null" />
    <el-option
      v-for="item in options"
      :key="item.id"
      :label="item[labelKey]"
      :value="item.id"
    />
  </el-select>
</template>

<script>
export default {
  props: {
    value: [Number, String],
    listRequest: {
      type: Function,
    },
    labelKey: {
      type: String,
      default: 'name',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    query: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    this.queryList();
  },
  data() {
    return {
      options: [],
    };
  },
  methods: {
    async queryList() {
      const { list } = await this.listRequest({ pageSize: 0 });
      this.options = list;
    },
    handleChange(newValue) {
      this.$emit('input', newValue);
      const row = this.options.find(item => item.id === newValue);
      this.$emit('change', row || {});
    },
  },
  watch: {
    listRequest() {
      this.queryList();
    },
  },
};
</script>

<style scoped lang="scss">
</style>
