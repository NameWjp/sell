<template>
  <div class="main-layout">
    <div class="page-control">
      <el-button v-permission="'productInfo:create'" type="primary" icon="el-icon-plus" @click="handleToEdit('add')">新增</el-button>
      <el-button v-permission="'productInfo:delete'" type="danger" icon="el-icon-delete" :disabled="!selectedRows.length" @click="handleDeleteByRows(selectedRows)">删除</el-button>
    </div>
    <div class="query-form">
      <my-form :model="queryForm" @query="handleQuery">
        <my-form-item label="商品名称" prop="productName">
          <el-input v-model="queryForm.productName" />
        </my-form-item>
        <my-form-item label="商品类型" prop="categoryCode">
          <product-category-select v-model="queryForm.categoryCode" />
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
      <el-table-column prop="productName" label="商品名称" />
      <el-table-column prop="productIcon" label="商品图标" />
      <el-table-column prop="categoryName" label="商品类型" />
      <el-table-column prop="productPrice" label="商品单价(元)" />
      <el-table-column prop="productStatus" label="商品状态">
        <template #default="{ row }">
          {{ row.productStatus | dict('product_status') }}
        </template>
      </el-table-column>
      <el-table-column prop="productStock" label="商品库存" />
      <el-table-column prop="productDescription" label="商品描述" />
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
          <el-button v-permission="'productInfo:update'" type="text" @click="handleToEdit('edit', row)">编辑</el-button>
          <el-button v-permission="'productInfo:delete'" type="text" @click="handleDeleteByRows([row])">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <my-pagination v-model="pagination" @mounted="queryList" @change="queryList" />
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';
import MyPagination from '@/components/MyPagination';
import { deleteProductInfo, getProductInfoList } from '@/api/product-info';
import ProductCategorySelect from '@/views/common/ProductCategorySelect';

export default {
  name: 'ProductInfo',
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
        name: 'ProductInfoEdit',
        query,
      });
    },
    handleQuery() {
      this.pagination.currentPage = 1;
      this.queryList();
    },
    async queryList() {
      const { list, ...pagination } = await getProductInfoList(this.pagination, this.queryForm);
      this.list = list;
      this.pagination = pagination;
    },
    handleSelectionChange(rows) {
      this.selectedRows = rows;
    },
    handleDeleteByRows(rows) {
      const names = rows.map(({ productName }) => productName);
      const ids = rows.map(({ id }) => id);

      this.$confirmMsg({
        message: `是否删除名称为 ${names.join(',')} 的商品`,
        onOk: async () => {
          const { code } = await deleteProductInfo(ids);
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
    ProductCategorySelect,
  },
};
</script>

<style scoped lang="scss">
</style>
