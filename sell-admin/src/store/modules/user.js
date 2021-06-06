import { login, getUserInfo, logout, refreshToken } from '@/api/login';
import { setToken, clearToken } from '@/utils/request';

// 过滤按钮 type: 2为按钮 1为菜单
function filterMenuTree(menus) {
  const ret = [];

  menus.forEach((item) => {
    const menu = { ...item };
    const { children, type } = menu;
    if (type === 1) {
      if (children && children.length) {
        menu.children = filterMenuTree(menu.children);
      }
      ret.push(menu);
    }
  });

  return ret;
}

const state = {
  name: 'test',
  roles: [],
  userInfo: {
    privilegeList: [
      { url: '/system-config' },
      { url: '/system-config/account-manage' },
      { url: '/system-config/dict-manage' },
      { url: '/system-config/role-manage' },
    ],
  },
  menuTree: [
    {
      name: '首页',
      icon: 'dashboard',
      url: '/dashboard',
      code: '/dashboard',
    },
    {
      name: '系统配置',
      icon: 'setting',
      url: '/system-config',
      code: 'system-config',
      children: [
        {
          name: '账户管理',
          url: '/system-config/account-manage',
        },
        {
          name: '字典管理',
          url: '/system-config/dict-manage',
        },
        {
          name: '角色管理',
          url: '/system-config/role-manage',
        },
      ],
    },
  ],
};

const mutations = {
  SET_NAME(state, name) {
    state.name = name;
  },
  SET_ROLES(state, roles) {
    state.roles = roles;
  },
  SET_USER_INFO(state, info) {
    state.userInfo = info;
  },
  SET_MENU_TREE(state, tree) {
    state.menuTree = tree;
  },
};

const actions = {
  async login({ commit }, userInfo) {
    const { username, password } = userInfo;
    try {
      const { data: { token: str, expiredDate } = {} } = await login({ username: username.trim(), password });
      const token = `Bearer${str}`;
      if (token) {
        setToken(token, expiredDate);
        return token;
      }
      return false;
    } catch (e) {
      return false;
    }
  },

  async getInfo({ commit }) {
    const { data: userInfo = [] } = await getUserInfo();
    const { username = '', privilegeList = [], privilegeTreeList = [] } = userInfo;
    const roles = (privilegeList || []).map(item => item.code);
    commit('SET_USER_INFO', userInfo);
    commit('SET_ROLES', roles);
    commit('SET_NAME', username);
    commit('SET_MENU_TREE', filterMenuTree(privilegeTreeList));
    return { roles };
  },

  async logout({ dispatch }) {
    await logout();
    await dispatch('resetToken');
  },

  async refreshToken() {
    const { data: { token: str, expiredDate } = {} } = await refreshToken();
    const token = `Bearer${str}`;
    setToken(token, expiredDate);
  },

  resetToken({ commit }) {
    return new Promise((resolve) => {
      commit('SET_ROLES', []);
      clearToken();
      resolve();
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
