<template>
  <div class="qrcode">
    <div v-if="code" class="content">
      <canvas ref="canvas" />
      <a v-if="download" class="link" @click="handleDownload">点击下载</a>
    </div>
    <div v-else>暂无</div>
  </div>
</template>

<script>
import QRCode from 'qrcode';
import { dateFormat } from '@/utils/date';

export default {
  name: 'QRCode',
  props: {
    download: {
      type: Boolean,
      default: true,
    },
    downloadName: {
      type: Boolean,
    },
    // 下载的图片类型, 可选类型：image/png, image/jpeg, image/webp.
    downloadType: {
      type: String,
      default: 'image/png',
    },
    code: {
      type: String,
    },
    width: {
      type: Number,
      default: 100,
    },
    margin: {
      type: Number,
      default: 0,
    },
    // 只支持 hex 格式颜色
    color: {
      type: String,
      default: '#000000ff',
    },
    // 只支持 hex 格式颜色
    backgroundColor: {
      type: String,
      default: '#ffffffff',
    },
  },
  data() {
    return {
      imgUrl: null,
    };
  },
  mounted() {
    this.initQRCode();
  },
  methods: {
    initQRCode() {
      if (this.code) {
        QRCode.toDataURL(this.$refs.canvas, this.code, {
          width: this.width,
          margin: this.margin,
          type: this.downloadType,
          color: {
            dark: this.color,
            light: this.backgroundColor,
          },
        }, (err, url) => {
          if (err) return;

          this.imgUrl = url;
        });
      }
    },
    handleDownload() {
      const a = document.createElement('a');
      a.download = this.downloadName || dateFormat('yyyyMMddhhmmss', new Date());
      a.href = this.imgUrl;
      a.dispatchEvent(new MouseEvent('click'));
    },
  },
  watch: {
    code() {
      this.initQRCode();
    },
  },
};
</script>

<style scoped lang="scss">
.qrcode {
  .content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .link {
      margin-top: 5px;
    }
  }
}
</style>
