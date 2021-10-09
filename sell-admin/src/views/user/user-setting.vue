<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="120px">
    <el-tabs tab-position="left">
      <el-tab-pane label="账户信息">
        <div class="title">账户信息</div>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item label="账号">
              <el-input v-model="form.username" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item label="所属部门">
              <el-input v-model="form.organName" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item label="所属角色">
              <el-input v-model="form.roleNames" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item>
              <el-button @click="handleClose">关闭</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="修改密码">
        <div class="title">修改密码</div>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item label="账号" required>
              <el-input v-model="form.username" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input v-model="form.oldPassword" type="password" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item label="新密码" prop="password">
              <el-input v-model="form.password" type="password" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item label="确认新密码" prop="validatePass">
              <el-input v-model="form.validatePass" type="password" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="SL.gutter">
          <el-col v-bind="SL.span">
            <el-form-item>
              <el-button @click="handleClose">关闭</el-button>
              <el-button type="primary" @click="handleSubmit">提交</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </el-form>
</template>

<script>
import { SL } from '@/utils/form-layout';
import { updatePassword } from '@/api/user';

export default {
  name: 'UserSetting',
  data() {
    this.SL = SL;
    this.validatorPass = (rule, value, callback) => {
      if (!value) {
        callback('新密码不能为空');
        return;
      }
      if (this.form.validatePass !== '') {
        this.$refs.form.validateField('validatePass');
      }
      callback();
    };
    this.validatorCheckPass = (rule, value, callback) => {
      if (value !== this.form.password) {
        callback('两次密码输入不一致');
      }
      callback();
    };
    return {
      form: {
        username: '',
        organName: '',
        roleNames: '',
        oldPassword: '',
        password: '',
        validatePass: '',
      },
      rules: {
        oldPassword: [{ required: true, trigger: 'blur', message: '旧密码不能为空' }],
        password: [{ required: true, trigger: 'blur', validator: this.validatorPass }],
        validatePass: [{ required: true, trigger: 'blur', validator: this.validatorCheckPass }],
      },
    };
  },
  created() {
    const { username, organName, roleNames } = this.$store.state.user.userInfo;
    this.form = {
      username,
      organName,
      roleNames,
    };
  },
  methods: {
    async handleClose() {
      const views = await this.$store.dispatch('tagsView/delView', this.$route);
      const latestView = views.visitedViews.slice()[0];
      if (latestView) {
        this.$router.push(latestView);
      } else {
        this.$router.push('/');
      }
    },
    handleSubmit() {
      this.$refs.form.validate(async (valid) => {
        if (!valid) return;
        const { oldPassword, password } = this.form;
        const { code } = await updatePassword({ oldPassword, newPassword: password });
        if (code === 200) {
          this.$message.success('修改成功');
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
.title {
  font-size: 20px;
  margin: 30px;
  padding-left: 30px;
}
/deep/ .el-tabs__item {
  padding: 0 40px;
}
</style>
