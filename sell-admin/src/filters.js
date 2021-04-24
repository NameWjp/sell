import Vue from 'vue';
import { dateFormat, timeSince } from '@/utils/date';
import Store from '@/store';

Vue.filter('datetime', (value) => {
  if (!value) {
    return '-';
  }
  return dateFormat('yyyy-MM-dd hh:mm:ss', new Date(value));
});

Vue.filter('date', (value) => {
  if (!value) {
    return '-';
  }
  return dateFormat('yyyy-MM-dd', new Date(value));
});

Vue.filter('time', (value) => {
  if (!value) {
    return '-';
  }
  return dateFormat('hh:mm:ss', new Date(value));
});

Vue.filter('month', (value) => {
  if (!value) {
    return '';
  }
  return dateFormat('yyyy-MM', new Date(value));
});

Vue.filter('timeSince', (value) => {
  if (!value) {
    return '';
  }
  return timeSince(new Date(value));
});

Vue.filter('dict', (value, dictCode) => {
  const map = Store.state.dict.dictMap[dictCode];
  if (map && map[value]) {
    return map[value];
  }
  return '未知';
});
