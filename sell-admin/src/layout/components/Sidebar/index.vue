<template>
  <div :class="{'has-logo':showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        unique-opened
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item v-for="route in filterRouter" :key="route.path" :item="route" :base-path="route.path" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import Logo from './Logo';
import SidebarItem from './SidebarItem';
import variables from '@/styles/variables.scss';
import { flattenTree } from '@/utils/tree';

function transformAbsolutePath(routes, rootPath) {
  const transRoutes = routes.map(item => {
    const route = { ...item };
    if (rootPath === '/') {
      route.path = `${rootPath}${route.path}`;
    } else if (rootPath && !route.path.startsWith('/')) {
      route.path = `${rootPath}/${route.path}`;
    }
    if (route.children && route.children.length) {
      route.children = transformAbsolutePath(route.children, route.path);
    }
    return route;
  });
  return transRoutes;
}

// 根据后台传来的菜单展示，并将路由的参数合并到菜单里
function menusConnectRoutes(menus, routeList) {
  const connectMenus = menus.map(item => {
    const menu = { ...item };
    const route = routeList.find(route => menu.url === route.path);

    if (route) {
      menu.meta = {
        ...route.meta,
        title: menu.name,
        icon: menu.icon,
      };
      menu.path = route.path;
      if (route.hidden) {
        menu.hidden = route.hidden;
      }
      if (route.alwaysShow) {
        menu.alwaysShow = route.alwaysShow;
      }
      if (route.redirect) {
        menu.redirect = route.redirect;
      }
    } else {
      menu.path = '';
      menu.meta = {
        title: menu.name,
        icon: menu.icon,
      };
    }

    if (menu.children && menu.children.length) {
      menu.children = menusConnectRoutes(menu.children, routeList);
    } else {
      menu.children = [];
    }

    return menu;
  });
  return connectMenus;
}

export default {
  components: { SidebarItem, Logo },
  computed: {
    ...mapGetters([
      'permission_routes',
      'sidebar',
      'menuTree',
    ]),
    filterRouter() {
      const routes = transformAbsolutePath(this.permission_routes, '');
      const routeList = flattenTree(routes);
      return menusConnectRoutes(this.menuTree, routeList);
    },
    activeMenu() {
      const route = this.$route;
      const { meta, path } = route;
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    variables() {
      return variables;
    },
    isCollapse() {
      return !this.sidebar.opened;
    },
  },
};
</script>
