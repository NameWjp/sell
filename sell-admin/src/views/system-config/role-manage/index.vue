<template>
  <div class="main-layout">
    <div class="page-control">
      <el-button v-permission="'role:create'" type="primary" icon="el-icon-plus" @click="handleToEdit('add')">新增</el-button>
      <el-button v-permission="'role:delete'" type="danger" icon="el-icon-delete" :disabled="!selectedRows.length" @click="handleDeleteByRows(selectedRows)">删除</el-button>
    </div>
    <div class="query-form">
      <my-form :model="queryForm" @query="handleQuery">
        <my-form-item label="角色名称" prop="name">
          <el-input v-model="queryForm.name" />
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
      <el-table-column prop="name" label="角色名称" />
      <el-table-column prop="description" label="角色描述" />
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
          <el-button v-permission="'role:update'" type="text" @click="handleToEdit('edit', row)">编辑</el-button>
          <el-button v-permission="'role:delete'" type="text" @click="handleDeleteByRows([row])">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <my-pagination v-model="pagination" @mounted="queryList" @change="queryList" />
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';
import MyPagination from '@/components/MyPagination';
import { getRoleList, deleteRole } from '@/api/role';

export default {
  name: 'RoleManage',
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
        name: 'RoleManageEdit',
        query,
      });
    },
    handleQuery() {
      this.pagination.currentPage = 1;
      this.queryList();
    },
    async queryList() {
      const { list, ...pagination } = await getRoleList(this.pagination, this.queryForm);
      this.list = list;
      this.pagination = pagination;
    },
    handleSelectionChange(rows) {
      this.selectedRows = rows;
    },
    handleDeleteByRows(rows) {
      const names = rows.map(({ name }) => name);
      const ids = rows.map(({ id }) => id);

      this.$confirmMsg({
        message: `是否删除名称为 ${names.join(',')} 的角色`,
        onOk: async () => {
          const { code } = await deleteRole(ids);
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
