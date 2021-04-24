<template>
  <div class="upload-box">
    <div v-if="status === 'success' && value.length" :style="{ width: width }" class="video-wrap">
      <video ref="video" class="video" controls :src="value[0].url" />
      <div v-if="!disabled" class="video-control">
        <el-button type="primary" @click="handleScreenshot">截图</el-button>
        <el-button type="primary" @click="handleClear">重新上传</el-button>
      </div>
    </div>
    <el-progress v-if="status === 'uploading'" type="circle" :percentage="percent" />
    <el-upload
      v-if="status === 'ready'"
      action=""
      :disabled="disabled"
      list-type="picture-card"
      :show-file-list="false"
      :http-request="customUploadFile"
      :on-success="handleSuccess"
      :on-progress="handleProgress"
      :limit="1"
      :on-exceed="handleExceed"
      :before-upload="beforeUpload"
      :file-list="fileList"
    >
      <i class="el-icon-plus avatar-uploader-icon" />
    </el-upload>
  </div>
</template>

<script>
import { customUploadFile } from '@/api/upload';
import { dateFormat } from '@/utils/date';
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
    disabled: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '700px',
    },
  },
  data() {
    this.customUploadFile = customUploadFile;
    return {
      fileList: [],
      // ready, uploading, success
      status: 'ready',
      percent: 0,
      // 用于截图
      canvas: null,
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
     * file: {
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
      this.status = 'success';
      this.changeValue([...this.resFileList]);
    },
    handleClear() {
      this.status = 'ready';

      this.changeValue([]);
    },
    handleScreenshot() {
      if (!this.canvas) {
        this.canvas = document.createElement('canvas');
      }
      // 获取属性(注意这里要使用 videoWidth 和 videoHeight)
      const videoDom = this.$refs.video;
      const width = videoDom.videoWidth;
      const height = videoDom.videoHeight;

      // 设置 canvas 的尺寸
      this.canvas.width = width;
      this.canvas.height = height;

      // 获取截图 url
      const canvasCtx = this.canvas.getContext('2d');
      canvasCtx.drawImage(videoDom, 0, 0, width, height, 0, 0, width, height);
      const url = this.canvas.toDataURL('image/png');

      // 下载图片
      const a = document.createElement('a');
      a.download = dateFormat('yyyyMMddhhmmss', new Date());
      a.href = url;
      a.dispatchEvent(new MouseEvent('click'));
    },
    handleProgress({ percent }) {
      this.percent = percent;
    },
    beforeUpload(file) {
      if (file.type.split('/')[0] !== 'video') {
        this.$message.error('只能上传视频');
        return false;
      }

      this.status = 'uploading';

      return true;
    },
    handleExceed() {
      this.$message.error(`只能上传 1 个视频`);
    },
  },
  watch: {
    value: {
      handler(files) {
        if (!isEqual(files, this.resFileList)) {
          this.fileList = files.map(item => ({ response: item, url: item.url, name: item.name }));

          if (files.length) {
            this.status = 'success';
          } else {
            this.status = 'ready';
          }
        }
      },
      immediate: true,
    },
  },
};
</script>

<style scoped lang="scss">
.upload-box {
  .video-wrap {
    .video {
      display: block;
      width: 100%;
    }
    .video-control {
      margin-top: 20px;
      text-align: center;
    }
  }
}
</style>
