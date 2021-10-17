<template>
  <div class="main-layout">
    <div class="page-control">
      <el-button v-permission="'category:create'" type="primary" icon="el-icon-plus" @click="handleToEdit('add')">新增</el-button>
      <el-button v-permission="'category:delete'" type="danger" icon="el-icon-delete" :disabled="!selectedRows.length" @click="handleDeleteByRows(selectedRows)">删除</el-button>
    </div>
    <div class="query-form">
      <my-form :model="queryForm" @query="handleQuery">
        <my-form-item label="名称" prop="name">
          <el-input v-model="queryForm.name" />
        </my-form-item>
        <my-form-item label="编号" prop="code">
          <el-input v-model="queryForm.code" />
        </my-form-item>
      </my-form>
    </div>
    <el-table
      :data="list"
      border
      stripe
      @sort-change="handleSortChange"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column type="index" width="50" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="code" label="编码" />
      <el-table-column prop="sort" label="排序" sortable="custom" />
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
          <el-button v-permission="'category:update'" type="text" @click="handleToEdit('edit', row)">编辑</el-button>
          <el-button v-permission="'category:delete'" type="text" @click="handleDeleteByRows([row])">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <my-pagination v-model="pagination" @mounted="queryList" @change="queryList" />
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';
import MyPagination from '@/components/MyPagination';
import { getCategoryList, deleteCategory } from '@/api/category';

export default {
  name: 'ProductCategory',
  data() {
    return {
      queryForm: {
        sort: null,
      },
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
        name: 'ProductCategoryEdit',
        query,
      });
    },
    handleQuery() {
      this.pagination.currentPage = 1;
      this.queryList();
    },
    async queryList() {
      const { list, ...pagination } = await getCategoryList(this.pagination, this.queryForm);
      this.list = list;
      this.pagination = pagination;
    },
    handleSelectionChange(rows) {
      this.selectedRows = rows;
    },
    handleSortChange({ prop, order }) {
      if (prop === 'sort') {
        if (order === 'ascending') {
          this.queryForm.sort = 1;
        } else if (order === 'descending') {
          this.queryForm.sort = 2;
        } else {
          this.queryForm.sort = null;
        }
      }

      this.handleQuery();
    },
    handleDeleteByRows(rows) {
      const names = rows.map(({ name }) => name);
      const ids = rows.map(({ id }) => id);

      this.$confirmMsg({
        message: `是否删除名称为 ${names.join(',')} 的商品类型`,
        onOk: async () => {
          const { code } = await deleteCategory(ids);
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
