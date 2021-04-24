<template>
  <el-select
    :disabled="disabled"
    :value="value"
    placeholder="请选择"
    style="width: 100%;"
    :filterable="filterable"
    :multiple="multiple"
    @change="handleChange"
  >
    <el-option v-if="query" label="全部" :value="null" />
    <el-option
      v-for="[dictValue, dictName] in options"
      :key="dictValue"
      :label="dictName"
      :value="dictValue"
    />
  </el-select>
</template>

<script>
export default {
  props: {
    filterable: {
      type: Boolean,
      default: false,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    dictCode: {
      type: String,
    },
    value: [String, Number, Boolean, Array],
    query: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    options() {
      if (this.dictCode) {
        return Object.entries(this.$store.state.dict.dictMap[this.dictCode]);
      }
      return [];
    },
  },
  methods: {
    handleChange(newValue) {
      this.$emit('input', newValue);
    },
  },
  components: {},
};
</script>

<style scoped lang="scss">
</style>
