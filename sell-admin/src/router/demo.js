import Layout from '@/layout';

// 由于支持 keep-alive 多级子路由会出现缓存问题，这里的路由只设置两级，菜单设置多级
export default {
  path: '/report-center',
  component: Layout,
  name: 'ReportCenter',
  meta: { title: '报表中心', icon: 'baobiao' },
  redirect: { name: 'GarbageYield' },
  children: [
    {
      path: 'garbage-yield',
      name: 'GarbageYield',
      component: () => import('@/views/report-center/garbage-yield'),
      meta: {
        title: '每日垃圾产量报表',
        tagKey: 'GarbageYield',
      },
    },
    {
      path: 'garbage-yield-edit',
      name: 'GarbageYieldEdit',
      hidden: true,
      component: () => import('@/views/report-center/garbage-yield/edit'),
      meta: {
        title: '每日垃圾产量报表',
        tagKey: 'GarbageYield',
        activeMenu: '/report-center/garbage-yield',
      },
    },
  ],
};
