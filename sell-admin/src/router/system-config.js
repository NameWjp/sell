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
    {
      path: 'dict-manage',
      name: 'DictManage',
      component: () => import('@/views/system-config/dict-manage'),
      meta: {
        tagKey: 'DictManage',
        title: '字典管理',
      },
    },
    {
      path: 'dict-manage-edit',
      name: 'DictManageEdit',
      component: () => import('@/views/system-config/dict-manage/edit'),
      meta: {
        tagKey: 'DictManage',
        title: '字典管理',
        activeMenu: '/system-config/dict-manage',
      },
    },
    {
      path: 'role-manage',
      name: 'RoleManage',
      component: () => import('@/views/system-config/role-manage'),
      meta: {
        tagKey: 'RoleManage',
        title: '角色管理',
      },
    },
    {
      path: 'role-manage-edit',
      name: 'RoleManageEdit',
      component: () => import('@/views/system-config/role-manage/edit'),
      meta: {
        tagKey: 'RoleManage',
        title: '角色管理',
        activeMenu: '/system-config/role-manage',
      },
    },
  ],
};
