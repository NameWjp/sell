<template>
  <div class="main-layout">
    <my-form ref="form" type="submit" :model="submitForm" :rules="submitRules">
      <my-form-item label="用户名" prop="username">
        <el-input v-model="submitForm.username" />
      </my-form-item>
      <my-form-item label="是否启用" prop="isEnable">
        <el-select v-model="submitForm.isEnable" style="width: 100%">
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="2" />
        </el-select>
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
import { validateUsername } from '@/utils/validator';
import { addUser } from '@/api/user';

export default {
  name: 'AccountManageEdit',
  data() {
    return {
      type: this.$route.query.type,
      submitForm: {
        isEnable: 1,
      },
      submitRules: {
        username: [
          { required: true, message: '用户名称不能为空' },
          { validator: validateUsername },
        ],
        isEnable: [
          { required: true, message: '是否启用不能为空' },
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
  methods: {
    handleBack() {
      this.$router.push({
        name: 'AccountManage',
      });
    },
    async handleSubmit() {
      const valid = this.$refs.form.validate();
      if (!valid) return;

      const { code } = await addUser(this.submitForm);
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
