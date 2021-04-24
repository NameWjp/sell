import { TOKEN_KEY, TOKEN_EXPIRED_DATE, THEME_CLASS } from '@/constant';
import { hasUser, getTheme } from '@/utils/storage';
import { setToken } from '@/utils/request';
import { MessageBox, Loading } from 'element-ui';
import Vue from 'vue';

let loading = null;

// 加载过期时间和token
const savedToken = localStorage.getItem(TOKEN_KEY);
const savedExpiredAt = localStorage.getItem(TOKEN_EXPIRED_DATE);
if (hasUser()) {
  // 设置未过期的token
  setToken(savedToken, savedExpiredAt);
}

// 加载缓存样式
const newStyle = getTheme().style;
if (newStyle) {
  const styleTag = document.createElement('style');
  styleTag.setAttribute('id', THEME_CLASS);
  styleTag.innerText = newStyle;
  document.head.appendChild(styleTag);
}

// vue 全局方法
Vue.prototype.$confirmMsg = async function({ message, onOk }) {
  try {
    await MessageBox({
      title: '提示',
      type: 'warning',
      message: message || '此操作不可逆，是否确认？',
      showCancelButton: true,
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      beforeClose: async (action, instance, done) => {
        if (action === 'confirm') {
          const ret = onOk();
          if (ret && ret.then) {
            instance.confirmButtonLoading = true;
            try {
              await ret;
            } catch (e) {}
            instance.confirmButtonLoading = false;
            done();
          }
        } else {
          instance.confirmButtonLoading = false;
          done();
        }
      },
    });
  // eslint-disable-next-line no-empty
  } catch (e) {}
};

Vue.prototype.$showLoading = function(info = {}) {
  loading = Loading.service({
    lock: true,
    text: 'Loading',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.7)',
    ...info,
  });
};

Vue.prototype.$closeLoading = function() {
  if (loading && typeof loading.close === 'function') {
    loading.close();
  }
};
