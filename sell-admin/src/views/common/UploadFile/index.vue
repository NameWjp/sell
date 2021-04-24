<template>
  <div class="upload-box" :class="{hide: !multiple && value.length}">
    <el-upload
      action=""
      :disabled="disabled"
      list-type="text"
      :on-remove="handleRemove"
      :http-request="customUploadFile"
      :on-success="handleSuccess"
      :before-upload="beforeUpload"
      :file-list="fileList"
      :multiple="multiple"
      :limit="max"
      :on-exceed="handleExceed"
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <div v-if="tip" slot="tip" class="el-upload__tip">{{ tip }}</div>
    </el-upload>
  </div>
</template>

<script>
import { customUploadFile } from '@/api/upload';
import { isEqual } from 'lodash-es';

export default {
  model: {
    props: 'value',
    event: 'change',
  },
  props: {
    tip: {
      type: String,
      default: '',
    },
    customUploadFile: {
      type: Function,
      default: customUploadFile,
    },
    value: {
      type: Array,
      default: () => [],
    },
    multiple: {
      type: Boolean,
      default: false,
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
    return {
      fileList: [],
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
      this.changeValue([...this.resFileList]);
    },
    handleRemove(file) {
      const { response: { url } = {}, status } = file;
      if (status === 'success') {
        const removeIndex = this.fileList.findIndex(({ response = {} }) => response.url === url);
        this.fileList.splice(removeIndex, 1);
        this.changeValue([...this.resFileList]);
      }
    },
    beforeUpload(file) {
      if (file.size > this.maxSize * 1024 * 1024) {
        this.$message.error(`文件尺寸不能大于${this.maxSize}MB`);
        return false;
      }
      return true;
    },
    handleExceed() {
      this.$message.error(`最多只能上传${this.max}个文件`);
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
</style>
