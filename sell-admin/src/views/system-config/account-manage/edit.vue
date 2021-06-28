<template>
  <div class="main-layout">
    <my-form ref="form" type="submit" :model="submitForm" :rules="submitRules">
      <my-form-item label="用户名" prop="username">
        <el-input v-model="submitForm.username" :disabled="isView" />
      </my-form-item>
      <my-form-item label="是否启用" prop="isEnable">
        <dict-select v-model="submitForm.isEnable" :disabled="isView" dict-code="is_enable" />
      </my-form-item>
      <my-form-item label="角色" prop="roleIds">
        <list-select v-model="submitForm.roleIds" multiple :list-request="getRoleList" />
      </my-form-item>
      <my-form-item label="组织机构" prop="organId">
        <organ-tree-select v-model="submitForm.organId" />
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
import DictSelect from '@/views/common/DictSelect';
import ListSelect from '@/views/common/ListSelect';
import OrganTreeSelect from '@/views/common/OrganTreeSelect';
import { addUser, getUserInfoById, editUser } from '@/api/user';
import { getRoleList } from '@/api/role';

export default {
  name: 'AccountManageEdit',
  data() {
    this.getRoleList = getRoleList;
    return {
      type: this.$route.query.type,
      id: this.$route.query.id,
      submitForm: {
        isEnable: 1,
        roleIds: [],
      },
      submitRules: {
        username: [
          { required: true, message: '用户名称不能为空' },
          { validator: validateUsername },
        ],
        isEnable: [
          { required: true, message: '是否启用不能为空' },
        ],
        roleIds: [
          { required: true, message: '角色不能为空' },
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
      const { data } = await getUserInfoById({ id: this.id });
      this.submitForm = {
        ...this.submitForm,
        ...data,
      };
    },
    handleBack() {
      this.$router.push({
        name: 'AccountManage',
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
        ({ code } = await addUser(data));
      }
      if (this.isEdit) {
        data.id = this.id;
        ({ code } = await editUser(data));
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
    DictSelect,
    ListSelect,
    OrganTreeSelect,
  },
};
</script>
    MyFormItem

<style scoped lang="scss">
</style>
