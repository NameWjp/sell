<template>
  <div class="upload-container">
    <el-button
      :style="{background:color,borderColor:color}"
      icon="el-icon-upload"
      size="mini"
      type="primary"
      :disabled="disabled"
      @click="handleSetDialogStatus(true)"
    >
      上传
    </el-button>
    <el-dialog
      :visible.sync="showDialog"
      title="上传图片"
    >
      <upload-img ref="uploadImg" v-model="imgList" />
      <div style="text-align: right">
        <el-button @click="handleSetDialogStatus(false)">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import UploadImg from '@/views/common/UploadImg';

export default {
  name: 'EditorSlideUpload',
  props: {
    disabled: {
      type: Boolean,
      default: false,
    },
    color: {
      type: String,
      default: '#1890ff',
    },
  },
  data() {
    return {
      showDialog: false,
      imgList: [],
    };
  },
  methods: {
    handleSetDialogStatus(status) {
      this.showDialog = status;
    },
    handleSubmit() {
      if (!this.$refs.uploadImg.checkAllSuccess()) {
        this.$message('请等待图片上传完成后再点保存');
        return;
      }
      this.$emit('successCBK', this.imgList);
      this.handleSetDialogStatus(false);
      this.imgList = [];
    },
  },
  components: {
    UploadImg,
  },
};
</script>

<style lang="scss" scoped>
</style>
