<template>
  <div class="main-layout">
    <my-form ref="form" type="submit" :model="submitForm" :rules="submitRules">
      <my-form-item label="菜单名称" prop="name">
        <el-input v-model="submitForm.name" :disabled="isView" />
      </my-form-item>
      <my-form-item label="所属菜单" prop="parentId">
        <tree-select ref="tree" v-model="submitForm.parentId" :tree-request="getMenuTree" :disabled="isView" />
      </my-form-item>
      <my-form-item label="类型" prop="type">
        <dict-select v-model="submitForm.type" dict-code="menu_type" :disabled="isView" />
      </my-form-item>
      <my-form-item label="编号" prop="code">
        <el-input v-model.trim="submitForm.code" :disabled="isView" />
      </my-form-item>
      <my-form-item label="URL" prop="url">
        <el-input v-model.trim="submitForm.url" :disabled="isView" />
      </my-form-item>
      <my-form-item label="排序" prop="sort">
        <el-input v-model="submitForm.sort" :disabled="isView" />
      </my-form-item>
      <my-form-item label="图标" prop="icon">
        <el-input v-model="submitForm.icon" :disabled="isView" />
      </my-form-item>
      <template slot="control">
        <el-button @click="handleBack">返回</el-button>
        <loading-btn v-if="showControl" type="primary" @click="handleSubmit">保存</loading-btn>
        <loading-btn v-if="isAdd" type="primary" @click="handleSubmitNotBack">保存不返回</loading-btn>
      </template>
    </my-form>
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';
import LoadingBtn from '@/components/LoadingBtn';
import { getMenuTree, addMenu, editMenu, getMenuInfo } from '@/api/menu';
import TreeSelect from '@/views/common/TreeSelect';
import DictSelect from '@/views/common/DictSelect';

export default {
  name: 'MenuManageEdit',
  data() {
    this.getMenuTree = getMenuTree;
    return {
      type: this.$route.query.type,
      id: this.$route.query.id,
      submitForm: {
        name: null,
        code: null,
        parentId: null,
        type: 1,
        sort: 10,
      },
      submitRules: {
        name: [
          { required: true, message: '菜单名称不能为空' },
        ],
        type: [
          { required: true, message: '类型不能为空' },
        ],
        code: [
          { required: true, message: '编号不能为空' },
        ],
        sort: [
          { required: true, message: '排序不能为空' },
        ],
      },
    };
  },
  computed: {
    showControl() {
      return this.isAdd || this.isEdit;
    },
    isAdd() {
      return this.type === 'add';
    },
    isEdit() {
      return this.type === 'edit';
    },
    isView() {
      return this.type === 'view';
    },
  },
  mounted() {
    if (this.isEdit || this.isView) {
      this.getDetail();
    }
    if (this.isAdd) {
      const parentId = this.$route.query.parentId;
      if (parentId) {
        this.submitForm.parentId = parentId;
      }
    }
  },
  methods: {
    async getDetail() {
      const { data } = await getMenuInfo({ id: this.id });
      this.submitForm = {
        ...this.submitForm,
        ...data,
      };
    },
    handleBack() {
      this.$router.push({
        name: 'MenuManage',
      });
    },
    async handleSubmitNotBack() {
      await this.handleSubmit(false);
      this.submitForm.name = null;
      this.submitForm.code = null;
    },
    async handleSubmit(needBack = true) {
      const valid = this.$refs.form.validate();
      if (!valid) return;

      let code;
      const data = {
        ...this.submitForm,
      };
      if (this.isAdd) {
        ({ code } = await addMenu(data));
      }
      if (this.isEdit) {
        data.id = this.id;
        ({ code } = await editMenu(data));
      }

      if (code === 200) {
        this.$message.success(`${this.isAdd ? '新增' : this.isEdit ? '修改' : '操作'}成功`);
        if (needBack) {
          this.handleBack();
        }
      }
    },
  },
  components: {
    MyForm,
    MyFormItem,
    LoadingBtn,
    TreeSelect,
    DictSelect,
  },
};
</script>

<style scoped lang="scss">
</style>
