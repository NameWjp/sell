import Layout from '@/layout';

export default {
  path: '/user',
  name: 'User',
  component: Layout,
  redirect: { path: 'UserSetting' },
  children: [
    {
      path: 'user-setting',
      name: 'UserSetting',
      component: () => import('@/views/user/user-setting'),
      hidden: true,
      meta: { tagKey: 'UserSetting', title: '用户设置' },
    },
  ],
};
