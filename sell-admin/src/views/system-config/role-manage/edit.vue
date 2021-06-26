<template>
  <div class="main-layout">
    <el-form ref="form" :model="submitForm" label-width="100px" :rules="submitRules">
      <el-row :gutter="40">
        <el-col :span="12">
          <el-row>
            <el-col :span="20">
              <el-form-item label="角色名称" prop="name">
                <el-input v-model="submitForm.name" :disabled="isView" />
              </el-form-item>
            </el-col>
            <el-col :span="20">
              <el-form-item label="角色描述" prop="description">
                <el-input
                  v-model="submitForm.description"
                  type="textarea"
                  :autosize="{ minRows: 2 }"
                  :disabled="isView"
                />
              </el-form-item>
            </el-col>
            <el-col :span="20">
              <div class="form-control">
                <el-button @click="handleBack">返回</el-button>
                <loading-btn type="primary" @click="handleSubmit">保存</loading-btn>
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12">
          <el-form-item label="权限配置" prop="privilegeIds">
            <el-tree
              ref="tree"
              node-key="id"
              :data="privilegeTree"
              show-checkbox
              :render-after-expand="false"
              :props="treeProps"
              @check-change="handleCheckTree"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import LoadingBtn from '@/components/LoadingBtn';
import { addRole, getRoleInfoById, editRole } from '@/api/role';
import { getMenuTree } from '@/api/menu';
import { getLeafKeys } from '@/utils/tree';

export default {
  name: 'RoleManageEdit',
  data() {
    return {
      type: this.$route.query.type,
      id: this.$route.query.id,
      privilegeTree: [],
      treeProps: {
        children: 'children',
        label: 'name',
      },
      submitForm: {
        privilegeIds: [],
      },
      submitRules: {
        name: [
          { required: true, message: '角色名称不能为空' },
        ],
        privilegeIds: [
          { required: true, message: '权限配置不能为空' },
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
  async mounted() {
    await this.getPrivilegeTree();
    if (this.isEdit || this.isView) {
      this.getDetail();
    }
  },
  methods: {
    handleCheckTree() {
      this.submitForm.privilegeIds = this.$refs.tree.getCheckedNodes(false, true).map(({ id }) => id);
    },
    async getPrivilegeTree() {
      const { data } = await getMenuTree();
      this.privilegeTree = data;
    },
    async getDetail() {
      const { data } = await getRoleInfoById({ id: this.id });

      const { privilegeIds, ...form } = data;

      if (privilegeIds && privilegeIds.length) {
        this.$nextTick(() => {
          const leafKeys = getLeafKeys(this.privilegeTree);
          const keys = privilegeIds.filter(id => leafKeys.includes(id));
          this.$refs.tree.setCheckedKeys(keys, true);
        });
      }

      this.submitForm = {
        ...this.submitForm,
        ...form,
      };
    },
    handleBack() {
      this.$router.push({
        name: 'RoleManage',
      });
    },
    async handleSubmit() {
      let valid;
      this.$refs.form.validate((val) => { valid = val; });
      if (!valid) return;

      let code;
      const data = {
        ...this.submitForm,
      };

      if (this.isAdd) {
        ({ code } = await addRole(data));
      }
      if (this.isEdit) {
        data.id = this.id;
        ({ code } = await editRole(data));
      }

      if (code === 200) {
        this.$message.success(`${this.isAdd ? '新增' : this.isEdit ? '修改' : '操作'}成功`);
        this.handleBack();
      }
    },
  },
  components: {
    LoadingBtn,
  },
};
</script>

<style scoped lang="scss">
</style>
