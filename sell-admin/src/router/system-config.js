import Layout from '@/layout';

export default {
  path: '/system-config',
  name: 'SystemConfig',
  component: Layout,
  redirect: 'noRedirect',
  meta: { title: '系统配置' },
  children: [
    {
      path: 'account-manage',
      name: 'AccountManage',
      component: () => import('@/views/system-config/account-manage'),
      meta: {
        tagKey: 'AccountManage',
        title: '账户管理',
      },
    },
    {
      path: 'account-manage-edit',
      name: 'AccountManageEdit',
      component: () => import('@/views/system-config/account-manage/edit'),
      meta: {
        tagKey: 'AccountManage',
        title: '账户管理',
        activeMenu: '/system-config/account-manage',
      },
    },
  ],
};
