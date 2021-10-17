<template>
  <div class="main-layout">
    <my-form ref="form" type="submit" :model="submitForm" :rules="submitRules">
      <my-form-item label="名称" prop="name">
        <el-input v-model="submitForm.name" :disabled="isView" />
      </my-form-item>
      <my-form-item label="编码" prop="code">
        <el-input v-model="submitForm.code" :disabled="isView" />
      </my-form-item>
      <my-form-item label="排序" prop="sort">
        <el-input v-model="submitForm.sort" :disabled="isView" />
      </my-form-item>
      <template slot="control">
        <el-button @click="handleBack">返回</el-button>
        <loading-btn v-if="showControl" type="primary" @click="handleSubmit">保存</loading-btn>
      </template>
    </my-form>
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';
import LoadingBtn from '@/components/LoadingBtn';
import { addCategory, editCategory, getCategoryInfoById } from '@/api/category';
import { toOnlyInteger } from '@/utils/transform';
import { validatePositive, validateNotZero } from '@/utils/validator';

export default {
  name: 'ProductCategoryEdit',
  data() {
    return {
      type: this.$route.query.type,
      id: this.$route.query.id,
      submitForm: {},
      submitRules: {
        name: [
          { required: true, message: '名称不能为空' },
        ],
        code: [
          { required: true, message: '编码不能为空' },
        ],
        sort: [
          { required: true, message: '排序不能为空' },
          { type: 'number', transform: toOnlyInteger, message: '排序只能为整数' },
          { validator: validatePositive },
          { validator: validateNotZero },
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
  },
  methods: {
    async getDetail() {
      const { data } = await getCategoryInfoById({ id: this.id });
      this.submitForm = {
        ...this.submitForm,
        ...data,
      };
    },
    handleBack() {
      this.$router.push({
        name: 'ProductCategory',
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
        ({ code } = await addCategory(data));
      }
      if (this.isEdit) {
        data.id = this.id;
        ({ code } = await editCategory(data));
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
  },
};
</script>

<style scoped lang="scss">
</style>
