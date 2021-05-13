/* eslint-disable no-undef */
import axios from 'axios';
import { Message } from 'element-ui';
import store from '@/store';
import { setLocalToken, removeLocalToken, getToken } from '@/utils/storage';
import router from '@/router';
import Vue from 'vue';

const baseURL = PROXY_BASE_URL;
let refreshing = false;
let expiredAt = 0;

const instance = axios.create({
  baseURL,
  headers: {
    common: {
      'X-Requested-With': 'XMLHttpRequest',
    },
  },
});

// token操作
function setToken(newToken, expiredDate) {
  instance.defaults.headers.common.Authorization = newToken;
  expiredAt = expiredDate;
  setLocalToken(newToken, expiredDate);
}

function clearToken() {
  delete instance.defaults.headers.common.Authorization;
  removeLocalToken();
}

async function refreshToken() {
  if (refreshing) {
    return;
  }
  refreshing = true;
  await store.dispatch('user/refreshToken');
  refreshing = false;
}

function showResponseError(response) {
  const { data } = response;
  let msg = '未知错误';
  const { message = '' } = data;

  if (message) {
    Message.warning(message);
    return;
  }

  switch (response.code) {
    case 200:
      if (data.code !== 200 && (data.message || data.msg)) {
        msg = data.message || data.msg;
      }
      break;
    case 403:
      msg = '403 没有访问权限';
      break;
    case 404:
      msg = '404 资源不见了';
      break;
    case 405:
      msg = '405 请求的姿势不对';
      break;
    case 408:
      msg = '408 请求超时';
      break;
    case 413:
      msg = '413 文件太大';
      break;
    case 422:
      msg = '输入的内容有误';
      break;
    case 500:
      msg = '500 服务器错误，请联系开发';
      break;
    case 502:
      msg = '502 Bad Gateway';
      break;
    case 503:
      msg = '503 Service Unavailable';
      break;
    case 504:
      msg = '504 Gateway Timeout';
      break;
    default:
      msg = '未知错误';
  }
  Message.warning(msg);
}

// 请求拦截
instance.interceptors.request.use((config) => {
  if (expiredAt) {
    const now = new Date();
    // 刷新快过期(还剩1小时)的token
    if (expiredAt > now && expiredAt - now < 3600000) {
      refreshToken();
    }
  }
  return config;
});
// 响应拦截
instance.interceptors.response.use(async (response) => {
  const { data = {} } = response;
  const { code = 200, message = '用户未登录' } = data;
  if (code !== 200 && code !== 401) {
    showResponseError(response);
  }
  if (code === 401) {
    await store.dispatch('user/resetToken');
    router.push(`/login?redirect=${router.currentRoute.fullPath}`);
    Message.warning(message);
  }
  return data;
}, (error) => {
  // 错误预处理
  if (error.response) {
    const resp = error.response;
    showResponseError(resp);
  } else {
    Message.error('请求失败，请检查网络');
  }
  // 关闭loading
  Vue.prototype.$closeLoading();
  return Promise.reject(error);
});

function encodeParams(path, params = {}) {
  let str = `${path}?`;
  for (const [key, value] of Object.entries(params)) {
    if ((key || key === 0) && (value || value === 0)) {
      if (value instanceof Date) {
        str += `${encodeURIComponent(key)}=${encodeURIComponent(value.toJSON())}&`;
      } else {
        str += `${encodeURIComponent(key)}=${encodeURIComponent(value)}&`;
      }
    }
  }
  return str.slice(0, str.length - 1);
}

function open(url, params = {}) {
  const path = `${baseURL}${url}`;
  const paramsObj = { ...params, Authorization: getToken() };
  window.open(encodeParams(path, paramsObj));
}

function redirect(url, params = {}) {
  const path = `${baseURL}${url}`;
  const paramsObj = { ...params, Authorization: getToken() };
  window.location.href = encodeParams(path, paramsObj);
}

const {
  get, post, put, head, delete: del,
} = instance;

export { setToken, clearToken, get, post, put, del, head, open, redirect, encodeParams };
