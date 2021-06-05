<template>
  <div class="main-layout">
    <my-form ref="form" type="submit" :model="submitForm" :rules="submitRules">
      <my-form-item label="父级" prop="parentId">
        <tree-select ref="tree" v-model="submitForm.parentId" :tree-request="getSubTree" :disabled="isView || isEdit" @input="handleTreeChange" />
      </my-form-item>
      <my-form-item label="编号" prop="dictCode">
        <el-input v-model="submitForm.dictCode" :disabled="disabledDictCode" />
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
import { addDict, getSubTree, getDictInfo, editDiet } from '@/api/dict';
import TreeSelect from '@/views/common/TreeSelect';

export default {
  name: 'DictManageEdit',
  data() {
    this.getSubTree = getSubTree;
    return {
      type: this.$route.query.type,
      id: this.$route.query.id,
      // 当前新增的层级
      level: null,
      submitForm: {
        parentId: null,
        dictCode: null,
      },
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
    disabledDictCode() {
      return !this.isAdd || this.level >= 3;
    },
  },
  mounted() {
    if (this.isEdit || this.isView) {
      this.getDetail();
    }
    if (this.isAdd) {
      const parentId = this.$route.query.parentId;
      const dictCode = this.$route.query.dictCode;
      if (parentId) {
        this.submitForm.parentId = parentId;
      }
      if (dictCode) {
        this.submitForm.dictCode = dictCode;
      }
      this.level = this.$route.query.level;
    }
  },
  methods: {
    async getDetail() {
      const { data } = await getDictInfo({ id: this.id });
      this.submitForm = {
        ...this.submitForm,
        ...data,
      };
    },
    handleBack() {
      this.$router.push({
        name: 'DictManage',
      });
    },
    handleTreeChange(id) {
      const row = this.$refs.tree.getTreeNodeById(id);
      if (row.parentIds) {
        this.level = row.parentIds.split(',').length + 1;
        if (this.level === 3) {
          this.submitForm.dictCode = row.dictCode;
        } else {
          this.submitForm.dictCode = null;
        }
      } else {
        this.level = 1;
        this.submitForm.dictCode = null;
      }
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
        data.id = this.id;
        ({ code } = await editDiet(data));
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
