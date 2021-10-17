import Layout from '@/layout';

export default {
  path: '/product-manage',
  name: 'ProductManage',
  component: Layout,
  redirect: 'noRedirect',
  meta: { title: '商品管理', icon: 'product' },
  children: [
    {
      path: 'product-category',
      name: 'ProductCategory',
      component: () => import('@/views/product-manage/product-category'),
      meta: {
        tagKey: 'ProductCategory',
        title: '商品类型管理',
      },
    },
    {
      path: 'product-category-edit',
      name: 'ProductCategoryEdit',
      component: () => import('@/views/product-manage/product-category/edit'),
      meta: {
        tagKey: 'ProductCategory',
        title: '商品类型管理',
        activeMenu: '/product-manage/product-category',
      },
    },
  ],
};
