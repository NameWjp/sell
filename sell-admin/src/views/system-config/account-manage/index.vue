<template>
  <div class="main-layout">
    <div class="page-control">
      <el-button type="primary" icon="el-icon-plus" @click="handleToEdit('add')">新增</el-button>
      <el-button type="danger" icon="el-icon-delete" :disabled="!selectedRows.length" @click="handleDeleteByRows(selectedRows)">删除</el-button>
    </div>
    <div class="query-form">
      <my-form :model="queryForm" @query="handleQuery">
        <my-form-item label="用户名" prop="username">
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
      <el-table-column type="selection" width="55" />
      <el-table-column type="index" width="50" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="isEnable" label="是否启用">
        <template #default="{ row }">
          {{ row.isEnable | dict('is_enable') }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间">
        <template #default="{ row }">
          {{ row.createTime | datetime }}
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="修改时间">
        <template #default="{ row }">
          {{ row.updateTime | datetime }}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="180"
      >
        <template #default="{ row }">
          <el-button type="text" @click="handleToEdit('view', row)">查看</el-button>
          <el-button type="text" @click="handleToEdit('edit', row)">编辑</el-button>
          <el-button type="text" @click="handleDeleteByRows([row])">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <my-pagination v-model="pagination" @mounted="queryList" @change="queryList" />
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';
import MyPagination from '@/components/MyPagination';
import { getUserList, deleteUser } from '@/api/user';

export default {
  name: 'AccountManage',
  data() {
    return {
      queryForm: {},
      list: [],
      pagination: {},
      selectedRows: [],
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
    handleQuery() {
      this.pagination.currentPage = 1;
      this.queryList();
    },
    async queryList() {
      const { list, ...pagination } = await getUserList(this.pagination, this.queryForm);
      this.list = list;
      this.pagination = pagination;
    },
    handleSelectionChange(rows) {
      this.selectedRows = rows;
    },
    handleDeleteByRows(rows) {
      const names = rows.map(({ username }) => username);
      const ids = rows.map(({ id }) => id);

      this.$confirmMsg({
        message: `是否删除名称为 ${names.join(',')} 的用户`,
        onOk: async () => {
          const { code } = await deleteUser(ids);
          if (code === 200) {
            this.$message.success('删除成功');
            this.queryList();
          }
        },
      });
    },
  },
  components: {
    MyForm,
    MyFormItem,
    MyPagination,
  },
};
</script>

<style scoped lang="scss">
</style>
