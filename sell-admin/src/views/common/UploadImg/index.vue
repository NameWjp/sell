<template>
  <div class="upload-box" :class="{hide: max === value.length}">
    <el-upload
      action=""
      :disabled="disabled"
      list-type="picture-card"
      :on-remove="handleRemove"
      :http-request="customUploadImg"
      :on-preview="handlePictureCardPreview"
      :on-success="handleSuccess"
      :before-upload="beforeUpload"
      :file-list="fileList"
      :multiple="multiple"
      :limit="max"
      :on-exceed="handleExceed"
    >
      <i class="el-icon-plus avatar-uploader-icon" />
    </el-upload>
    <el-dialog
      title="预览"
      :visible.sync="showDialog"
      append-to-body
    >
      <img style="display:block;width:100%;" :src="previewImgUrl">
    </el-dialog>
  </div>
</template>

<script>
import { customUploadImg } from '@/api/upload';
import { isEqual } from 'lodash-es';

export default {
  model: {
    props: 'value',
    event: 'change',
  },
  props: {
    value: {
      type: Array,
      default: () => [],
    },
    multiple: {
      type: Boolean,
      default: true,
    },
    max: {
      type: Number,
      default: 1,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    maxSize: {
      type: Number,
      default: 2,
    },
  },
  data() {
    this.customUploadImg = customUploadImg;
    return {
      fileList: [],
      // 用于判断文件是否上传成功的状态
      fileSuccessMap: {},
      previewImgUrl: '',
      showDialog: false,
    };
  },
  computed: {
    resFileList() {
      return this.fileList.filter(_ => _.status === 'success').map(_ => _.response);
    },
    _ElFormItem() {
      let parent = this.$parent;
      while (parent) {
        if (parent.$options.componentName !== 'ElFormItem') {
          parent = parent.$parent;
        } else {
          return parent;
        }
      }
      return false;
    },
  },
  methods: {
    /**
     * file格式
     * file: {
     *  name: xxx,
     *  url: xxx, 必填
     * }
    */
    changeValue(value) {
      this.$emit('change', value);
      if (this._ElFormItem) {
        this._ElFormItem.$emit('el.form.change', value);
      }
    },
    handleSuccess(file, obj, fileList) {
      this.fileList = fileList;
      this.fileSuccessMap[obj.uid] = true;
      this.changeValue([...this.resFileList]);
    },
    handleRemove(file) {
      const { response: { url } = {}, status } = file;
      if (status === 'success') {
        const removeIndex = this.fileList.findIndex(({ response = {} }) => response.url === url);
        this.fileList.splice(removeIndex, 1);
        delete this.fileSuccessMap[file.uid];
        this.changeValue([...this.resFileList]);
      }
    },
    beforeUpload(file) {
      if (file.type.split('/')[0] !== 'image') {
        this.$message.error('只能上传图片');
        return false;
      }
      if (file.size > this.maxSize * 1024 * 1024) {
        this.$message.error(`文件尺寸不能大于${this.maxSize}MB`);
        return false;
      }

      // 更新 fileMap
      const fileSuccessMap = { ...this.fileSuccessMap };
      fileSuccessMap[file.uid] = false;
      this.fileSuccessMap = fileSuccessMap;

      // beforeUpload 如果返回的是 Promise 且解析后文件为 file 格式，则会使用新的 file 上传
      // 图片压缩可在此处进行
      return true;
    },
    handleExceed() {
      this.$message.error(`最多只能上传${this.max}图片`);
    },
    handlePictureCardPreview({ response = {} }) {
      this.previewImgUrl = response.url;
      this.showDialog = true;
    },
    checkAllSuccess() {
      for (const uid in this.fileSuccessMap) {
        if (!this.fileSuccessMap[uid]) {
          return false;
        }
      }
      return true;
    },
  },
  watch: {
    value: {
      handler(files) {
        if (!isEqual(files, this.resFileList)) {
          this.fileList = files.map(item => ({ response: item, url: item.url, name: item.name }));
        }
      },
      immediate: true,
    },
  },
};
</script>

<style scoped lang="scss">
.upload-box {
  .img-box {
    height: 100%;
    .img {
      display: block;
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }
}
.upload-box.hide {
  /deep/ .el-upload {
    display: none;
  }
}
</style>
