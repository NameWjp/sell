<template>
  <div class="pg-wrap">
    <el-pagination class="pagination" v-bind="pagination" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
  </div>
</template>

<script>
import { isEqual } from 'lodash-es';

export default {
  model: {
    prop: 'nowPagination',
    event: 'input',
  },
  data() {
    return {
      pagination: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        background: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        pageSizes: [10, 20, 30, 40, 50],
      },
    };
  },
  props: {
    nowPagination: Object,
  },
  mounted() {
    this.setPagination(this.nowPagination);
    this.$emit('input', { ...this.pagination });
    this.$emit('mounted', { ...this.pagination });
  },
  methods: {
    handleChange() {
      this.$emit('input', { ...this.pagination });
      this.$emit('change', { ...this.pagination });
    },
    handleCurrentChange(pageNum) {
      this.pagination.currentPage = pageNum;
      this.handleChange();
    },
    handleSizeChange(size) {
      this.pagination.pageSize = size;
      this.handleChange();
    },
    resetPagination() {
      this.setPagination({
        total: 0,
        currentPage: 1,
        pageSize: 20,
      });
      this.handleChange();
    },
    setPagination(paginationMix) {
      this.pagination = { ...this.pagination, ...paginationMix };
    },
  },
  watch: {
    nowPagination() {
      if (!isEqual(this.nowPagination, this.pagination)) {
        this.setPagination(this.nowPagination);
        this.$emit('input', { ...this.pagination });
      }
    },
  },
};
</script>

<style scoped lang="scss">
.pg-wrap {
  margin-top: 20px;
  text-align: right;
}
</style>
