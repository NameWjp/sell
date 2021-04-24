import Vue from 'vue';
import Store from '@/store';

export function checkPermissions(authority) {
  const currentAuthority = Store.state.user.roles;
  // 不需要权限
  if (!authority) {
    return true;
  }
  // 数组处理
  if (Array.isArray(authority)) {
    for (let i = 0; i < authority.length; i += 1) {
      if (!currentAuthority.includes(authority[i])) {
        return false;
      }
    }
    return true;
  }

  // string 处理
  if (typeof authority === 'string') {
    return !!currentAuthority.includes(authority);
  }
  return false;
}

Vue.directive('permission', {
  inserted(el, binding) {
    const authority = binding.value;
    if (!checkPermissions(authority)) {
      el.parentNode.removeChild(el);
    }
  },
});
