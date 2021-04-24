<template>
  <div>
    <screen-full />
    <el-input v-model="text" />
    <loading-btn @click="handleOk">提交表单</loading-btn>
    <el-button type="primary" @click="handleConfirm">确认按钮</el-button>
    <el-button type="primary" @click="handleSetPagination">修改分页</el-button>
    <my-pagination v-model="pagination" @mounted="handlePageMounted" @change="handlePageChange" />
  </div>
</template>

<script>
import LoadingBtn from '@/components/LoadingBtn';
import ScreenFull from '@/components/ScreenFull';
import MyPagination from '@/components/MyPagination';

function delay(time) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve({ data: 666 });
    }, time);
  });
}

export default {
  name: 'TestPage',
  data() {
    return {
      text: '',
      pagination: {},
    };
  },
  methods: {
    handlePageMounted(pagination) {
      console.log('mounted', pagination);
    },
    handlePageChange(pagination) {
      console.log('change', pagination);
    },
    handleSetPagination() {
      this.pagination = { pageSize: 40, currentPage: 2, total: 100 };
    },
    async handleOk(e) {
      const data = await delay(2000);
      console.log(data);
    },
    handleConfirm() {
      this.$confirmMsg({
        message: '请确认删除内容xxxx',
        onOk: async () => {
          const data = await delay(2000);
          this.$message.success('删除成功');
        },
      });
    },
  },
  components: {
    LoadingBtn,
    ScreenFull,
    MyPagination,
  },
};
</script>

<style>

</style>
