<template>
  <div class="main-layout">
    <my-form ref="form" type="submit" :model="submitForm" :rules="submitRules">
      <my-form-item label="父级" prop="parentId">
        <tree-select v-model="submitForm.parentId" :tree-request="getSubTree" :disabled="isView" />
      </my-form-item>
      <my-form-item label="编号" prop="dictCode">
        <el-input v-model="submitForm.dictCode" :disabled="isView" />
      </my-form-item>
      <my-form-item label="名称" prop="dictName">
        <el-input v-model="submitForm.dictName" :disabled="isView" />
      </my-form-item>
      <my-form-item label="值" prop="dictValue">
        <el-input v-model="submitForm.dictValue" :disabled="isView" />
      </my-form-item>
      <template slot="control">
        <el-button @click="handleBack">返回</el-button>
        <loading-btn type="primary" @click="handleSubmit">保存</loading-btn>
      </template>
    </my-form>
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';
import LoadingBtn from '@/components/LoadingBtn';
import { addDict, getSubTree } from '@/api/dict';
import TreeSelect from '@/views/common/TreeSelect';

export default {
  name: 'DictManageEdit',
  data() {
    this.getSubTree = getSubTree;
    return {
      type: this.$route.query.type,
      id: this.$route.query.id,
      submitForm: {},
      submitRules: {
        dictCode: [
          { required: true, message: '编号不能为空' },
        ],
        dictName: [
          { required: true, message: '名称不能为空' },
        ],
        dictValue: [
          { required: true, message: '值不能为空' },
        ],
      },
    };
  },
  computed: {
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
  },
  methods: {
    async getDetail() {
      // const { data } = await getUserInfoById({ id: this.id });
      // this.submitForm = {
      //   ...this.submitForm,
      //   ...data,
      // };
    },
    handleBack() {
      this.$router.push({
        name: 'DictManage',
      });
    },
    async handleSubmit() {
      const valid = this.$refs.form.validate();
      if (!valid) return;

      let code;
      const data = {
        ...this.submitForm,
      };
      if (this.isAdd) {
        ({ code } = await addDict(data));
      }
      if (this.isEdit) {
        // data.id = this.id;
        // ({ code } = await editUser(data));
      }

      if (code === 200) {
        this.$message.success(`${this.isAdd ? '新增' : this.isEdit ? '修改' : '操作'}成功`);
        this.handleBack();
      }
    },
  },
  components: {
    MyForm,
    MyFormItem,
    LoadingBtn,
    TreeSelect,
  },
};
</script>

<style scoped lang="scss">
</style>
