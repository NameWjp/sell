import { asyncRoutes, constantRoutes, getPathByName } from '@/router';

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role));
  }
  return true;
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = [];

  routes.forEach((route) => {
    const tmp = { ...route };
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles);
      }
      res.push(tmp);
    }
  });

  return res;
}

// 通过权限列表，过滤前台的路由
function filterAsyncRoutesByList(routes, privilegeList) {
  const res = [];

  routes.forEach((route) => {
    const tmp = { meta: {}, ...route };

    if (tmp.name) {
      const path = getPathByName(tmp.name);

      const privilege = privilegeList.find((row) => {
        return row.url === path || row.url === (tmp.meta || {}).activeMenu;
      });

      if (privilege) {
        if (tmp.children && tmp.children.length) {
          tmp.children = filterAsyncRoutesByList(tmp.children, privilegeList);
        }
        res.push(tmp);
      }
    }

    if (tmp.path === '*') {
      res.push(tmp);
    }
  });
  return res;
}

const state = {
  routes: [],
  addRoutes: [],
};

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes;
    state.routes = constantRoutes.concat(routes);
  },
};

const actions = {
  generateRoutes({ commit, rootState }, roles) {
    return new Promise((resolve) => {
      let accessedRoutes;
      if (roles.includes('admin')) {
        accessedRoutes = asyncRoutes || [];
      } else {
        const privilegeList = rootState.user.userInfo.privilegeList;
        accessedRoutes = filterAsyncRoutesByList(asyncRoutes, privilegeList);
        // accessedRoutes = filterAsyncRoutes(asyncRoutes, roles);
      }

      commit('SET_ROUTES', accessedRoutes);
      resolve(accessedRoutes);
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
