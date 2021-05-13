<template>
  <div class="main-layout">
    <div class="page-control">
      <el-button type="primary" icon="el-icon-plus" @click="handleToEdit('add')">新增</el-button>
      <el-button type="danger" icon="el-icon-delete">删除</el-button>
    </div>
    <div class="query-form">
      <my-form :model="queryForm">
        <my-form-item label="用户名">
          <el-input v-model="queryForm.username" />
        </my-form-item>
      </my-form>
    </div>
    <el-table
      :data="list"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column
        type="index"
        width="50"
      />
      <el-table-column
        prop="username"
        label="用户名"
      />
      <el-table-column
        label="操作"
        width="100"
      >
        <template slot-scope="scope">
          <el-button type="text">查看</el-button>
          <el-button type="text">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';

export default {
  name: 'AccountManage',
  data() {
    return {
      queryForm: {},
      list: [],
    };
  },
  methods: {
    handleToEdit(type, row = {}) {
      const query = { type };
      if (type !== 'add') {
        query.id = row.id;
      }
      this.$router.push({
        name: 'AccountManageEdit',
        query,
      });
    },
    handleSelectionChange(ids) {
      console.log(ids);
    },
  },
  components: {
    MyForm,
    MyFormItem,
  },
};
</script>

<style scoped lang="scss">
</style>
