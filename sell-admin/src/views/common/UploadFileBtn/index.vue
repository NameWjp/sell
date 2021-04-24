<template>
  <div class="upload-file-modal el-btn">
    <el-button type="primary" @click="handleShowDialog">导入</el-button>
    <el-dialog
      title="导入"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :visible.sync="showDialog"
    >
      <div class="tips">点击下载<a class="link" @click="handleExportModal">模板</a></div>
      <upload-file
        v-model="fileList"
        multiple
        :max="5"
        :custom-upload-file="customUploadFile"
      />
    </el-dialog>
  </div>
</template>

<script>
import UploadFile from '@/views/common/UploadFile';

export default {
  props: {
    customUploadFile: Function,
    templateRequest: Function,
    templateFunc: Function,
  },
  data() {
    return {
      showDialog: false,
      fileList: [],
    };
  },
  methods: {
    handleShowDialog() {
      this.showDialog = true;
    },
    async handleExportModal() {
      if (this.templateFunc && typeof this.templateFunc === 'function') {
        this.templateFunc();
        return;
      }
      if (this.templateRequest && typeof this.templateRequest === 'function') {
        const { data: url } = await this.templateRequest();
        window.open(url);
      }
    },
  },
  components: {
    UploadFile,
  },
};
</script>

<style scoped lang="scss">
.upload-file-modal {
  .link {
    color: #409eff;
    text-decoration: underline;
    margin-left: 5px;
  }
  .tips {
    margin-bottom: 10px;
  }
}
</style>
