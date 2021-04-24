import Vue from 'vue';
import ElementUI from 'element-ui';

import 'normalize.css/normalize.css'; // A modern alternative to CSS resets
import './styles/element-variables.scss';
import '@/styles/index.scss'; // global css

import App from './App';
import store from './store';
import router from './router';

import '@/icons'; // icon
import '@/permission'; // permission control
import '@/bootstrap'; // 用于初始化应用加载的js
import '@/filters'; // 全局过滤器
import '@/directive'; // 全局指令

Vue.use(ElementUI, { size: 'small' });

Vue.config.productionTip = false;

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
});
