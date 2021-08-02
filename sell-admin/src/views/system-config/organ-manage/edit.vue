<template>
  <div class="main-layout">
    <my-form ref="form" type="submit" :model="submitForm" :rules="submitRules">
      <my-form-item label="父级机构" prop="parentId">
        <tree-select ref="tree" v-model="submitForm.parentId" :tree-request="getOrganTree" :disabled="isView" />
      </my-form-item>
      <my-form-item label="机构名称" prop="name">
        <el-input v-model="submitForm.name" :disabled="isView" />
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
import { getOrganTree, addOrgan, editOrgan, getOrganInfo } from '@/api/organ';
import TreeSelect from '@/views/common/TreeSelect';

export default {
  name: 'OrganManageEdit',
  data() {
    this.getOrganTree = getOrganTree;
    return {
      type: this.$route.query.type,
      id: this.$route.query.id,
      submitForm: {
        name: null,
        parentId: null,
      },
      submitRules: {
        name: [
          { required: true, message: '机构名称不能为空' },
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
      const { data } = await getOrganInfo({ id: this.id });
      this.submitForm = {
        ...this.submitForm,
        ...data,
      };
    },
    handleBack() {
      this.$router.push({
        name: 'OrganManage',
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
        ({ code } = await addOrgan(data));
      }
      if (this.isEdit) {
        data.id = this.id;
        ({ code } = await editOrgan(data));
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
