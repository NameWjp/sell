import NProgress from 'nprogress'; // progress bar
import router from './router';
import store from './store';
import 'nprogress/nprogress.css'; // progress bar style
import { getToken } from '@/utils/storage'; // get token from cookie
import getPageTitle from '@/utils/get-page-title';
import { resetRouter } from '@/router';
import { Message } from 'element-ui';

NProgress.configure({ showSpinner: false }); // NProgress Configuration

const whiteList = ['/login']; // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  // start progress bar
  NProgress.start();

  // set page title
  document.title = getPageTitle(to.meta.title);

  // determine whether the user has logged in
  // const hasToken = getToken();
  const hasToken = true;

  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' });
      NProgress.done();
    } else {
      const hasRoles = store.getters.roles && store.getters.roles.length > 0;
      if (hasRoles) {
        next();
      } else {
        try {
          // get user info
          // const { roles } = await store.dispatch('user/getInfo');

          // if (roles.length) {
          //   // generate accessible routes map based on roles
          //   const accessRoutes = await store.dispatch('permission/generateRoutes', roles);

          //   // 添加router前重置掉之前的router
          //   resetRouter();
          //   router.addRoutes(accessRoutes);

          //   // hack method to ensure that addRoutes is complete
          //   // set the replace: true, so the navigation will not leave a history record
          //   next({ path: to.fullPath, replace: true });
          // } else {
          //   store.dispatch('user/resetToken');
          //   Message.warning('该账号未分配角色，请联系管理员分配角色');
          // }
          // 模拟权限
          store.commit('user/SET_ROLES', ['test']);
          store.dispatch('dict/loadDictList', true);
          store.dispatch('organization/loadOrganizationTree', true);
          const accessRoutes = await store.dispatch('permission/generateRoutes', ['test']);
          resetRouter();
          router.addRoutes(accessRoutes);
          next({ path: to.fullPath, replace: true });
        } catch (error) {
          console.error(error);
          NProgress.done();
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next();
    } else {
      next(`/login?redirect=${to.fullPath}`);
      NProgress.done();
    }
  }
});

router.afterEach(() => {
  NProgress.done();
});
